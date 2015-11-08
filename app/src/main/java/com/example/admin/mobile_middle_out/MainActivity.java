package com.example.admin.mobile_middle_out;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.paypal.android.MEP.PayPal;
import com.paypal.android.MEP.PayPalPayment;


public class MainActivity extends AppCompatActivity{


    private EditText username;
    private EditText password;
    private String email = "";
    private String pass = "";
    private TextView place;
    private TextView balance;
    private TextView country;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_french);
        boolean bad = false;

        File file = getBaseContext().getFileStreamPath("PaypalInfo.txt");
        File file2 = getBaseContext().getFileStreamPath("Language.txt");

        if(!file2.exists()){
            setContentView(R.layout.activity_french);

            try {
                FileOutputStream fos = openFileOutput("Balance.txt", Context.MODE_PRIVATE);
                fos.write(("100.00").getBytes());
                fos.close();
            }
            catch (Exception e){
            }
        }

        else if(!file.exists()){
            setContentView(R.layout.activity_login);
            username = (EditText) findViewById(R.id.username);
            username.getBackground().setColorFilter(212121, PorterDuff.Mode.SRC_IN);

        }
        else{
            setContentView(R.layout.activity_main);
            place = (TextView) findViewById(R.id.textView8);
            balance = (TextView) findViewById(R.id.textView14);

            try{
                String ret = "";

                String filename = "Location.txt";
                FileInputStream inputStream = openFileInput(filename);
                BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
                ret = r.readLine();
                r.close();
                inputStream.close();
                place.setText(ret);
            }
            catch(Exception e){
                place.setText("CANADA");
            }

            try{
                String ret = "";

                String filename = "Balance.txt";
                FileInputStream inputStream = openFileInput(filename);
                BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
                ret = r.readLine();
                r.close();
                inputStream.close();
                balance.setText("$" + ret);
            }
            catch(Exception e){
                balance.setText("$100.00");
            }
            //country = (TextView) findViewById(R.id.textView15);
        }

/*
        Geocoder gcd = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = gcd.getFromLocation(43.7001100, -79.4163000, 1);
            if (addresses.size() > 0) {
                System.out.println(addresses.get(0).getLocality());
                city.setText(addresses.get(0).getLocality());
            }
        }catch(Exception e){

        }

        try{
           String locale = this.getResources().getConfiguration().locale.getDisplayCountry();
            country.setText(locale);
        }catch(Exception e){

        }*/

    }

    public void toSetPrice(View view){
        Intent intent = new Intent(this, SetPrice.class);
        startActivity(intent);
    }

    public void toBuy(View view){
        Intent intent = new Intent(this, buy11.class);
        startActivity(intent);
    }

    public void toLogin(View view){
        setContentView(R.layout.login);
    }

    public void toHistory(View view){
        Intent intent = new Intent(this, History.class);
        startActivity(intent);
    }

    public void toEnglish(View view){
        try {
            FileOutputStream fos = openFileOutput("Language.txt", Context.MODE_PRIVATE);
            fos.write(("E").getBytes());
            fos.close();
        }
        catch (Exception e){
        }/*
        setContentView(R.layout.activity_login);
        username = (EditText) findViewById(R.id.username);
        username.getBackground().setColorFilter(727272, PorterDuff.Mode.SRC_IN);*/

        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    public void toFrench(View view){
        try {
            FileOutputStream fos = openFileOutput("Language.txt", Context.MODE_PRIVATE);
            fos.write(("F").getBytes());
            fos.close();
        }
        catch (Exception e){
        }/*
        setContentView(R.layout.activity_login);
        username = (EditText) findViewById(R.id.username);
        username.getBackground().setColorFilter(727272, PorterDuff.Mode.SRC_IN);*/

        Intent intent = getIntent();
        finish();
        startActivity(intent);

    }


    public void savePaypalInfo(View view){
        email = username.getText().toString() + "\n";
        try {
            FileOutputStream fos = openFileOutput("PaypalInfo.txt", Context.MODE_PRIVATE);
            fos.write((email).getBytes());
            fos.close();

            Toast toast = Toast.makeText(getApplicationContext(), "Details Saved!", Toast.LENGTH_SHORT);
            toast.show();

            //setContentView(R.layout.activity_main);
        }
        catch (Exception e){
            Toast toast = Toast.makeText(getApplicationContext(), "Try Again!", Toast.LENGTH_SHORT);
            toast.show();
        }

        try{
            String locale = this.getResources().getConfiguration().locale.getDisplayCountry();
            place = (TextView) findViewById(R.id.textView8);
            place.setText(locale);
            FileOutputStream fos = openFileOutput("Location.txt", Context.MODE_PRIVATE);
            fos.write(locale.getBytes());
            fos.close();
        }catch(Exception e){

        }
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

}