package com.example.erikd.listadespensa.Adaptador;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;

import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.erikd.listadespensa.Datos.DataBaseManagerDespensa;
import com.example.erikd.listadespensa.Dialogo.EditarProducto;
import com.example.erikd.listadespensa.Modelo.Despensa;
import com.example.erikd.listadespensa.R;

import java.util.List;

/**
 * Created by erikd on 08/01/2017.
 */

public class CartaDespensaAdapter extends RecyclerView.Adapter<CartaDespensaAdapter.CartaViewHolder> {

    private Context mainContext;
    private  List<Despensa> items;

    private DataBaseManagerDespensa managerDespensa;

    String Id;
    String Producto;
    double Precio;
    int Estado;

    public CartaDespensaAdapter(List<Despensa> items, Context contexto) {
        this.mainContext = contexto;
        this.items = items;

    }


    static class CartaViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener, View.OnCreateContextMenuListener, View.OnClickListener {
        // Campos respectivos de un item
        protected  TextView id;
        protected CheckedTextView nombre;
        protected  TextView precio;
        protected ImageView imagen;
        ItemLongClickListener itemLongClickListener;
        ItemClickListener itemClickListener;



        public CartaViewHolder(View view) {
            super(view);

            this.id = (TextView) view.findViewById(R.id.id_producto);
            this.nombre = (CheckedTextView) view.findViewById(R.id.producto);
            this.precio = (TextView) view.findViewById(R.id.precio);
            this.imagen = (ImageView) view.findViewById(R.id.imagen_menu);
            view.setOnLongClickListener(this);
            view.setOnCreateContextMenuListener(this);
            view.setOnClickListener(this);


        }

        public void setItemLongClickListener(ItemLongClickListener ic){
            this.itemLongClickListener = ic;
        }

        @Override
        public boolean onLongClick(View view) {
            this.itemLongClickListener.onLongClick(getLayoutPosition());
            return false;
        }



        public  void setItemClickListener(ItemClickListener ic){
            this.itemClickListener=ic;
        }

        @Override
        public void onClick(View view) {
            this.itemClickListener.onClick(view, getLayoutPosition());
        }




        @Override
        public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            menu.add(0, 0, 0, "Editar Producto");
            menu.add(0, 1, 0, "Eliminar Producto");

        }


    }

  //Primero creamos una interface para los eventos clic

    public interface ItemLongClickListener{
        void onLongClick(int pos);
    }


    public  interface  ItemClickListener{
        void onClick(View view, int pos);
    }


    /**
     * creamos la card o targeta
     * @param viewGroup
     * @param viewType
     * @return
     */
    @Override
    public CartaViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item, viewGroup, false);
        return new CartaViewHolder(v);
    }

    /**
     * Este metodo actualiza el RecyclerView.ViewHolder
     * @param viewHolder
     * @param position
     */
    @Override
    public void onBindViewHolder(final CartaViewHolder viewHolder, final int position) {
        final Despensa item = items.get(position);
        final EditarProducto mensaje = new EditarProducto();

        boolean b = (item.getEstado()!= 0);
        viewHolder.itemView.setTag(item);

        viewHolder.id.setText("Nº: "+item.getId());
        viewHolder.nombre.setText(item.getNombre());
        viewHolder.precio.setText("Precio: $" + item.getPrecio());
        viewHolder.nombre.setChecked(b);
        viewHolder.imagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mainContext, "Click IMAGEN " + item.getId(), Toast.LENGTH_SHORT).show();
            }
        });



        viewHolder.setItemLongClickListener(new ItemLongClickListener() {
            @Override
            public void onLongClick(int pos) {
                Id= item.getId();
                Producto=item.getNombre();
                Precio=item.getPrecio();
                Estado=item.getEstado();
            }
        });

         viewHolder.setItemClickListener(new ItemClickListener() {
             @Override
             public void onClick(View view, int pos) {
                 if (viewHolder.nombre.isChecked()) {
// set cheek mark drawable and set checked property to false
                     viewHolder.nombre.setChecked(false);
                 } else {
// set cheek mark drawable and set checked property to true
                     viewHolder.nombre.setChecked(true);
                 }
             }
         });

           /*      Id= item.getId();
                 Producto=item.getNombre();
                 Precio=item.getPrecio();
                 Estado=item.getEstado();

                 Toast.makeText(mainContext, "Mensaje", Toast.LENGTH_SHORT).show();


                 if (viewHolder.nombre.isChecked()) {
// set cheek mark drawable and set checked property to false
                     viewHolder.nombre.setChecked(false);
                 } else {
// set cheek mark drawable and set checked property to true
                     viewHolder.nombre.setChecked(true);
             } */




    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    public String obtenerId(){
       return Id;
    }

    public String obtenerNombre(){
        return Producto;
    }

    public  double obtenerPrecio(){
        return Precio;
    }

    public  int obtenerEstado(){
        return  Estado;
    }



    public void getSelectedContextMenuItem(MenuItem item){
     }




}