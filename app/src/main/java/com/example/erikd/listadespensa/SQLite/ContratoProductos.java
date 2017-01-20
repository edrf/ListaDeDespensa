package com.example.erikd.listadespensa.SQLite;

import java.util.UUID;

/**
 * Created by erikd on 19/01/2017.
 */

public class ContratoProductos {

    interface ColumnasCabeceraPedido {
        String ID_CABECERA_PEDIDO = "id";
        String FECHA = "fecha";
        String ID_LUGAR = "id_lugar";
    }

    interface ColumnasDetallePedido {
        String ID_DETALLE_PEDIDO = "id";
        String SECUENCIA = "secuencia";
        String ID_PRODUCTO = "id_producto";
        String CANTIDAD = "cantidad";
        String PRECIO = "precio";
    }

    interface ColumnasProducto {
        String ID_PRODUCTO = "id";
        String NOMBRE = "nombre";
        String PRECIO = "precio";
        String EXISTENCIAS = "existencias";
    }

    interface ColumnasLugarCompra {
        String ID_LUGAR_COMPRA = "id";
        String NOMBRE = "nombre";
    }


    public static class CabecerasPedido implements ColumnasCabeceraPedido {
        public static String generarIdCabeceraPedido() {
            return "CP-" + UUID.randomUUID().toString();
        }
    }

    public static class DetallesPedido implements ColumnasDetallePedido {
        // Métodos auxiliares
    }

    public static class Productos implements ColumnasProducto{
        public static String generarIdProducto() {
            return "PRO-" + UUID.randomUUID().toString();
        }
    }
    public static class LugarCompras implements ColumnasLugarCompra{
        public static String generarIdLugar() {
            return "LC-" + UUID.randomUUID().toString();
        }
    }


    private ContratoProductos() {
    }
}
