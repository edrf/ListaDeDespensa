package com.example.erikd.listadespensa.SQLite;

import com.example.erikd.listadespensa.Modelo.CabeceraPedido;
import com.example.erikd.listadespensa.Modelo.Despensa;
import com.example.erikd.listadespensa.Modelo.DetallePedido;
import com.example.erikd.listadespensa.Modelo.Producto;
import com.example.erikd.listadespensa.Modelo.LugarCompra;

import com.example.erikd.listadespensa.SQLite.BaseDatosProductos.Tablas;
import com.example.erikd.listadespensa.SQLite.ContratoProductos.CabecerasPedido;
import com.example.erikd.listadespensa.SQLite.ContratoProductos.DetallesPedido;
import com.example.erikd.listadespensa.SQLite.ContratoProductos.Productos;
import com.example.erikd.listadespensa.SQLite.ContratoProductos.LugarCompras;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erikd on 19/01/2017.
 */

/**
 * Clase auxiliar que implementa a {@link BaseDatosProductos} para llevar a cabo el CRUD
 * sobre las entidades existentes.
 */

public final class OperacionesBaseDatos {

    private static BaseDatosProductos baseDatos;

    private static OperacionesBaseDatos instancia = new OperacionesBaseDatos();

    private OperacionesBaseDatos() {
    }

    public static OperacionesBaseDatos obtenerInstancia(Context contexto) {
        if (baseDatos == null) {
            baseDatos = new BaseDatosProductos(contexto);
        }
        return instancia;
    }


 //   El siguiente método obtendrá todas las cabeceras de pedidos que existan en la base de datos, haciendo un INNER JOIN
    // con 'LugarCompra'. El valor de retorno será Cursor.

    // [OPERACIONES_CABECERA_PEDIDO]
    public Cursor obtenerCabecerasPedidos() {
        SQLiteDatabase db = baseDatos.getReadableDatabase();

        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();

        builder.setTables(CABECERA_PEDIDO_JOIN_LUGAR_COMPRA);

        return builder.query(db, proyCabeceraPedido, null, null, null, null, null);
    }

    public Cursor obtenerCabeceraPorId(String id) {
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        String selection = String.format("%s=?", CabecerasPedido.ID_CABECERA_PEDIDO);
        String[] selectionArgs = {id};

        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(CABECERA_PEDIDO_JOIN_LUGAR_COMPRA);

        String[] proyeccion = {
                Tablas.CABECERA_PEDIDO + "." + CabecerasPedido.ID_CABECERA_PEDIDO,
                CabecerasPedido.FECHA,
                LugarCompras.NOMBRE};

        return builder.query(db, proyeccion, selection, selectionArgs, null, null, null);
    }



    public String insertarCabeceraPedido(CabeceraPedido pedido) {
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        // Generar Pk
        String idCabeceraPedido = CabecerasPedido.generarIdCabeceraPedido();

        ContentValues valores = new ContentValues();
        valores.put(CabecerasPedido.ID_CABECERA_PEDIDO, idCabeceraPedido);
        valores.put(CabecerasPedido.FECHA, pedido.fecha);
        valores.put(CabecerasPedido.ID_LUGAR, pedido.idLugarCompra);

        // Insertar cabecera
        db.insertOrThrow(Tablas.CABECERA_PEDIDO, null, valores);

        return idCabeceraPedido;
    }

    public boolean actualizarCabeceraPedido(CabeceraPedido pedidoNuevo) {
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(CabecerasPedido.FECHA, pedidoNuevo.fecha);
        valores.put(CabecerasPedido.ID_LUGAR, pedidoNuevo.idLugarCompra);

        String whereClause = String.format("%s=?", CabecerasPedido.ID_CABECERA_PEDIDO);
        String[] whereArgs = {pedidoNuevo.idCabeceraPedido};

        int resultado = db.update(Tablas.CABECERA_PEDIDO, valores, whereClause, whereArgs);

        return resultado > 0;
    }

    public boolean eliminarCabeceraPedido(String idCabeceraPedido) {
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        String whereClause = CabecerasPedido.ID_CABECERA_PEDIDO + "=?";
        String[] whereArgs = {idCabeceraPedido};

        int resultado = db.delete(Tablas.CABECERA_PEDIDO, whereClause, whereArgs);

        return resultado > 0;
    }
    // [/OPERACIONES_CABECERA_PEDIDO]

    // [OPERACIONES_DETALLE_PEDIDO]
    public Cursor obtenerDetallesPedido() {
        SQLiteDatabase db = baseDatos.getReadableDatabase();

        String sql = String.format("SELECT * FROM %s", Tablas.DETALLE_PEDIDO);

        return db.rawQuery(sql, null);
    }

    public Cursor obtenerDetallesPorIdPedido(String idCabeceraPedido) {
        SQLiteDatabase db = baseDatos.getReadableDatabase();

        String sql = String.format("SELECT * FROM %s WHERE %s=?",
                Tablas.DETALLE_PEDIDO, CabecerasPedido.ID_CABECERA_PEDIDO);

        String[] selectionArgs = {idCabeceraPedido};

        return db.rawQuery(sql, selectionArgs);

    }

