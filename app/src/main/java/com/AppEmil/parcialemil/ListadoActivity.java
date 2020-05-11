package com.AppEmil.parcialemil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleObserver;


import com.AppEmil.parcialemil.Base_de_Datos.DBControlador;
import com.AppEmil.parcialemil.Modelos.*;


import java.util.ArrayList;

public class ListadoActivity extends AppCompatActivity implements LifecycleObserver {

    ListView lista;

    ArrayList<Persona> listarPersona;

    DBControlador controlador;

    ListaAdapter adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado);

        lista = findViewById(R.id.listView);

        controlador = new DBControlador(getApplicationContext());

        listarPersona = controlador.obtenerRegistros();
        adaptador = new ListaAdapter(this, listarPersona);
        lista.setAdapter(adaptador);

        registerForContextMenu(lista);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2) {
            if (resultCode == Activity.RESULT_OK) {
                ArrayList<Persona> listaServicios = controlador.obtenerRegistros();
                ListaAdapter adap = new ListaAdapter(this, listaServicios);
                lista.setAdapter(adap);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(), "Cancelado", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.listado_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getItemId()) {
            case R.id.action_modificar_registro:
                modificarRegistro(menuInfo.position);
                return true;
            case R.id.action_borrar_registro:
                borrarRegistro(menuInfo.position);
                return true;
        }
        return super.onContextItemSelected(item);
    }


    private void modificarRegistro(int posicion) {
        Intent intent = new Intent(this, ModificarActivity.class);
        intent.putExtra("Indice", posicion);
        startActivityForResult(intent, 2);
    }

    private void borrarRegistro(int posicion) {
        int retorno = controlador.borrarRegistro(listarPersona.get(posicion));
        if (retorno == 1) {
            Toast.makeText(getApplicationContext(), "Dato eliminado", Toast.LENGTH_SHORT).show();
            listarPersona = controlador.obtenerRegistros();
            adaptador = new ListaAdapter(this, listarPersona);
            lista.setAdapter(adaptador);
        } else {
            Toast.makeText(getApplicationContext(), "Error al borrar", Toast.LENGTH_SHORT).show();
        }
    }
}
