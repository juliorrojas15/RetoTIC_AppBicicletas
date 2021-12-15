package com.app.retotic.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;

import com.app.retotic.MainActivity;
import com.app.retotic.R;

public class Splash extends AppCompatActivity {

    private int time = 5000;
    private ProgressBar opbarbarra;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        opbarbarra = (ProgressBar) findViewById(R.id.pbarBarra);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                opbarbarra.incrementProgressBy(20);
            }
        };
        for (int i = 0; i < 5; i++) {
            new Handler().postDelayed(runnable,(i+1)*1000);
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Splash.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        },time);
    }
}