    public String insertarDetallePedido(DetallePedido detalle) {
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(DetallesPedido.ID_DETALLE_PEDIDO, detalle.idCabeceraPedido);
        valores.put(DetallesPedido.SECUENCIA, detalle.secuencia);
        valores.put(DetallesPedido.ID_PRODUCTO, detalle.idProducto);
        valores.put(DetallesPedido.CANTIDAD, detalle.cantidad);
        valores.put(DetallesPedido.PRECIO, detalle.precio);

        db.insertOrThrow(Tablas.DETALLE_PEDIDO, null, valores);

        return String.format("%s#%d", detalle.idCabeceraPedido, detalle.secuencia);

    }

    public boolean actualizarDetallePedido(DetallePedido detalle) {
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(DetallesPedido.SECUENCIA, detalle.secuencia);
        valores.put(DetallesPedido.CANTIDAD, detalle.cantidad);
        valores.put(DetallesPedido.PRECIO, detalle.precio);

        String selection = String.format("%s=? AND %s=?",
                DetallesPedido.ID_DETALLE_PEDIDO, DetallesPedido.SECUENCIA);
        final String[] whereArgs = {detalle.idCabeceraPedido, String.valueOf(detalle.secuencia)};

        int resultado = db.update(Tablas.DETALLE_PEDIDO, valores, selection, whereArgs);

        return resultado > 0;
    }

    public boolean eliminarDetallePedido(String idCabeceraPedido, int secuencia) {
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        String selection = String.format("%s=? AND %s=?",
                DetallesPedido.ID_DETALLE_PEDIDO, DetallesPedido.SECUENCIA);
        String[] whereArgs = {idCabeceraPedido, String.valueOf(secuencia)};

        int resultado = db.delete(Tablas.DETALLE_PEDIDO, selection, whereArgs);

        return resultado > 0;
    }
    // [/OPERACIONES_DETALLE_PEDIDO]

    // [OPERACIONES_PRODUCTO]
    public Cursor obtenerProductos() {
        SQLiteDatabase db = baseDatos.getReadableDatabase();

        String sql = String.format("SELECT * FROM %s", Tablas.PRODUCTO);

        return db.rawQuery(sql, null);
    }

    public String insertarProducto(Producto producto) {
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        ContentValues valores = new ContentValues();
        // Generar Pk
        String idProducto = Productos.generarIdProducto();
        valores.put(Productos.ID_PRODUCTO, idProducto);
        valores.put(Productos.NOMBRE, producto.nombre);
        valores.put(Productos.PRECIO, producto.precio);
        valores.put(Productos.EXISTENCIAS, producto.existencias);

        db.insertOrThrow(Tablas.PRODUCTO, null, valores);

        return idProducto;

    }

    public boolean actualizarProducto(Producto producto) {
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(Productos.NOMBRE, producto.nombre);
        valores.put(Productos.PRECIO, producto.precio);
        valores.put(Productos.EXISTENCIAS, producto.existencias);

        String whereClause = String.format("%s=?", Productos.ID_PRODUCTO);
        String[] whereArgs = {producto.idProducto};

        int resultado = db.update(Tablas.PRODUCTO, valores, whereClause, whereArgs);

        return resultado > 0;
    }

    public boolean eliminarProducto(String idProducto) {
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        String whereClause = String.format("%s=?", Productos.ID_PRODUCTO);
        String[] whereArgs = {idProducto};

        int resultado = db.delete(Tablas.PRODUCTO, whereClause, whereArgs);

        return resultado > 0;
    }
    // [/OPERACIONES_PRODUCTO]



    // [OPERACIONES_LUGAR]
    public Cursor obtenerLugarCompra() {
        SQLiteDatabase db = baseDatos.getReadableDatabase();

        String sql = String.format("SELECT * FROM %s", Tablas.LUGAR);

        return db.rawQuery(sql, null);
    }

    public String insertarLugarCompra(LugarCompra lugarCompras) {
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        // Generar Pk
        String idFormaPago = LugarCompras.generarIdLugar();

        ContentValues valores = new ContentValues();
        valores.put(LugarCompras.ID_LUGAR_COMPRA, idFormaPago);
        valores.put(LugarCompras.NOMBRE, lugarCompras.nombre);

        return db.insertOrThrow(Tablas.LUGAR, null, valores) > 0 ? idFormaPago : null;
    }

    public boolean actualizarLugarCompra(LugarCompra lugarCompras) {
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(LugarCompras.NOMBRE, lugarCompras.nombre);

        String whereClause = String.format("%s=?", LugarCompras.ID_LUGAR_COMPRA);
        String[] whereArgs = {lugarCompras.idLugarCompra};

        int resultado = db.update(Tablas.LUGAR, valores, whereClause, whereArgs);

        return resultado > 0;
    }

    public boolean eliminarLugarCompra(String idLugarCompra) {
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        String whereClause = String.format("%s=?", LugarCompras.ID_LUGAR_COMPRA);
        String[] whereArgs = {idLugarCompra};

        int resultado = db.delete(Tablas.LUGAR, whereClause, whereArgs);

        return resultado > 0;
    }

    public SQLiteDatabase getDb() {
        return baseDatos.getWritableDatabase();
    }


    // [/OPERACIONES_LUGAR]


    private static final String CABECERA_PEDIDO_JOIN_LUGAR_COMPRA = "cabecera_pedido " +
            "INNER JOIN lugar_compra " +
            "ON cabecera_pedido.id_lugar = lugar_compra.id";


    private final String[] proyCabeceraPedido = new String[]{
            Tablas.CABECERA_PEDIDO + "." + CabecerasPedido.ID_CABECERA_PEDIDO,
            CabecerasPedido.FECHA,
            LugarCompras.NOMBRE};

}
