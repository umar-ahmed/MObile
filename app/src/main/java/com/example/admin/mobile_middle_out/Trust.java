package com.example.admin.mobile_middle_out;

/**
 * Created by Admin on 08/11/2015.
 */
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.io.FileOutputStream;

public class Trust extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trust);
    }

    public void add2q(View view){
        try {
            FileOutputStream fos = openFileOutput("trust.txt", Context.MODE_PRIVATE);
            fos.write(("1").getBytes());
            fos.close();

            Intent intent = new Intent(this, NFC.class);
            //Intent intent = new Intent(this, NFCDisplay.class);
            startActivity(intent);
        }
        catch (Exception e){
        }
    }

    public void cancel(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
