package com.luisgomez.listas_sqlite_con_recyclerview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class AgregarUsuario extends AppCompatActivity {
    private Button btnAgregarUsuario, btnCancelarNuevoUsuario;
    private EditText etNombre, etEdad;
    private MetodosParaOperarBotones metodosParaOperarBotones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_usuario);


        etNombre = findViewById(R.id.nombreSitio_A);
        etEdad = findViewById(R.id.nombreCiudad_A);
        btnAgregarUsuario = findViewById(R.id.btnAgregarSitio);
        btnCancelarNuevoUsuario = findViewById(R.id.btnCancelarNuevoSitio);


        metodosParaOperarBotones = new MetodosParaOperarBotones(AgregarUsuario.this);

        // Boton agregar
        btnAgregarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Remover previos errores si existen

                etNombre.setError(null);
                etEdad.setError(null);

                //Validaciones àra determnar que tanto el nombre como la edad no estan vacios para dejarnos agregar Usuario

                String nombre = etNombre.getText().toString(),
                        edadComoCadena = etEdad.getText().toString();
                if ("".equals(nombre)) {
                    etNombre.setError("Escribe el nombre del usuario");
                    etNombre.requestFocus();
                    return;
                }
                if ("".equals(edadComoCadena)) {
                    etEdad.setError("Escribe la edad del usuario");
                    etEdad.requestFocus();
                    return;
                }

                // Ademas para la edad valid si es un numero o no
                int edad;
                try {
                    edad = Integer.parseInt(etEdad.getText().toString());
                } catch (NumberFormatException e) {
                    etEdad.setError("Escribe un número");
                    etEdad.requestFocus();
                    return;
                }

                // Una vez validamos, entonces nos vamos a la class que contiene los metodos y añadimos los datos a la base de datos y se actualiza la lista

                Usuario nuevoUsuario = new Usuario(nombre, edad);
                long id = metodosParaOperarBotones.nuevoUsuario(nuevoUsuario);
                if (id == -1) {

                    // Si se prodce error al guardar
                    Toast.makeText(AgregarUsuario.this, "Error al guardar. Intenta de nuevo", Toast.LENGTH_SHORT).show();
                } else {

                    // Sino cerramos la actividad
                    finish();
                }
            }
        });

        // Boton cancelar, cierra la actividad
        btnCancelarNuevoUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
