package com.utn.edu.myfirstapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import androidx.activity.EdgeToEdge;
import com.utn.edu.myfirstapplication.Interfaces.ApiService;
import com.utn.edu.myfirstapplication.Models.MaestroEmpleado;
import com.utn.edu.myfirstapplication.Remote.RetrofitClient;

public class MainActivity extends AppCompatActivity {

    private EditText txtNombre, txtApellido, txtEdad, txtDireccion, txtTelefono, txtEmail;
    private Button btnAgregar;

    private boolean modoEdicion = false;
    private int idAEditar = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // 1. Referenciar los componentes de la vista
        txtNombre = findViewById(R.id.txtNombre);
        txtApellido = findViewById(R.id.txtApellido);
        txtEdad = findViewById(R.id.txtEdad);
        txtDireccion = findViewById(R.id.txtDireccion);
        txtTelefono = findViewById(R.id.txtTelefono);
        txtEmail = findViewById(R.id.txtEmail);
        btnAgregar = findViewById(R.id.btnAgregar);

        // 2. Verificar si venimos de la lista para EDITAR
        if (getIntent().getBooleanExtra("esEdicion", false)) {
            modoEdicion = true;
            idAEditar = getIntent().getIntExtra("id", -1);

            // Llenamos los campos con los datos recibidos del Intent
            txtNombre.setText(getIntent().getStringExtra("nombre"));
            txtApellido.setText(getIntent().getStringExtra("apellido"));
            txtEdad.setText(String.valueOf(getIntent().getIntExtra("edad", 0)));
            txtDireccion.setText(getIntent().getStringExtra("direccion"));
            txtTelefono.setText(getIntent().getStringExtra("telefono"));
            txtEmail.setText(getIntent().getStringExtra("email"));

            btnAgregar.setText("Actualizar Datos");
        }

        // 3. Evento del botón principal (Guardar o Actualizar)
        btnAgregar.setOnClickListener(v -> {
            enviarDatos();
        });

        // 4. Botón para ir a la pantalla de consulta
        Button btnConsultar = findViewById(R.id.txtConsultarEmpleados);
        btnConsultar.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, ListarActivity.class);
            startActivity(i);
        });
    }

    private void limpiarCampos() {
        txtNombre.setText("");
        txtApellido.setText("");
        txtEdad.setText("");
        txtDireccion.setText("");
        txtTelefono.setText("");
        txtEmail.setText("");
        txtNombre.requestFocus();
    }

    private void enviarDatos() {
        String nombre = txtNombre.getText().toString().trim();
        String apellido = txtApellido.getText().toString().trim();
        String edadStr = txtEdad.getText().toString().trim();
        String direccion = txtDireccion.getText().toString().trim();
        String numero = txtTelefono.getText().toString().trim();
        String email = txtEmail.getText().toString().trim();

        // Validar campos vacíos
        if (nombre.isEmpty() || apellido.isEmpty() || edadStr.isEmpty() ||
                direccion.isEmpty() || numero.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, "⚠️ Completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        int edad = Integer.parseInt(edadStr);

        // IMPORTANTE: Crear el objeto base
        MaestroEmpleado empleado = new MaestroEmpleado(nombre, apellido, edad, direccion, numero, email);
        ApiService apiService = RetrofitClient.getApiService();

        if (modoEdicion) {
            // --- LÓGICA DE ACTUALIZAR ---
            empleado.setIdEmpleado(idAEditar); // Debes tener el método setIdEmpleado en tu modelo
            apiService.modificarEmpleado(empleado).enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.isSuccessful() && response.body() != null && response.body()) {
                        Toast.makeText(MainActivity.this, "✅ Actualizado", Toast.LENGTH_SHORT).show();
                        finish(); // Regresa a la lista
                    } else {
                        Toast.makeText(MainActivity.this, "❌ Error al actualizar", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "Error de red", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            // --- LÓGICA DE AGREGAR (La que ya tenías) ---
            apiService.agregarEmpleado(empleado).enqueue(new Callback<Integer>() {
                @Override
                public void onResponse(Call<Integer> call, Response<Integer> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(MainActivity.this, "✅ Guardado", Toast.LENGTH_SHORT).show();
                        limpiarCampos();
                    }
                }
                @Override
                public void onFailure(Call<Integer> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "Error de red", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}