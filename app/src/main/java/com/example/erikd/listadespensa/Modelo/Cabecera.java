package com.example.erikd.listadespensa.Modelo;

/**
 * Created by erikd on 20/01/2017.
 */

public class Cabecera {

    private String idCabecera;
    private String titulo;
    private String latitud;
    private String longitud;
    private String fecha;

    public Cabecera(String idCabecera, String titulo, String latitud, String longitud , String fecha) {
        this.idCabecera = idCabecera;
        this.titulo = titulo;
        this.latitud = latitud;
        this.longitud = longitud;
        this.fecha = fecha;
    }

    public Cabecera() {

    }


    public String getIdCabecera() {
        return idCabecera;
    }

    public void setIdCabecera(String idCabecera) {
        this.idCabecera = idCabecera;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
