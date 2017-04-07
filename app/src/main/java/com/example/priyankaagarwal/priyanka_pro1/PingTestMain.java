package com.example.priyankaagarwal.priyanka_pro1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.io.InputStreamReader;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.*;

public class PingTestMain extends AppCompatActivity {

    Button _btn_PingSetup ;
    Button _btn_StartTest ;
    TextView _screen;
    private String display = "LOGS" + '\n';
    private String Final_Result = '\n' +  '\n' + "FINAL RESULTS:" + '\n';

    ArrayList<TC_Type_Item> Play_TC_List;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ping_test_main);
        setTitle("Ping Test");

        _screen = (TextView)findViewById(R.id.TextView_Log);
        _screen.setText(display);

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


                try
                {
                    for (TC_Type_Item temp1:Play_TC_List) {

                        if(temp1.Bit_TC)
                        {
                            for(int P_Count=0; P_Count < Integer.parseInt(temp1.Info_Packet_Count);P_Count++)
                            {
                                String s = fExecutePing(temp1);
                                int Internal_Delay = (Integer.parseInt(temp1.Info_Interval))*1000;
                                Thread.sleep(Internal_Delay);
                                Final_Result += "PING NO: " + Integer.toString(P_Count+1) + " RespTime :" +  s +"ms" + '\n';

                            }
                        }



                        else
                        {
                            for(int wait_count = 0; wait_count < Integer.parseInt(temp1.Info_Delay);wait_count++)
                            {
                                Thread.sleep(1000);
                            }
                        }

                    }

                    display += Final_Result;
                    updatescreen();


                }
                //Toast.makeText(PingTestMain.this,"Item to run " + Play_TC_List.size(),Toast.LENGTH_SHORT).show();
                catch (Exception e) {
                     Toast.makeText(getApplicationContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    //return Time_string;

                }

            }
        });
    }


    private void updatescreen()
    {
        _screen.setMovementMethod(new ScrollingMovementMethod());
        _screen.setText(display);
    }


    //public void fExecutePing()
    public String fExecutePing(TC_Type_Item Test_Info)
    {
        String Time_string = "fail";

        try
        {
            String cmdPing = "ping -c 1 -s " + Test_Info.Info_Size + " " + Test_Info.Info_Host;
            //String cmdPing = "ping "+host;
            Runtime r = Runtime.getRuntime();
            Process p = r.exec(cmdPing);
            BufferedReader in = new BufferedReader(	new InputStreamReader(p.getInputStream()));
            String inputLine;

            while((inputLine = in.readLine())!= null)
            {
                display += inputLine + '\n' ;
                updatescreen();

                if(inputLine.contains("time="))
                {
                    Time_string = inputLine.substring(inputLine.indexOf("time=") + 5, inputLine.indexOf("ms")).trim();
                    //_RespList.add(Time_string);
                }

            }

            display += "Time String::" + Time_string + '\n';
            updatescreen();
            return Time_string;

        }

        catch (Exception e) {
            Toast.makeText(this, "Error: "+ e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            return Time_string;

        }

    }




}
