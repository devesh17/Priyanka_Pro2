package com.example.priyankaagarwal.priyanka_pro1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class PingTestMain extends AppCompatActivity {

    Button _btn_PingSetup ;
    Button _btn_StartTest ;

    ArrayList<TC_Type_Item> Play_TC_List;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ping_test_main);
        setTitle("Ping Test");

        Bundle bundleObject = getIntent().getExtras();
        Play_TC_List = (ArrayList<TC_Type_Item>) bundleObject.getSerializable("Info_TC_List");
        SetupButtonCLick1();
        StartButtonClick();
    }

    private void SetupButtonCLick1() {

        // Get a reference to a button
        _btn_PingSetup= (Button) findViewById(R.id.btn_PingSetup);
        _btn_PingSetup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                  /*  Log.d("Priyanka", "Click is used");
                    Toast.makeText(PingTestMain.this,"clicked",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(),PingTestSetup.class);
                    intent.putExtra("ACT", "Going to set Ping Test");
                    startActivity(intent);
                    */

                    Intent Main_Save_Intent = new Intent(getApplicationContext(),PingTestSetup.class);
                    Bundle Main_Save_Bundle = new Bundle();
                    Main_Save_Bundle.putSerializable("Play_TC_List",Play_TC_List);
                    Main_Save_Intent.putExtras(Main_Save_Bundle);
                    startActivity(Main_Save_Intent);


            }
        });

    }


    private void StartButtonClick(){

        _btn_StartTest = (Button) findViewById(R.id.btn_PingPlay);

        _btn_StartTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(PingTestMain.this,"Item to run " + Play_TC_List.size(),Toast.LENGTH_SHORT).show();
            }
        });
    }

}
