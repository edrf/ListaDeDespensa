package com.example.erikd.listadespensa.Datos;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by erikd on 08/01/2017.
 */

public abstract class DataBaseManager {

    private DbHelper helper;
    private SQLiteDatabase db;


    public DataBaseManager(Context ctx) {
        helper= new DbHelper(ctx);
        db=helper.getWritableDatabase();

    }


    public void cerrar(){
        db.close();
    }


    abstract void insertar_4parametros(String id, String nombre, String precio, String estado);
    abstract void actualizar_4parametros(String id, String nombre, String precio, String estado);

    abstract void insertar_5parametrosCab(String id, String titulo, String fecha, String latitud, String longitud);
    abstract void actualizar_5parametrosCab(String id, String titulo, String fecha, String latitud, String longitud);


    abstract public void eliminar(String id);
    abstract public void eliminarTodo();
    abstract public Cursor cargarCursor();
    abstract Boolean compruebaRegistro(String id);


    abstract public void eliminarCab(String id);
    abstract public Cursor cargarCursorCabecera();



    /**
     * GET Y SET
     * @return
     */
    public DbHelper getHelper() {
        return helper;
    }

    public void setHelper(DbHelper helper) {
        this.helper = helper;
    }

    public SQLiteDatabase getDb() {
        return db;
    }

    public void setDb(SQLiteDatabase db) {
        this.db = db;
    }
}