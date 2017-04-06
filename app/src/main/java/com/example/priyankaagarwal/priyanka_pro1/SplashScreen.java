package com.example.priyankaagarwal.priyanka_pro1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Thread myThread = new Thread(){

            @Override
            public void run(){
                try {

                    sleep(2000);
                    Intent intent1 = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent1);
                    finish();

                }

                catch (Exception e){
                    e.printStackTrace();
                }


            }
        };

        myThread.start();
    }
}
