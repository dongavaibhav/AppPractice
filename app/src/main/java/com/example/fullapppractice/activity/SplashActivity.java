package com.example.fullapppractice.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.fullapppractice.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Thread timer= new Thread() {
            public void run() {
                try {
                    sleep(3000);
                }
                catch (InterruptedException e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }
                finally {
                    Intent nextactivity = new Intent(SplashActivity.this, HomeActivity.class);
                    startActivity(nextactivity);
                }
            }
        };
        timer.start();
    }
}
