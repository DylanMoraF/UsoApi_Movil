package com.utn.edu.myfirstapplication.Models;

public class MaestroEmpleado {
    // No incluimos IdEmpleado porque tu API lo calcula con el Max() + 1
    private int idEmpleado;
    private String nombre;
    private String apellido;
    private Integer edad;
    private String direccion;
    private String numero; // Este mapea con el campo "Telefono" de tu vista
    private String email;

    public MaestroEmpleado(String nombre, String apellido, Integer edad, String direccion, String numero, String email) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.direccion = direccion;
        this.numero = numero;
        this.email = email;
    }

    // Getters y setters...

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
}
