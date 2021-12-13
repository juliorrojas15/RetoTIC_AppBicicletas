package com.app.retotic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class FormActivity extends AppCompatActivity {

    TextView otvTexto;
    String nameExtra = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        otvTexto = (TextView) findViewById(R.id.tvTexto);

        Intent intent = getIntent();
        nameExtra = intent.getStringExtra("nameExtra");

        otvTexto.setText(nameExtra);
    }
}