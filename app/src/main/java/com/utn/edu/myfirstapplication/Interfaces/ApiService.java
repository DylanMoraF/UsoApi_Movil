package com.utn.edu.myfirstapplication.Interfaces;

import com.utn.edu.myfirstapplication.Models.MaestroEmpleado;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface ApiService {
    @POST("api/MaestroEmpleado/Agregar")
    Call<Integer> agregarEmpleado(@Body MaestroEmpleado empleado);

    @GET("api/MaestroEmpleado/Listar")
    Call<List<MaestroEmpleado>> listarEmpleados();

    @PUT("api/MaestroEmpleado/Modificar")
    Call<Integer> modificarEmpleado(@Body MaestroEmpleado empleado);

    @DELETE("api/MaestroEmpleado/Eliminar")
    Call<Boolean> eliminarEmpleado(@Query("id") int id);
}
