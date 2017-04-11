package com.example.priyankaagarwal.priyanka_pro1;

import android.annotation.TargetApi;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.io.InputStreamReader;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.*;

public class PingTestMain extends AppCompatActivity {

    Button _btn_PingSetup ;
    Button _btn_StartTest ;
    Button _btn_pause;
    Button _btn_stop;
    TextView _screen;
    ScrollView _ScrollView1;
    private String display = "LOGS" + '\n';
    private String Final_Result = '\n' +  '\n' + "FINAL RESULTS:" + '\n';
    ArrayList<String> Final_Result_list = new ArrayList<String>();

    ArrayList<TC_Type_Item> Play_TC_List;
    public boolean pause_state = false;

    public Thread t1 = new Thread(){};


    private View.OnClickListener btnClickListner = new View.OnClickListener(){

        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        @Override
        public void onClick(View v) {

            switch (v.getId()) {

                case R.id.btn_PingPlay:
                    Toast.makeText(getApplicationContext(), "Start Button Clicked", Toast.LENGTH_SHORT).show();
                    RunTest();
                    break;
                case R.id.btn_stop :
                    StopTest();
                    break;
                case R.id.btn_pause :
                    PauseTest();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ping_test_main);
        setTitle("Ping Test");

        _ScrollView1 = (ScrollView)findViewById(R.id.ScrollView1);
        _screen = (TextView)findViewById(R.id.TextView_Log);
        _screen.setText(display);

        Bundle bundleObject = getIntent().getExtras();
        Play_TC_List = (ArrayList<TC_Type_Item>) bundleObject.getSerializable("Info_TC_List");

        _btn_StartTest = (Button)findViewById(R.id.btn_PingPlay);
        _btn_StartTest.setOnClickListener(btnClickListner);

        _btn_pause = (Button)findViewById(R.id.btn_pause);
        _btn_pause.setOnClickListener(btnClickListner);

        _btn_stop = (Button)findViewById(R.id.btn_stop);
        _btn_stop.setOnClickListener(btnClickListner);

         SetupButtonCLick1();
        //StartButtonClick();
    }

    public void PauseTest()
    {
        String Pause_Title = (String)_btn_pause.getText();
        if(Pause_Title == "Pause")
        {
            pause_state = true;
        }
        else
        {
            pause_state = false;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    _btn_pause.setText("Pause");

                }
            });
        }

    }


    public void RunTest(){

        try
        {
            Final_Result_list.clear();

            t1 = new Thread(){

                @Override
                public void run(){

                    try
                    {
                        MainRun();
                    }

                    catch (Exception e)
                    {
                        Toast.makeText(getApplicationContext(), "Error: "+ e.getMessage().toString(), Toast.LENGTH_SHORT).show();

                    }
                }


            };

            t1.start();
            _btn_StartTest.setEnabled(false);

        }

        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(), "Error: "+ e.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }

    }


    public void StopTest(){

        if(t1.isAlive())
        {
            String Pause_Title2 = (String)_btn_pause.getText();
            if(Pause_Title2 == "Resume")
            {
                pause_state = false;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        _btn_pause.setText("Pause");

                    }
                });
            }
            t1.interrupt();
            //super.onDestroy();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    display +=  "Test has been Stopped" + '\n';
                    _screen.setMovementMethod(new ScrollingMovementMethod());
                    _screen.setText(display);
                    _ScrollView1.fullScroll(ScrollView.FOCUS_DOWN);

                }
            });
        }
        SaveResultsText();

        _btn_StartTest.setEnabled(true);

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
                    Thread T1 = new Thread(){
                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    MainRun();
                                }
                            });

                        }
                    };

                    T1.start();


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
    @TargetApi(Build.VERSION_CODES.N)
    public String fExecutePing(TC_Type_Item Test_Info)
    {

        String current_time = (DateFormat.format("dd-MM-yyyy hh:mm:ss", new java.util.Date()).toString()).replaceAll("-","_").replaceAll(" ","_").replaceAll(":","_");
        String Time_string = current_time + " fail";


        try
        {
            String cmdPing = "ping -c 1 -s " + Test_Info.Info_Size + " " + Test_Info.Info_Host;

            current_time = (DateFormat.format("dd-MM-yyyy hh:mm:ss", new java.util.Date()).toString()).replaceAll("-","_").replaceAll(" ","_").replaceAll(":","_");

            Runtime r = Runtime.getRuntime();
            Process p = r.exec(cmdPing);
            BufferedReader in = new BufferedReader(	new InputStreamReader(p.getInputStream()));
            String inputLine;

            while((inputLine = in.readLine())!= null)
            {

                if(inputLine.contains("time="))
                {

                    Time_string =  current_time + " "+ inputLine.substring(inputLine.indexOf("time=") + 5, inputLine.indexOf("ms")).trim();
                }

            }

            return Time_string;

        }

        catch (Exception e) {
            Toast.makeText(this, "Error: "+ e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            return current_time ;

        }

    }


    public void SaveResultsText()
    {
        try{

            // Going to Save result in text file
            String Save_Result_Time = (DateFormat.format("dd-MM-yyyy hh:mm:ss", new java.util.Date()).toString()).replaceAll("-","_").replaceAll(" ","_").replaceAll(":","_");


            File file = new File("/sdcard/TestConnect/", "Results");
            boolean Create_Result_folder_result = file.mkdirs();

            String filename = "/sdcard/TestConnect/Results/" + Save_Result_Time + ".txt";

            FileOutputStream outputStream;
            outputStream = new FileOutputStream( new File(filename));
            for (String result_line:Final_Result_list) {

                outputStream.write(result_line.getBytes());
            }


            outputStream.close();

        }

        catch (Exception e){
            Log.e("TEST PING", "exception: " + e.getMessage());
            Log.e("TEST PING", "exception: " + e.toString());

        }
    }


    public void MainRun()
    {
        try
        {

            int TC_Count = 0;

            for (TC_Type_Item temp1:Play_TC_List) {

                int pause_state_print = 0;
                while(pause_state)
                {

                    if(pause_state_print == 0)
                    {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                display +=  "Test has been paused" + '\n';
                                _screen.setMovementMethod(new ScrollingMovementMethod());
                                _screen.setText(display);
                                _btn_pause.setText("Resume");
                                _ScrollView1.fullScroll(ScrollView.FOCUS_DOWN);

                            }
                        });

                        pause_state_print = 1;
                    }

                    Thread.sleep(500);

                }

                if(temp1.Bit_TC)
                {
                    TC_Count += 1;

                    for(int P_Count=0; P_Count < Integer.parseInt(temp1.Info_Packet_Count);P_Count++)
                    {
                        String s = fExecutePing(temp1);
                        int Internal_Delay = (Integer.parseInt(temp1.Info_Interval))*1000;
                        Thread.sleep(Internal_Delay);

                        Final_Result = "TC No: " + Integer.toString(TC_Count) +" PING NO: " + Integer.toString(P_Count+1) + " RespTime :" +  s +"ms" + '\n';
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                display +=  Final_Result + '\n';
                                Final_Result_list.add(Final_Result);
                                _screen.setMovementMethod(new ScrollingMovementMethod());
                                _screen.setText(display);
                                _ScrollView1.fullScroll(ScrollView.FOCUS_DOWN);
                            }
                        });


                        pause_state_print = 0;
                        while(pause_state)
                        {

                            if(pause_state_print == 0)
                            {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        display +=  "Test has been paused" + '\n';
                                        _screen.setMovementMethod(new ScrollingMovementMethod());
                                        _screen.setText(display);
                                        _btn_pause.setText("Resume");
                                        _ScrollView1.fullScroll(ScrollView.FOCUS_DOWN);

                                    }
                                });

                                pause_state_print = 1;

                            }

                            Thread.sleep(500);

                        }
                    }
                }



                else
                {
                    for(int wait_count = 0; wait_count < Integer.parseInt(temp1.Info_Delay);wait_count++)
                    {
                        Thread.sleep(1000);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                display += "Waiting" + '\n'+'\n' ;
                                _screen.setMovementMethod(new ScrollingMovementMethod());
                                _screen.setText(display);
                                _ScrollView1.fullScroll(ScrollView.FOCUS_DOWN);
                            }
                        });

                        while(pause_state)
                        {

                            if(pause_state_print == 0)
                            {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        display +=  "Test has been paused" + '\n';
                                        _screen.setMovementMethod(new ScrollingMovementMethod());
                                        _screen.setText(display);
                                        _btn_pause.setText("Resume");
                                        _ScrollView1.fullScroll(ScrollView.FOCUS_DOWN);

                                    }
                                });

                                pause_state_print = 1;

                            }

                            Thread.sleep(500);

                        }

                    }
                }



            }

            //No need now , will open it after some time
            /*
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    display +=  "Final_Result" + '\n';

                    for (String s:Final_Result_list) {

                        display +=  s + '\n';
                        _screen.setMovementMethod(new ScrollingMovementMethod());
                        _screen.setText(display);

                    }

                }


            });

            */

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    display += "***** Test Completed *****" + '\n'+'\n' ;
                    _screen.setText(display);


                    display += "****************" + '\n'+'\n' + " ************ " + '\n'+'\n' ;;
                    _screen.setMovementMethod(new ScrollingMovementMethod());
                    _screen.setText(display);

                    _screen.setMovementMethod(new ScrollingMovementMethod());
                    _ScrollView1.fullScroll(ScrollView.FOCUS_DOWN);
                }
            });

            // Going to Save result in text file
            String Save_Result_Time = (DateFormat.format("dd-MM-yyyy hh:mm:ss", new java.util.Date()).toString()).replaceAll("-","_").replaceAll(" ","_").replaceAll(":","_");


            File file = new File("/sdcard/TestConnect/", "Results");
            boolean Create_Result_folder_result = file.mkdirs();

            String filename = "/sdcard/TestConnect/Results/" + Save_Result_Time + ".txt";

            FileOutputStream outputStream;
            outputStream = new FileOutputStream( new File(filename));
            for (String result_line:Final_Result_list) {

                outputStream.write(result_line.getBytes());
            }


            outputStream.close();


        }

        catch (Exception e){
            Log.e("TEST PING", "exception: " + e.getMessage());
            Log.e("TEST PING", "exception: " + e.toString());

        }
    }




}
