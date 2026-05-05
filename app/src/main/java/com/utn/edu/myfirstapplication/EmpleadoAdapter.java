package com.utn.edu.myfirstapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.utn.edu.myfirstapplication.Models.MaestroEmpleado;
import com.utn.edu.myfirstapplication.Remote.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmpleadoAdapter extends RecyclerView.Adapter<EmpleadoAdapter.ViewHolder> {
    private List<MaestroEmpleado> lista;

    public EmpleadoAdapter(List<MaestroEmpleado> lista) { this.lista = lista; }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_empleado, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MaestroEmpleado emp = lista.get(position);
        holder.lbId.setText(String.valueOf(emp.getIdEmpleado()));
        holder.lbNombre.setText(emp.getNombre() + " " + emp.getApellido());
        holder.lbEmail.setText(emp.getEmail());

        // EVENTO: Mantener presionado para Eliminar
        holder.itemView.setOnLongClickListener(v -> {
            new androidx.appcompat.app.AlertDialog.Builder(v.getContext())
                    .setTitle("Eliminar Empleado")
                    .setMessage("¿Estás seguro de que deseas eliminar a " + emp.getNombre() + "?")
                    .setPositiveButton("Sí, eliminar", (dialog, which) -> {
                        eliminarDeApi(emp.getIdEmpleado(), position, v.getContext());
                    })
                    .setNegativeButton("Cancelar", null)
                    .show();
            return true;
        });

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), MainActivity.class);
            // Pasamos los datos del empleado para que el formulario se llene solo
            intent.putExtra("id", emp.getIdEmpleado());
            intent.putExtra("nombre", emp.getNombre());
            intent.putExtra("apellido", emp.getApellido());
            intent.putExtra("edad", emp.getEdad());
            intent.putExtra("direccion", emp.getDireccion());
            intent.putExtra("telefono", emp.getNumero());
            intent.putExtra("email", emp.getEmail());
            intent.putExtra("esEdicion", true); // Bandera para saber que vamos a editar
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() { return lista.size(); }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView lbNombre, lbEmail, lbId;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            lbNombre = itemView.findViewById(R.id.lbNombreCompleto);
            lbEmail = itemView.findViewById(R.id.lbEmail);
            lbId = itemView.findViewById(R.id.lbId);
        }
    }
    private void eliminarDeApi(int id, int posicion, Context context) {
        RetrofitClient.getApiService().eliminarEmpleado(id).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful() && response.body()) {
                    lista.remove(posicion); // Lo quitamos de la lista local
                    notifyItemRemoved(posicion); // Animación de borrado
                    Toast.makeText(context, "Empleado eliminado", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast.makeText(context, "Error al eliminar", Toast.LENGTH_SHORT).show();
            }
        });
    }
}