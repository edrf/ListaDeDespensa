package com.example.erikd.listadespensa.Datos;

        import android.content.ContentValues;
        import android.content.Context;
        import android.database.Cursor;
        import android.util.Log;

        import java.util.ArrayList;
        import java.util.List;

        import com.example.erikd.listadespensa.Modelo.Cabecera;
        import com.example.erikd.listadespensa.Modelo.Despensa;

public class DataBaseManagerDespensa extends DataBaseManager {

    private static final String TABLA_CABECERA="cabecera";
    private static final String CN_ID="_id";
    private static final String CN_TITULO="titulo";
    private static final String CN_LATITUD="latitud";
    private static final String CN_LONGITUD="longitud";
    private static final String CN_FECHA="fecha";

    private static final String TABLA_PRODUCTOS="productos";
    private static final String CN_ID_PRO="_id_pro";
    private static final String CN_ID_CAB="id_cab";
    private static final String CN_PRODUCTO="producto";
    private static final String CN_PRECIO="precio";
    private static final String CN_ESTADO="estado";


    public static final String CREATE_TABLE_CABECERA = "create table " + TABLA_CABECERA + " ("
            + CN_ID + " integer PRIMARY KEY AUTOINCREMENT, "
            + CN_TITULO + " text NOT NULL, "
            + CN_FECHA + " text NULL,"
            + CN_LATITUD + " text NULL,"
            + CN_LONGITUD+ " text NULL"
            + ");";




    public static final String CREATE_TABLE_PRODUCTOS = "create table " + TABLA_PRODUCTOS + " ("
            + CN_ID_PRO + " integer PRIMARY KEY AUTOINCREMENT, "
            + CN_PRODUCTO + " text NOT NULL, "
            + CN_PRECIO + " DECIMAL(10,5) NULL,"
            + CN_ESTADO + " INTEGER DEFAULT 0,"
            + CN_ID_CAB + " INTEGER,"
            + "FOREIGN KEY(" + CN_ID_CAB +") REFERENCES " + TABLA_CABECERA + " (" + CN_ID +")"
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
        Log.d("productos_insertar", super.getDb().insert(TABLA_PRODUCTOS, null, generarContentValues(id, nombre, precio, estado)) + "");
    }

    @Override
    void insertar_5parametrosCab(String id, String titulo, String fecha, String latitud, String longitud) {
        Log.d("productos_insertar", super.getDb().insert(TABLA_CABECERA, null, generarContentValuesCabecera(id, titulo, fecha, latitud, longitud)) + "");
    }

    @Override
    void actualizar_5parametrosCab(String id, String titulo, String fecha, String latitud, String longitud) {
        ContentValues valores = new ContentValues();
        valores.put(CN_ID, id);
        valores.put(CN_TITULO, titulo);
        valores.put(CN_FECHA, fecha);
        valores.put(CN_LATITUD, latitud);
        valores.put(CN_LONGITUD, longitud);


        String [] args= new String[]{id};


        Log.d("Cabecera_actualizar", super.getDb().update(TABLA_CABECERA, valores, "_id=?", args)+"");
    }

    @Override
    public void eliminarCab(String id) {
        super.getDb().delete(TABLA_CABECERA, CN_ID + "=?", new String[]{id});
    }

    @Override
    public void actualizar_4parametros(String id, String nombre, String precio, String estado) {
        ContentValues valores = new ContentValues();
        valores.put(CN_ID_PRO, id);
        valores.put(CN_PRODUCTO, nombre);
        valores.put(CN_PRECIO, precio);
        valores.put(CN_ESTADO, estado);

        String [] args= new String[]{id};


        Log.d("productos_actualizar", super.getDb().update(TABLA_PRODUCTOS, valores, "_id_pro=?", args)+"");
    }


    private ContentValues generarContentValues(String id, String name,  String precio, String estado) {
        ContentValues valores = new ContentValues();
        valores.put(CN_ID_PRO, id);
        valores.put(CN_PRODUCTO, name);
        valores.put(CN_PRECIO, precio);
        valores.put(CN_ESTADO, estado);

        return valores;
    }

    private ContentValues generarContentValuesCabecera(String id, String titulo,  String fecha, String latitud, String longitud) {
        ContentValues valores = new ContentValues();
        valores.put(CN_ID, id);
        valores.put(CN_TITULO, titulo);
        valores.put(CN_FECHA, fecha);
        valores.put(CN_LATITUD, latitud);
        valores.put(CN_LONGITUD, longitud);

        return valores;
    }

    @Override
    public void eliminar(String id) {

        super.getDb().delete(TABLA_PRODUCTOS, CN_ID_PRO + "=?", new String[]{id});
    }

    @Override
    public void eliminarTodo() {

        super.getDb().execSQL("DELETE FROM "+ TABLA_PRODUCTOS+";");
        Log.d("cursos_eliminar", "Datos borrados");

    }

    @Override
    public Cursor cargarCursor() {
        String [] columnas= new String[]{CN_ID_PRO, CN_PRODUCTO, CN_PRECIO, CN_ESTADO};


        return super.getDb().query(TABLA_PRODUCTOS,columnas,null,null,null,null,null );
    }


    @Override
    public Cursor cargarCursorCabecera() {
        String [] columnas= new String[]{CN_ID, CN_TITULO, CN_FECHA, CN_LATITUD, CN_LONGITUD};


        return super.getDb().query(TABLA_CABECERA,columnas,null,null,null,null,null );
    }


    @Override
    Boolean compruebaRegistro(String id) {

        boolean esta=true;

        Cursor resultSet= super.getDb().rawQuery("Select * from " + TABLA_PRODUCTOS + " WHERE " + CN_ID + "=" + id, null);

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


    public List<Cabecera> getCursosListCab(){
        List<Cabecera>  list= new ArrayList<>();

        Cursor c= cargarCursorCabecera();


        while (c.moveToNext()){
            Cabecera cabecera = new Cabecera();

            cabecera.setIdCabecera(c.getString(0));
            cabecera.setTitulo(c.getString(1));
            cabecera.setFecha(c.getString(2));
            cabecera.setLatitud(c.getString(3));
            cabecera.setLongitud(c.getString(4));
            list.add(cabecera);
        }

        return list;

    }





}
