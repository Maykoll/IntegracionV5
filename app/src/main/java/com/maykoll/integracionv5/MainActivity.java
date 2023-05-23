package com.maykoll.integracionv5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btn_sigup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_sigup = findViewById(R.id.btn);

        btn_sigup.setOnClickListener(view -> {
            Intent intent = new Intent(this, Formulario.class);
            startActivity(intent);

        });


    }
}