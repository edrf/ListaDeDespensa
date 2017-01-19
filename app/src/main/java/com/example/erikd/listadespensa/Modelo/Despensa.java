package com.example.erikd.listadespensa.Modelo;

/**
 * Created by erikd on 08/01/2017.
 */

public class Despensa {
    private String id;
    private String nombre;
    private int estado;
    private double precio;


    public Despensa() {
    }

    public Despensa(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Despensa(String id, String nombre, double precio, Integer estado) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.estado = estado;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int Estado) {
        this.estado = estado;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

}