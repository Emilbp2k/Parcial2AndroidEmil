package com.AppEmil.parcialemil;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.AppEmil.parcialemil.Base_de_Datos.DBControlador;
import com.AppEmil.parcialemil.Modelos.Persona;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

   DBControlador controlador;

    EditText editCedula, editNombre, editSalario;
    Spinner spinnerEstrato, spinnerNivel;

    Button btnGuardar, btnCancelar;

    int estrato, nivelEducativo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editCedula = findViewById(R.id.edCedula);
        editNombre = findViewById(R.id.edNombre);
        editSalario = findViewById(R.id.edSalario);
        spinnerEstrato = findViewById(R.id.spEstrato);
        spinnerNivel = findViewById(R.id.spNivelEducativo);
        btnGuardar = findViewById(R.id.btGuardar);
        btnCancelar = findViewById(R.id.btCancelar);

        controlador = new DBControlador(getApplicationContext());

        String estrato  [] = {"1","2","3","4","5","6"};
        ArrayAdapter<String> adap = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,estrato);
        spinnerEstrato.setAdapter(adap);

        spinnerEstrato.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                MainActivity.this.estrato = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        String nivelE  [] = { "Bachillerato","Pregrado","Maestria","Doctorado"};
        ArrayAdapter<String> Nivelest = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,nivelE);
        spinnerNivel.setAdapter(Nivelest);

        spinnerNivel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                nivelEducativo = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnGuardar.setOnClickListener(this);
        btnCancelar.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btGuardar:
                try {
                    int cedula = editCedula.getText().toString().isEmpty() ? 0 : Integer.parseInt(editCedula.getText().toString());
                    int salario = editSalario.getText().toString().isEmpty() ? 0 : Integer.parseInt(editSalario.getText().toString());
                    Persona persona = new Persona(cedula, editNombre.getText().toString(), estrato, salario, nivelEducativo);
                    long retorno = controlador.agregarRegistro(persona);
                    if (retorno != -1) {
                        Toast.makeText(v.getContext(), "Guardado Exitoso", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(v.getContext(), "Guardado Fallido", Toast.LENGTH_SHORT).show();
                    }
                    limpiarCampo();
                } catch (NumberFormatException numEx) {
                    Toast.makeText(getApplicationContext(), "Numero demasiado grande", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btCancelar:
                limpiarCampo();
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_buscar_registro:
                Intent intent1 = new Intent(this, BuscarActivity.class);
                startActivity(intent1);
                return true;
            case R.id.action_listado:
                Intent intent = new Intent(this, ListadoActivity.class);
                startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void limpiarCampo() {
        editCedula.setText("");
        editNombre.setText("");
        editSalario.setText("");
    }


}
