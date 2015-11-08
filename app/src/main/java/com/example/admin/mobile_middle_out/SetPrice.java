package com.example.admin.mobile_middle_out;

/**
 * Created by admin on 2015-11-07.
 */
import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileOutputStream;

public class SetPrice extends AppCompatActivity {

    private TextView total;
    public String display = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_price);
        total = (TextView) findViewById(R.id.textView);


        /*View title = getWindow().findViewById(android.R.id.title);
        View titleBar = (View) title.getParent();
        titleBar.setBackgroundColor(Color.CYAN);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);*/

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
    }

    public void press1(View view){

        PriceAdd("1");
    }
    public void press2(View view){

        PriceAdd("2");
    }
    public void press3(View view){

        PriceAdd("3");
    }
    public void press4(View view){

        PriceAdd("4");
    }
    public void press5(View view){

        PriceAdd("5");
    }
    public void press6(View view){

        PriceAdd("6");
    }
    public void press7(View view){

        PriceAdd("7");
    }
    public void press8(View view){

        PriceAdd("8");
    }
    public void press9(View view){

        PriceAdd("9");
    }
    public void press0(View view){

        PriceAdd("0");
    }

    public void del(View view){
        if(display.length() == 0){
            return;
        }
        display = display.substring(0,display.length()-1);
        if(display.length() == 0){
            display = "000";
        }
        else if(display.length() == 1){
            display = "00" + display;
        }
        else if(display.length() == 2){
            display = "0" + display;
        }

        display = strip(display);
        //display = "$" + display.substring(0,display.length()-2) + "." + display.substring(display.length()-2,display.length());
        total.setText("$" + display.substring(0, display.length() - 2) + "." + display.substring(display.length() - 2, display.length()));
        return;
    }

    public void PriceAdd(String num){
        display = display + num;

        if(display.length() == 1){
            display = "00" + display;
        }
        else if(display.length() == 2){
            display = "0" + display;
        }

        display = strip(display);

        //display = "$" + display.substring(0,display.length()-2) + "." + display.substring(display.length()-2,display.length());
        total.setText("$" + display.substring(0, display.length() - 2) + "." + display.substring(display.length() - 2, display.length()));
    }

    public void SaveAndWait(View view){

        try {
            FileOutputStream fos = openFileOutput("Price.txt", Context.MODE_PRIVATE);
            fos.write((total.getText().toString()).getBytes());
            fos.close();
        }
        catch (Exception e){
            Toast toast = Toast.makeText(getApplicationContext(), "Try Again!", Toast.LENGTH_SHORT);
            toast.show();
        }

        ConnectivityManager cm = (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        if(!isConnected){
            Intent intent = new Intent(this, Trust.class);
            startActivity(intent);

            Toast.makeText(getApplicationContext(), "No Connection Found", Toast.LENGTH_SHORT).show();
        }

        else {
            //setContentView(R.layout.activity_waiting_blue);
            try {
                FileOutputStream fos = openFileOutput("trust.txt", Context.MODE_PRIVATE);
                fos.write(("0").getBytes());
                fos.close();

                Intent intent = new Intent(this, NFC.class);
                //Intent intent = new Intent(this, NFCDisplay.class);
                startActivity(intent);
            }catch(Exception e){

            }
        }
    }

    public void ToMain(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public String strip(String s){
        String ans = s;
        for(int i = 0; i < max(0, s.length() - 3); i++){
            if(s.charAt(i) != '0'){
                break;
            }
            ans = ans.substring(1,s.length());
        }
        return ans;
    }

    private int max(int a, int b) {
        if(a > b)
            return a;
        else
            return b;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivityForResult(myIntent, 0);
        return true;

    }

}