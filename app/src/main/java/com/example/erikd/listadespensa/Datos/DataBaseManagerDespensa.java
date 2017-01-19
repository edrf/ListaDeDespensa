package com.example.erikd.listadespensa.Datos;

        import android.content.ContentValues;
        import android.content.Context;
        import android.database.Cursor;
        import android.util.Log;

        import java.util.ArrayList;
        import java.util.List;

        import com.example.erikd.listadespensa.Modelo.Despensa;

public class DataBaseManagerDespensa extends DataBaseManager {

    private static final String NOMBRE_TABLA="productos";

    private static final String CN_ID="_id";
    private static final String CN_NOMBRE="nombre";
    private static final String CN_PRECIO="precio";
    private static final String CN_ESTADO="estado";


    public static final String CREATE_TABLE = "create table " + NOMBRE_TABLA + " ("
            + CN_ID + " integer PRIMARY KEY AUTOINCREMENT, "
            + CN_NOMBRE + " text NOT NULL, "
            + CN_PRECIO + " DECIMAL(10,5) NULL,"
            + CN_ESTADO + " INTEGER DEFAULT 0"
            + ");";





    public DataBaseManagerDespensa(Context ctx) {
        super(ctx);
    }


    @Override
    public void cerrar() {
        super.getDb().close();
    }

    @Override
    public void insertar_4parametros(String id, String nombre, String precio, String estado) {
        Log.d("productos_insertar", super.getDb().insert(NOMBRE_TABLA, null, generarContentValues(id, nombre, precio, estado)) + "");
    }

    @Override
    public void actualizar_4parametros(String id, String nombre, String precio, String estado) {
        ContentValues valores = new ContentValues();
        valores.put(CN_ID, id);
        valores.put(CN_NOMBRE, nombre);
        valores.put(CN_PRECIO, precio);
        valores.put(CN_ESTADO, estado);

        String [] args= new String[]{id};


        Log.d("productos_actualizar", super.getDb().update(NOMBRE_TABLA, valores, "_id=?", args)+"");
    }


    private ContentValues generarContentValues(String id, String name,  String precio, String estado) {
        ContentValues valores = new ContentValues();
        valores.put(CN_ID, id);
        valores.put(CN_NOMBRE, name);
        valores.put(CN_PRECIO, precio);
        valores.put(CN_ESTADO, estado);


        return valores;
    }





    @Override
    public void eliminar(String id) {

        super.getDb().delete(NOMBRE_TABLA, CN_ID + "=?", new String[]{id});
    }

    @Override
    public void eliminarTodo() {

        super.getDb().execSQL("DELETE FROM "+ NOMBRE_TABLA+";");
        Log.d("cursos_eliminar", "Datos borrados");

    }

    @Override
    public Cursor cargarCursor() {
        String [] columnas= new String[]{CN_ID, CN_NOMBRE, CN_PRECIO, CN_ESTADO};


        return super.getDb().query(NOMBRE_TABLA,columnas,null,null,null,null,null );
    }

    @Override
    Boolean compruebaRegistro(String id) {

        boolean esta=true;

        Cursor resultSet= super.getDb().rawQuery("Select * from " + NOMBRE_TABLA + " WHERE " + CN_ID + "=" + id, null);

        if(resultSet.getCount()<=0)
            esta=false;
        else
            esta=true;

        return esta;

    }

    public List<Despensa> getCursosList(){
        List<Despensa>  list= new ArrayList<>();

        Cursor c= cargarCursor();


        while (c.moveToNext()){
            Despensa despensa = new Despensa();

            despensa.setId(c.getString(0));
            despensa.setNombre(c.getString(1));
            despensa.setPrecio(c.getDouble(2));
            despensa.setEstado(c.getInt(3));
            list.add(despensa);
        }

        return list;

    }





}
