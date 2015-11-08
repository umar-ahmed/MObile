package com.example.admin.mobile_middle_out;


/**
 * Created by admin on 2015-11-07.
 */
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SectionIndexer;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class History extends AppCompatActivity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        // 1. pass context and data to the custom adapter
        MyAdapter adapter = new MyAdapter(this, generateData());

        // 2. Get ListView from activity_main.xml
        ListView listView = (ListView) findViewById(R.id.listView);

        // 3. setListAdapter
        listView.setAdapter(adapter);
    }

    private ArrayList<Item> generateData(){

        List<String> receipts =  new ArrayList<String>();

        receipts.add("Jacket $20");

        try{
            String filename = "Receipts.txt";
            FileInputStream inputStream = openFileInput(filename);
            BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder total = new StringBuilder();
            String line;
            while ((line = r.readLine()) != null) {
                receipts.add(line);
            }
            r.close();
            inputStream.close();
        }
        catch(Exception e){
            System.out.println("bad");
        }


        ArrayList<Item> items = new ArrayList<Item>();

        for(int i = 0; i < receipts.size(); i++){
            int splithere = 0;
            String s = receipts.get(i);
            for(int j = 0; j < s.length(); j++){
                if(s.charAt(j) == ' '){
                    splithere = j;
                    break;
                }
            }
            String pp = s.substring(splithere+1,s.length());
            String desc = s.substring(0,splithere);
            items.add(new Item(pp,desc));
        }
        items.add(new Item("$12.00","Groovy Q's mixtape"));
        items.add(new Item("$10.00","Second Item on the list"));
        items.add(new Item("$35.00","Third Item on the list"));

        return items;
    }
}