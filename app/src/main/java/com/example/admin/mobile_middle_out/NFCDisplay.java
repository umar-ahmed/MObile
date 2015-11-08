package com.example.admin.mobile_middle_out;

/**
 * Created by Admin on 08/11/2015.
 */
/**
 * Created by admin on 2015-11-07.
 */
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.SystemClock;
import android.os.Vibrator;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;

public class NFCDisplay extends Activity {

    private TextView mTextView;
    private TextView nTextView;
    private EditText des;
    private static final int REQUEST_CODE_PAYMENT = 1;
    String email = "burhan.123@gmail.com";
    String price = "10.00";

    private PayPalConfiguration paypalConfig=new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId( Util.paypal_sdk_id)
            .acceptCreditCards(true)
            .defaultUserEmail(getEmail())
            .defaultUserPhone("4162744792");

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfcdisplay);
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(500);
        mTextView = (TextView) findViewById(R.id.textView10);
        nTextView = (TextView) findViewById(R.id.textView16);
        des = (EditText) findViewById(R.id.editText);

        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, paypalConfig);
        startService(intent);
    }

    @Override
    protected void onResume(){
        super.onResume();
        Intent intent = getIntent();
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction())) {
            Parcelable[] rawMessages = intent.getParcelableArrayExtra(
                    NfcAdapter.EXTRA_NDEF_MESSAGES);

            NdefMessage message = (NdefMessage) rawMessages[0]; // only one message transferred
            String emailprice = new String(message.getRecords()[0].getPayload());

            int splitpt = 0;
            boolean otherseen = false;

            for(int i = 0; i < emailprice.length(); i++){
                if(emailprice.charAt(i) != ' '){
                    otherseen = true;
                }
                if(emailprice.charAt(i) == ' ' && otherseen){
                    splitpt = i;
                    break;
                }
            }
            email = emailprice.substring(0,splitpt);
            price = emailprice.substring(splitpt+1,emailprice.length());
            price = price.substring(1);

            mTextView.setText("$" + price);
            nTextView.setText(email);

        } else
            mTextView.setText("Waiting for NDEF Message");

    }


    public void launchPayPalPayment(View view) {
        PayPalPayment thingToBuy = new PayPalPayment(new BigDecimal(price),"CAD", email,
                PayPalPayment.PAYMENT_INTENT_SALE);

        Intent intent = new Intent(NFCDisplay.this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, paypalConfig);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, thingToBuy);
        startActivityForResult(intent, REQUEST_CODE_PAYMENT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                Toast.makeText(getApplicationContext(), "Payment Verified", Toast.LENGTH_LONG).show();

                long cur_time = SystemClock.uptimeMillis();
                try {
                    FileOutputStream fos = openFileOutput("Receipts.txt", Context.MODE_APPEND);
                    fos.write((price + " " + des.getText().toString() + "\n").getBytes());
                    fos.close();
                }
                catch (Exception e){
                }

                String ret = "100.00";

                try{
                    String filename = "Balance.txt";
                    FileInputStream inputStream = openFileInput(filename);
                    BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
                    ret = r.readLine();
                    r.close();
                    inputStream.close();
                }
                catch(Exception e){
                }

                double neww = Double.parseDouble(ret) - Double.parseDouble(price);
                neww = max(0.00,neww);

                try {
                    FileOutputStream fos = openFileOutput("Balance.txt", Context.MODE_PRIVATE);
                    fos.write((String.valueOf(neww)).getBytes());
                    fos.close();
                }
                catch (Exception e){
                }

                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);

            }
            else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(), "Payment Canceled , Try again ", Toast.LENGTH_LONG).show();

            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                Toast.makeText(getApplicationContext(), "Payment failed , Try again ", Toast.LENGTH_LONG).show();
            }
        }
    }

    public String getEmail(){
        try{
            String ret = "";

            String filename = "PaypalInfo.txt";
            FileInputStream inputStream = openFileInput(filename);
            BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
            ret = r.readLine() + " ";
            r.close();
            inputStream.close();

            return ret;
        }
        catch(Exception e){
            return "kman29@gmail.com";
        }
    }

    public void toMain(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private double max(double a, double b){
        if(a > b) return a;
        else return b;
    }


}