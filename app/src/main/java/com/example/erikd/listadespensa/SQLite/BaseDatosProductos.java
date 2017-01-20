package com.example.erikd.listadespensa.SQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.provider.BaseColumns;

import com.example.erikd.listadespensa.SQLite.ContratoProductos.CabecerasPedido;
import com.example.erikd.listadespensa.SQLite.ContratoProductos.DetallesPedido;
import com.example.erikd.listadespensa.SQLite.ContratoProductos.Productos;
import com.example.erikd.listadespensa.SQLite.ContratoProductos.LugarCompras;

/**
 * Created by erikd on 19/01/2017.
 */

public class BaseDatosProductos extends SQLiteOpenHelper {

    private static final String NOMBRE_BASE_DATOS = "productos.db";

    private static final int VERSION_ACTUAL = 1;

    private final Context contexto;

    interface Tablas {
        String CABECERA_PEDIDO = "cabecera_pedido";
        String DETALLE_PEDIDO = "detalle_pedido";
        String PRODUCTO = "producto";
        String LUGAR = "lugar_compra";
    }

    interface Referencias {

        String ID_CABECERA_PEDIDO = String.format("REFERENCES %s(%s) ON DELETE CASCADE",
                Tablas.CABECERA_PEDIDO, CabecerasPedido.ID_CABECERA_PEDIDO);

        String ID_PRODUCTO = String.format("REFERENCES %s(%s)",
                Tablas.PRODUCTO, Productos.ID_PRODUCTO);

        String ID_LUGAR = String.format("REFERENCES %s(%s)",
                Tablas.LUGAR, LugarCompras.ID_LUGAR_COMPRA);


    }

    public BaseDatosProductos(Context contexto) {
        super(contexto, NOMBRE_BASE_DATOS, null, VERSION_ACTUAL);
        this.contexto = contexto;
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                db.setForeignKeyConstraintsEnabled(true);
            } else {
                db.execSQL("PRAGMA foreign_keys=ON");
            }
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "%s TEXT UNIQUE NOT NULL,%s DATETIME NOT NULL," +
                        "%s TEXT NOT NULL %s)",
                Tablas.CABECERA_PEDIDO, BaseColumns._ID,
                CabecerasPedido.ID_CABECERA_PEDIDO, CabecerasPedido.FECHA,
                CabecerasPedido.ID_LUGAR, Referencias.ID_LUGAR));

        db.execSQL(String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "%s TEXT NOT NULL %s,%s INTEGER NOT NULL CHECK (%s>0),%s INTEGER NOT NULL %s," +
                        "%s INTEGER NOT NULL,%s REAL NOT NULL,UNIQUE (%s,%s) )",
                Tablas.DETALLE_PEDIDO, BaseColumns._ID,
                DetallesPedido.ID_DETALLE_PEDIDO, Referencias.ID_CABECERA_PEDIDO,
                DetallesPedido.SECUENCIA, DetallesPedido.SECUENCIA,
                DetallesPedido.ID_PRODUCTO, Referencias.ID_PRODUCTO,
                DetallesPedido.CANTIDAD, DetallesPedido.PRECIO,
                DetallesPedido.ID_DETALLE_PEDIDO, DetallesPedido.SECUENCIA));

        db.execSQL(String.format("CREATE TABLE %s ( %s INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "%s TEXT NOT NULL UNIQUE,%s TEXT NOT NULL,%s REAL NOT NULL," +
                        "%s INTEGER NOT NULL CHECK(%s>=0) )",
                Tablas.PRODUCTO, BaseColumns._ID,
                Productos.ID_PRODUCTO, Productos.NOMBRE, Productos.PRECIO,
                Productos.EXISTENCIAS, Productos.EXISTENCIAS));

        db.execSQL(String.format("CREATE TABLE %s ( %s INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "%s TEXT NOT NULL UNIQUE,%s TEXT NOT NULL )",
                Tablas.LUGAR, BaseColumns._ID,
                LugarCompras.ID_LUGAR_COMPRA, LugarCompras.NOMBRE));

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + Tablas.CABECERA_PEDIDO);
        db.execSQL("DROP TABLE IF EXISTS " + Tablas.DETALLE_PEDIDO);
        db.execSQL("DROP TABLE IF EXISTS " + Tablas.PRODUCTO);
        db.execSQL("DROP TABLE IF EXISTS " + Tablas.LUGAR);

        onCreate(db);
    }
}
