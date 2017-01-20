package com.example.erikd.listadespensa.Modelo;

public class CabeceraPedido {

    public String idCabeceraPedido;

    public String fecha;

    public String idLugarCompra;

    public CabeceraPedido(String idCabeceraPedido, String fecha,
                          String idLugarCompra) {
        this.idCabeceraPedido = idCabeceraPedido;
        this.fecha = fecha;
        this.idLugarCompra = idLugarCompra;
    }
}
