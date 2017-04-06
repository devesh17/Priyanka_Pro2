package com.example.priyankaagarwal.priyanka_pro1;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {

    ListView _TestList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        _TestList = (ListView)findViewById(R.id.TestList);

        String[] TestItemsList = {"PingTest", "Test1", "Test2"};
        ListAdapter adapterList = new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,TestItemsList);
        _TestList = (ListView) findViewById(R.id.TestList);

        _TestList.setAdapter(adapterList);

        _TestList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
             Intent intentPing = new Intent(MainActivity.this, PingTestMain.class);

             String ItemClicked = (String) adapterView.getItemAtPosition(i);

                if(ItemClicked == "PingTest")
                {
                    intentPing.putExtra("TestCall", _TestList.getItemAtPosition(i).toString());
                    startActivity(intentPing);
                }
                else {

                    Toast.makeText(MainActivity.this,"Yet to come", Toast.LENGTH_SHORT).show();
                }

            }
        } );


    }



}
