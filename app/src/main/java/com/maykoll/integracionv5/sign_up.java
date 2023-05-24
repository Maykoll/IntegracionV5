package com.maykoll.integracionv5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class sign_up extends AppCompatActivity {

    EditText name, email, pass;
    ImageView next;
    DBInterface dbInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //Base de datos
        dbInterface = new DBInterface(this);
        dbInterface.abre();

        signUp();

    }

    public void signUp(){
        name = findViewById(R.id.R_name);
        email = findViewById(R.id.R_email);
        pass = findViewById(R.id.R_password);

        next = findViewById(R.id.R_next);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String new_name = name.getText().toString();
                String new_email = email.getText().toString();
                String new_password = pass.getText().toString();

                if (new_name.equals("") || new_email.equals("") || new_password.equals("")) {
                    Toast.makeText(sign_up.this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
                } else {
                    boolean checkuser = dbInterface.checkusername(new_email);
                    if (!checkuser) {
                        boolean insert = dbInterface.insertarUsuario(new_name, new_email, new_password);
                        if (insert == true) {
                            Toast.makeText(sign_up.this, "Resgistrado Correctamente", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getBaseContext(), Formulario.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(sign_up.this, "Registro Fallido", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(sign_up.this, "Ya existe el usuario!", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

    }
}