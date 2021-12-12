package com.AriqJmartFA;

import static java.lang.Thread.sleep;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //startActivity(new Intent(SplashScreen.this, LoginActivity.class));

        /*
        try {
            sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // close splash activity
        finish();

         */

        Thread thread = new Thread() {

            @Override
            public void run() {
                try {
                    sleep(3000);
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
                finally {
                    Intent mainIntent=new Intent(SplashScreen.this, LoginActivity.class);

                    startActivity(mainIntent);
                }
            }
        };

        thread.start();



    }

    @Override
    protected void onPause(){
        super.onPause();
        finish();
    }
}