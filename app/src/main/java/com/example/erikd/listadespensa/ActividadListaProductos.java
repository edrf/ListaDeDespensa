package com.example.erikd.listadespensa;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.DatabaseUtils;
import android.os.AsyncTask;
import android.speech.RecognizerIntent;

import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.AdapterView;
import android.widget.Toast;

import com.example.erikd.listadespensa.Adaptador.CartaDespensaAdapter;
import com.example.erikd.listadespensa.Datos.DataBaseManagerDespensa;
import com.example.erikd.listadespensa.Dialogo.EditarProducto;
import com.example.erikd.listadespensa.Dialogo.InsertarProducto;
import com.example.erikd.listadespensa.Modelo.CabeceraPedido;
import com.example.erikd.listadespensa.Modelo.Despensa;
import com.example.erikd.listadespensa.Modelo.DetallePedido;
import com.example.erikd.listadespensa.Modelo.LugarCompra;
import com.example.erikd.listadespensa.Modelo.Producto;
import com.example.erikd.listadespensa.SQLite.OperacionesBaseDatos;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ActividadListaProductos extends AppCompatActivity implements InsertarProducto.OnInsertarResgistroListener, EditarProducto.OnActualizarResgistroListener {

private DataBaseManagerDespensa managerDespensa;

private List<Despensa> listaItemsDespensa;
private RecyclerView recycler;
private RecyclerView.LayoutManager lManager;
private CartaDespensaAdapter adapter;

private String NombreProducto;
private String Precio;
private  String Id;
private  String Estado;

protected static final int RESULT_SPEECH = 1;



@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_lista_productos);

        managerDespensa= new DataBaseManagerDespensa(this);

        inicializarRecicler();

        com.getbase.floatingactionbutton.FloatingActionButton fabTexto = (com.getbase.floatingactionbutton.FloatingActionButton) findViewById(R.id.accion_teclado);
        fabTexto.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
        NuevoProducto(view);

        }
        });


        com.getbase.floatingactionbutton.FloatingActionButton fabVoz = (com.getbase.floatingactionbutton.FloatingActionButton) findViewById(R.id.accion_voz);
        fabVoz.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View view) {
        Intent intent = new Intent(
        RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, Locale.getDefault());

        try {
        startActivityForResult(intent, RESULT_SPEECH);
        //Text.setText("");
        } catch (ActivityNotFoundException a) {
        Toast.makeText(getApplicationContext(),
        "Su dispositivo no es compatible con Reconocimiento de voz",
        Toast.LENGTH_SHORT).show();
        }
        }
        });
        }

@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
        case RESULT_SPEECH: {
        if (resultCode == RESULT_OK && null != data) {

        ArrayList<String> text = data
        .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
        NombreProducto = text.get(0);
        managerDespensa.insertar_4parametros(null, NombreProducto,"","");
        recargarRecicler();

        }
        break;
        }

        }
        }

private void NuevoProducto(View view) {
        InsertarProducto mensaje = new InsertarProducto();
        mensaje.show(getSupportFragmentManager(), "Main Activity");
        }

private void ActualizaProducto(View view) {
        EditarProducto mensaje = new  EditarProducto();
        mensaje.show(getSupportFragmentManager(), "Main Activity");
        }


        public void inicializarRecicler() {

        listaItemsDespensa = managerDespensa.getCursosList();

        // Obtener el Recycler
        recycler = (RecyclerView) findViewById(R.id.recyclerview1);
        recycler.setHasFixedSize(true);


        // Usar un administrador para LinearLayout
        lManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(lManager);

        // Crear un nuevo adaptador
        adapter = new CartaDespensaAdapter(listaItemsDespensa, this);
        recycler.setAdapter(adapter);


        recycler.setItemAnimator(new  DefaultItemAnimator());

        // registerForContextMenu(recycler);

        }


public void recargarRecicler() {
        //cargar datos
        listaItemsDespensa = managerDespensa.getCursosList();
        // Crear un nuevo adaptador
        adapter = new CartaDespensaAdapter(listaItemsDespensa, this);
        recycler.setAdapter(adapter);
        recycler.setItemAnimator(new DefaultItemAnimator());

        }


@Override
protected void onDestroy() {

        managerDespensa.cerrar();

        super.onDestroy();
        }


@Override
protected void onResume() {
        super.onResume();
        this.setTitle(getString(R.string.titulo_app));
        }

@Override
public void iniciarListener_insertar() {
        managerDespensa.insertar_4parametros(null, NombreProducto, Precio, "");
        recargarRecicler();
        }

@Override
public void iniciarListener_actualizar() {
        managerDespensa.actualizar_4parametros(Id, NombreProducto, Precio, "");
        recargarRecicler();
        }


public void capturarParametros(String nombre , String precio){
        NombreProducto = nombre;
        Precio = precio;
        }

public void  capturarId(String id){
        Id = id;
        }


@Override
public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu2,menu);
        return true;
        }

@Override
public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
        case R.id.opcion_actualizar:
        Toast.makeText( this,  R.string.lista_actualizada  , Toast.LENGTH_LONG).show();
        recargarRecicler();
        return true;

        case R.id.opcion_borrar_todo:
        Toast.makeText( this,  R.string.borrando_lista  , Toast.LENGTH_LONG).show();
        managerDespensa.eliminarTodo();
        recargarRecicler();
        return true;

        }
        return false;
        }

@Override
public boolean onContextItemSelected(MenuItem item) {
        Id = adapter.obtenerId();
        NombreProducto = adapter.obtenerNombre();
        Precio = String.valueOf(adapter.obtenerPrecio());
        Estado = String.valueOf(adapter.obtenerEstado());

        switch (item.getItemId()){
        case 0:
        ActualizaProducto(getCurrentFocus());
        managerDespensa.actualizar_4parametros(Id, NombreProducto, Precio, Estado);
        recargarRecicler();
        Toast.makeText( this, "Editar Producto " + Id + " " + NombreProducto, Toast.LENGTH_LONG).show();
        return true;

        case 1:
        managerDespensa.eliminar(Id);
        recargarRecicler();
        Toast.makeText( this,  "Se eliminó el Producto "+ Id + " " + NombreProducto, Toast.LENGTH_LONG).show();
        return true;

        }
        return false;
        }



        }
