package com.maykoll.integracionv5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText email, pass;
    ImageView next;
    TextView register;

    DBInterface dbInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Base de datos
        dbInterface = new DBInterface(this);
        dbInterface.abre();

        SignIn();
        GotoSignUp();

    }

    public void SignIn() {
        email = findViewById(R.id.inicio_email);
        pass = findViewById(R.id.inicio_pass);
        next = findViewById(R.id.inicio_next);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String correo = email.getText().toString();
                String password = pass.getText().toString();
                if (correo.equals("") || password.equals("")) {
                    Toast.makeText(MainActivity.this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
                } else {
                    boolean checkuserpass = dbInterface.checkusernamepassword(correo, password);
                    if (checkuserpass == true) {
                        Toast.makeText(MainActivity.this, "Usuario válido", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getBaseContext(), Formulario.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(MainActivity.this, "Credenciales no Válidas", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    public void GotoSignUp() {
        register = findViewById(R.id.inicio_newuser);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), sign_up.class);
                startActivity(intent);
                finish();
            }
        });
    }
}