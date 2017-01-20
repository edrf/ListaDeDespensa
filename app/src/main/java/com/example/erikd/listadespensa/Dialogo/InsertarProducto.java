package com.example.erikd.listadespensa.Dialogo;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.erikd.listadespensa.ActividadListaProductos;
import com.example.erikd.listadespensa.MainActivity;
import com.example.erikd.listadespensa.R;

/**
 * Created by erikd on 05/01/2017.
 */

public class InsertarProducto extends DialogFragment {

    public interface OnInsertarResgistroListener{
        void iniciarListener_insertar();
    }

    OnInsertarResgistroListener listernerInsertar;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogo = inflater.inflate(R.layout.dialogo_insertar, null);
        builder.setView(dialogo);

        builder.setTitle("Agrega un Producto");

        ImageButton btnAceptar = (ImageButton)dialogo.findViewById(R.id.btnAceptar);
        ImageButton btnCancelar = (ImageButton)dialogo.findViewById(R.id.btnCancelar);
        final EditText Producto = (EditText)dialogo.findViewById(R.id.edt_producto);
        final EditText Precio = (EditText)dialogo.findViewById(R.id.edt_precio);

        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    ((ActividadListaProductos)getActivity()).capturarParametros(Producto.getText().toString(), Precio.getText().toString());
                    listernerInsertar.iniciarListener_insertar();
                    dismiss();

            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        return builder.create();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            listernerInsertar = (OnInsertarResgistroListener) getActivity();
        }catch (ClassCastException ex){
            throw new ClassCastException(activity.toString()+"No se implemento la interface OnInsertarResgitroListener");
        }
    }
}
