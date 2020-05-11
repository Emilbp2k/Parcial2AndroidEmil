package com.AppEmil.parcialemil;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.AppEmil.parcialemil.Base_de_Datos.DBControlador;
import com.AppEmil.parcialemil.Modelos.Persona;

public class BuscarActivity extends AppCompatActivity implements View.OnClickListener {

    DBControlador controlador;

    EditText edCedula;

    Button btBuscar, btRegresar;

    TextView verCedula, verNombre, verEstrato, VerSalario, verNivel;
    int cedula;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar);

        edCedula = findViewById(R.id.edCedula);
        btBuscar = findViewById(R.id.btBuscar);
        btRegresar = findViewById(R.id.btRegresar);
        verCedula = findViewById(R.id.tvCedula);
        verNombre = findViewById(R.id.tvNombre);
        verEstrato = findViewById(R.id.tvEstrato);
        VerSalario = findViewById(R.id.tvSalario);
        verNivel = findViewById(R.id.tvNivelEducativo);

        controlador = new DBControlador(getApplicationContext());


        btBuscar.setOnClickListener(this);
        btRegresar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btBuscar:
                try {
                    cedula = Integer.parseInt(edCedula.getText().toString());
                } catch (NumberFormatException numEx) {
                    Toast.makeText(getApplicationContext(), "Numero Demasiado Grande", Toast.LENGTH_SHORT).show();
                }
                Persona persona = controlador.buscarPersona(cedula);
                if (persona != null) {
                    verCedula.setText(String.valueOf(cedula));
                    verNombre.setText(persona.getNombre());
                    verEstrato.setText(String.valueOf(persona.getEstrato2()));
                    VerSalario.setText(String.valueOf(persona.getSalario()));
                    switch (persona.getNivel_educativo()) {
                        case 0:
                            verNivel.setText(getString(R.string.educativo_bachillerato));
                            break;
                        case 1:
                            verNivel.setText(getString(R.string.educativo_pregado));
                            break;
                        case 2:
                            verNivel.setText(getString(R.string.educativo_maestro));
                            break;
                        case 3:
                            verNivel.setText(getString(R.string.educativo_doctorado));
                            break;
                    }
                } else {
                    verCedula.setText(getString(R.string.invalido));
                    verNombre.setText(getString(R.string.invalido));
                    verEstrato.setText(getString(R.string.invalido));
                    VerSalario.setText(getString(R.string.invalido));
                    verNivel.setText(getString(R.string.invalido));
                    Toast.makeText(getApplicationContext(), "Cedula No Encontrada", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btRegresar:
                finish();
                break;
        }
    }
}
