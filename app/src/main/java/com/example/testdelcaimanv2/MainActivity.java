package com.example.testdelcaimanv2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText etNombre;
    Button btnComenzar, btnVerPiramide;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNombre = findViewById(R.id.etNombre);
        btnComenzar = findViewById(R.id.btnComenzar);
        btnVerPiramide = findViewById(R.id.btnVerPiramide);

        btnComenzar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre = etNombre.getText().toString().trim();
                if(!nombre.isEmpty()){
                    sharedPreferences = getSharedPreferences("MisPreferencias", MODE_PRIVATE);
                    editor = sharedPreferences.edit();
                    editor.putString("nombre", nombre);
                    editor.apply();

                    Intent intent = new Intent(MainActivity.this, ActivityPreguntas.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(MainActivity.this,"Tienes que introducir un nombre, mendrugo",Toast.LENGTH_LONG).show();
                }
            }
        });

        btnVerPiramide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ActivityPiramide.class);
                startActivity(intent);
            }
        });
    }
}