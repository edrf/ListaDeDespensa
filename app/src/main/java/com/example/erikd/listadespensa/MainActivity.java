package com.example.erikd.listadespensa;

import android.database.DatabaseUtils;
import android.os.AsyncTask;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.erikd.listadespensa.Adaptador.CartaDespensaAdapter;
import com.example.erikd.listadespensa.Modelo.CabeceraPedido;
import com.example.erikd.listadespensa.Modelo.Despensa;
import com.example.erikd.listadespensa.Modelo.DetallePedido;
import com.example.erikd.listadespensa.Modelo.LugarCompra;
import com.example.erikd.listadespensa.Modelo.Producto;
import com.example.erikd.listadespensa.SQLite.ContratoProductos;
import com.example.erikd.listadespensa.SQLite.OperacionesBaseDatos;

import java.util.Calendar;
import java.util.List;


public class MainActivity extends AppCompatActivity{

    OperacionesBaseDatos datos;

    private List<ContratoProductos> listaItemsProductos;
    private RecyclerView recycler;
    private RecyclerView.LayoutManager lManager;
    private CartaDespensaAdapter adapter;


    public class TareaPruebaDatos extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {

            // [INSERCIONES]
            String fechaActual = Calendar.getInstance().getTime().toString();

            try {

                datos.getDb().beginTransaction();


                // Inserción Lugar Compra
                String lugarCompra1 = datos.insertarLugarCompra(new LugarCompra(null, "WalMart"));
                String lugarCompra2 = datos.insertarLugarCompra(new LugarCompra(null, "Chedraui"));

                // Inserción Productos
                String producto1 = datos.insertarProducto(new Producto(null, "Manzana unidad", 2, 100));
                String producto2 = datos.insertarProducto(new Producto(null, "Pera unidad", 3, 230));
                String producto3 = datos.insertarProducto(new Producto(null, "Guayaba unidad", 5, 55));
                String producto4 = datos.insertarProducto(new Producto(null, "Maní unidad", 3.6f, 60));

                // Inserción Pedidos
                String pedido1 = datos.insertarCabeceraPedido(
                        new CabeceraPedido(null, fechaActual,  lugarCompra1));
                String pedido2 = datos.insertarCabeceraPedido(
                        new CabeceraPedido(null, fechaActual, lugarCompra2));

                // Inserción Detalles
                datos.insertarDetallePedido(new DetallePedido(pedido1, 1, producto1, 5, 2));
                datos.insertarDetallePedido(new DetallePedido(pedido1, 2, producto2, 10, 3));
                datos.insertarDetallePedido(new DetallePedido(pedido2, 1, producto3, 30, 5));
                datos.insertarDetallePedido(new DetallePedido(pedido2, 2, producto4, 20, 3.6f));

                // Eliminación Pedido
                datos.eliminarCabeceraPedido(pedido1);


                datos.getDb().setTransactionSuccessful();
            } finally {
                datos.getDb().endTransaction();
            }

            // [QUERIES]

            Log.d("Lugar de compra", "Lugar de compra");
            DatabaseUtils.dumpCursor(datos.obtenerLugarCompra());
            Log.d("Productos", "Productos");
            DatabaseUtils.dumpCursor(datos.obtenerProductos());
            Log.d("Cabeceras de pedido", "Cabeceras de pedido");
            DatabaseUtils.dumpCursor(datos.obtenerCabecerasPedidos());
            Log.d("Detalles de pedido", "Detalles de pedido");
            DatabaseUtils.dumpCursor(datos.obtenerDetallesPedido());

            return null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getApplicationContext().deleteDatabase("pedidos.db");
        datos = OperacionesBaseDatos
                .obtenerInstancia(getApplicationContext());

        new MainActivity.TareaPruebaDatos().execute();
    }



}
