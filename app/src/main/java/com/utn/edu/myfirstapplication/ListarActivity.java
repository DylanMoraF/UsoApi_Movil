package com.utn.edu.myfirstapplication;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.utn.edu.myfirstapplication.Models.MaestroEmpleado;
import com.utn.edu.myfirstapplication.Remote.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar);

        RecyclerView rv = findViewById(R.id.rvEmpleados);
        // 1. SIN ESTO NO SE VE NADA
        rv.setLayoutManager(new LinearLayoutManager(this));

        RetrofitClient.getApiService().listarEmpleados().enqueue(new Callback<List<MaestroEmpleado>>() {
            @Override
            public void onResponse(Call<List<MaestroEmpleado>> call, Response<List<MaestroEmpleado>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<MaestroEmpleado> lista = response.body();

                    // LOG DE DIAGNÓSTICO: Revisa esto en la pestaña Logcat
                    Log.d("API_DEBUG", "Cantidad recibida: " + lista.size());

                    if (lista.size() > 0) {
                        EmpleadoAdapter adapter = new EmpleadoAdapter(lista);
                        rv.setAdapter(adapter);
                    } else {
                        Toast.makeText(ListarActivity.this, "La lista está vacía en la DB", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e("API_DEBUG", "Error en respuesta: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<MaestroEmpleado>> call, Throwable t) {
                Log.e("API_DEBUG", "Fallo total: " + t.getMessage());
            }
        });
    }
}