package com.example.testdelcaimanv2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ActivityPreguntas extends AppCompatActivity {

    List<Preguntas.Pregunta> preguntas;
    Retrofit retrofit;
    CaimanAPI caimanAPI;
    String URL = "http://oscar.caiman.dam.inspedralbes.cat:3008/";
    LinearLayout layout;
    Button btnA, btnB, btnC, btnD, btnEnviar;
    TextView tvEnunciado;
    int posicion = 0;
    int aciertos = 0;
    String nombre = "";
    int opcionSeleccionada = 0;
    int colorVerde;
    int colorGris;
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preguntas);


        sharedPreferences = getSharedPreferences("MisPreferencias", MODE_PRIVATE);
        nombre = sharedPreferences.getString("nombre","");
        Log.i("nombre",nombre);
        colorVerde = getResources().getColor(R.color.verde);
        colorGris = getResources().getColor(R.color.gris);
        btnA = findViewById(R.id.btnA);
        btnB = findViewById(R.id.btnB);
        btnC = findViewById(R.id.btnC);
        btnD = findViewById(R.id.btnD);

        btnA.setBackgroundColor(colorGris);
        btnB.setBackgroundColor(colorGris);
        btnC.setBackgroundColor(colorGris);
        btnD.setBackgroundColor(colorGris);
        btnEnviar = findViewById(R.id.btnEnviar);
        tvEnunciado = findViewById(R.id.tvEnunciado);
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                comprobarCorrecta();
                posicion++;
                mostrarPregunta();

                btnA.setBackgroundColor(colorGris);
                btnB.setBackgroundColor(colorGris);
                btnC.setBackgroundColor(colorGris);
                btnD.setBackgroundColor(colorGris);
            }
        });
        btnA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opcionSeleccionada = 1;
                btnA.setBackgroundColor(colorVerde);
                btnB.setBackgroundColor(colorGris);
                btnC.setBackgroundColor(colorGris);
                btnD.setBackgroundColor(colorGris);
            }
        });
        btnB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opcionSeleccionada = 2;
                btnA.setBackgroundColor(colorGris);
                btnB.setBackgroundColor(colorVerde);
                btnC.setBackgroundColor(colorGris);
                btnD.setBackgroundColor(colorGris);
            }
        });
        btnC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opcionSeleccionada = 3;
                btnA.setBackgroundColor(colorGris);
                btnB.setBackgroundColor(colorGris);
                btnC.setBackgroundColor(colorVerde);
                btnD.setBackgroundColor(colorGris);
            }
        });
        btnD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opcionSeleccionada = 4;
                btnA.setBackgroundColor(colorGris);
                btnB.setBackgroundColor(colorGris);
                btnC.setBackgroundColor(colorGris);
                btnD.setBackgroundColor(colorVerde);
            }
        });
        retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        caimanAPI = retrofit.create(CaimanAPI.class);

        Call<List<Preguntas.Pregunta>> call = caimanAPI.getPreguntas();

        call.enqueue(new Callback<List<Preguntas.Pregunta>>() {
            @Override
            public void onResponse(Call<List<Preguntas.Pregunta>> call, Response<List<Preguntas.Pregunta>> response) {
                if(response.isSuccessful()){
                    preguntas = response.body();
                    Collections.shuffle(preguntas);

                    mostrarPregunta();


                }else{
                    Log.d("error onResponse","no ha funcionado");
                }
            }

            @Override
            public void onFailure(Call<List<Preguntas.Pregunta>> call, Throwable t) {

                Log.d("error onFailure","error onFailure"+ t.getMessage());
            }
        });
    }

    public void mostrarPregunta(){
        if (preguntas != null && posicion < preguntas.size()) {
            Preguntas.Pregunta pregunta = preguntas.get(posicion);
            tvEnunciado.setText(pregunta.enunciado);
            btnA.setText(pregunta.opcionA.toString());
            btnB.setText(pregunta.opcionB.toString());
            btnC.setText(pregunta.opcionC.toString());
            btnD.setText(pregunta.opcionD.toString());
        } else {

            insertCaiman();
            // Todas las preguntas han sido respondidas
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Fin del test");
            builder.setMessage(nombre + " ha acertado " + aciertos + " preguntas");
            builder.setPositiveButton("Cerrar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // Aquí puedes agregar cualquier acción adicional que desees
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.clear();
                    editor.apply();
                    dialog.dismiss();
                    Intent intent = new Intent(ActivityPreguntas.this, MainActivity.class);
                    startActivity(intent);


                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }
    public void comprobarCorrecta(){
        if(opcionSeleccionada == preguntas.get(posicion).opcionCorrecta){
            aciertos++;
        }
    }
    public void insertCaiman(){

        Caimanes.Caiman caiman = new Caimanes.Caiman();
        caiman.setNombre(nombre);
        caiman.setAciertos(aciertos);
        caiman.setRango(decidirRango());

        Call<Void> call = caimanAPI.insertCaiman(caiman);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    Toast.makeText(ActivityPreguntas.this,"Has sido añadido a la pirámide",Toast.LENGTH_LONG).show();

                }else{
                    Log.e("error",response.message());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("error",call.toString());
            }
        });
    }

    public String decidirRango(){
        String result ="";
        if(aciertos >= 28){
            result = "Caimán";
        }else if(aciertos >=25){
            result = "Trucho";
        }else if(aciertos >= 21){
            result = "WaWaWa";
        }else if(aciertos>=12){
            result = "Poppy";
        }else{
            result = "Sapo";
        }
        return result;
    }
}
