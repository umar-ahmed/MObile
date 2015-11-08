package com.example.admin.mobile_middle_out;

/**
 * Created by Admin on 08/11/2015.
 */
import android.app.Activity;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;


public class NFC extends AppCompatActivity implements NfcAdapter.CreateNdefMessageCallback {

    //private EditText mEditText;
    //private TextView emaildd;
    //private TextView amountt;
    //private TextView both;

    private String amount="",email="";
    private String offline = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting);
       /* mEditText = (EditText) findViewById(R.id.edit_text_field);
        emaildd = (TextView) findViewById(R.id.textView11);
        amountt = (TextView) findViewById(R.id.textView12);
        both = (TextView) findViewById(R.id.textView13);*/
        getEmailPass();
        try {
            String filename = "trust.txt";
            FileInputStream inputStream = openFileInput(filename);
            BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
            offline = r.readLine();
            r.close();
            inputStream.close();
        }catch(Exception e){

        }


        NfcAdapter mAdapter = NfcAdapter.getDefaultAdapter(this);

        if (!mAdapter.isEnabled()) {
            startActivity(new Intent(Settings.ACTION_NFC_SETTINGS));
        }
        Toast.makeText(this, "NFC Available!", Toast.LENGTH_LONG).show();
        mAdapter.setNdefPushMessageCallback(this, this);
    }


    @Override
    public NdefMessage createNdefMessage(NfcEvent nfcEvent) {
        //String message = mEditText.getText().toString();
        getEmailPass();
        //
        String details = email + " " + amount;
        NdefRecord ndefRecord = NdefRecord.createMime("text/plain", details.getBytes());
        NdefMessage ndefMessage = new NdefMessage(ndefRecord);
        return ndefMessage;

    }

    public void getEmailPass(){
        try{
            String ret = "";

            String filename = "PaypalInfo.txt";
            FileInputStream inputStream = openFileInput(filename);
            BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));

            //ret = r.readLine() + " ";
            String s = r.readLine();
            email = s + "";
            //emaildd.setText(s);
            r.close();
            inputStream.close();

            filename = "Price.txt";
            inputStream = openFileInput(filename);
            r = new BufferedReader(new InputStreamReader(inputStream));
            s = r.readLine();
            amount = s.substring(1,s.length());
            //amountt.setText(s);
            r.close();
            inputStream.close();

            // both.setText(ret);
        }
        catch(Exception e){

        }
    }
}