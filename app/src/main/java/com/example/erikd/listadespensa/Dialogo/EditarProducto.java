package com.example.erikd.listadespensa.Dialogo;

import android.app.Activity;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.erikd.listadespensa.ActividadListaProductos;
import com.example.erikd.listadespensa.MainActivity;
import com.example.erikd.listadespensa.R;

/**
 * Created by erikd on 18/01/2017.
 */

public class EditarProducto extends DialogFragment {

    public interface OnActualizarResgistroListener{
        void iniciarListener_actualizar();
    }

    OnActualizarResgistroListener listenerActualizar;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogo = inflater.inflate(R.layout.dialogo_insertar, null);
        builder.setView(dialogo);

        builder.setTitle("Actualizar");

        ImageButton btnAceptar = (ImageButton)dialogo.findViewById(R.id.btnAceptar);
        ImageButton btnCancelar = (ImageButton)dialogo.findViewById(R.id.btnCancelar);
        final EditText Producto = (EditText)dialogo.findViewById(R.id.edt_producto);
        final EditText Precio = (EditText)dialogo.findViewById(R.id.edt_precio);

        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ActividadListaProductos)getActivity()).capturarParametros(Producto.getText().toString(), Precio.getText().toString());
                listenerActualizar.iniciarListener_actualizar();
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
            listenerActualizar = (OnActualizarResgistroListener) getActivity();
        }catch (ClassCastException ex){
            throw new ClassCastException(activity.toString()+"No se implemento la interface OnInsertarResgitroListener");
        }
    }
}
