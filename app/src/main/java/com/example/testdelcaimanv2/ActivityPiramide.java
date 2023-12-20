package com.example.testdelcaimanv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ActivityPiramide extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CaimanAdapter caimanAdapter;
    List<Caimanes.Caiman> caimanes;
    Button btnVolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_piramide);
        SharedPreferences sharedPreferences = getSharedPreferences("MisPreferencias", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        btnVolver = findViewById(R.id.btnVolver);
        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityPiramide.this, MainActivity.class);
                startActivity(intent);
            }
        });


        recyclerView = findViewById(R.id.recyclerViewCaimanes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        obtenerDatosCaimanes();

    }

    public void obtenerDatosCaimanes(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://oscar.caiman.dam.inspedralbes.cat:3008/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CaimanAPI caimanAPI = retrofit.create(CaimanAPI.class);
        Call<List<Caimanes.Caiman>> call = caimanAPI.getCaimanes();

        call.enqueue(new Callback<List<Caimanes.Caiman>>() {
            @Override
            public void onResponse(Call<List<Caimanes.Caiman>> call, Response<List<Caimanes.Caiman>> response) {
                if (response.isSuccessful()) {
                    caimanes = response.body();
                    caimanAdapter = new CaimanAdapter(ActivityPiramide.this, caimanes);
                    recyclerView.setAdapter(caimanAdapter);
                } else {
                    Toast.makeText(ActivityPiramide.this," error al llamar a los caimanes", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Caimanes.Caiman>> call, Throwable t) {
                Toast.makeText(ActivityPiramide.this, "Error de red", Toast.LENGTH_SHORT).show();
            }

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.piramide_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_back) {
            // Maneja el clic en el botón de retroceso aquí
            finish(); // Esto cierra la actividad y vuelve a la actividad anterior
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}