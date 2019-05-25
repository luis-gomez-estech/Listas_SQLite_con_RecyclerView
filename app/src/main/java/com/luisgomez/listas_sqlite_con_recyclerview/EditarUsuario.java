package com.luisgomez.listas_sqlite_con_recyclerview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



public class EditarUsuario extends AppCompatActivity {

    private EditText etEditarNombre, etEditarEdad;
    private Button btnGuardarCambios, btnCancelarEdicion;
    private Usuario usuario;// El usuario que vamos a editar
    private MetodosParaOperarBotones metodosParaOperarBotones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_usuario);

        // Recuperar datos que se enviaron a la base de datos
        Bundle extras = getIntent().getExtras();
        // Si no hay datos salimos
        if (extras == null) {
            finish();
            return;
        }
        // Instanciamos la class que contiene los metodos
        metodosParaOperarBotones = new MetodosParaOperarBotones(EditarUsuario.this);

        // obtenemos datos del Usuario
        long idUsuario = extras.getLong("idUsuario");
        String nombreUsuario = extras.getString("nombreUsuario");
        int edadUsuario = extras.getInt("edadUsuario");
        usuario = new Usuario(nombreUsuario, edadUsuario, idUsuario);


        // Botones

        etEditarNombre = findViewById(R.id.etEditarNombreUsuario);
        etEditarEdad = findViewById(R.id.etEditarEdadUsuario);

        btnCancelarEdicion = findViewById(R.id.btnCancelarEdicionUsuario);
        btnGuardarCambios = findViewById(R.id.btnGuardarCambiosUsuario);


        // Aqui se Rellenan los EditText con los datos de la usuario

        etEditarEdad.setText(String.valueOf(usuario.getRvEdadUsuario()));
        etEditarNombre.setText(usuario.getRvNombreUsuario());

        // Boton Cancelar, que cierra la actividad
        btnCancelarEdicion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Boton guarda cambios

        btnGuardarCambios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Remover previos errores si existen

                etEditarNombre.setError(null);
                etEditarEdad.setError(null);

                // Crear la usuario con los nuevos cambios pero ponerle
                // el id de la anterior

                String nuevoNombre = etEditarNombre.getText().toString();
                String posibleNuevaEdad = etEditarEdad.getText().toString();

                //Validaciones àra determnar que tanto el nombre como la edad no estan vacios para dejarnos agregar Usuario

                if (nuevoNombre.isEmpty()) {
                    etEditarNombre.setError("Escribe el nombre");
                    etEditarNombre.requestFocus();
                    return;
                }
                if (posibleNuevaEdad.isEmpty()) {
                    etEditarEdad.setError("Escribe la edad");
                    etEditarEdad.requestFocus();
                    return;
                }

                // Ademas para la edad valid si es un numero o no
                int nuevaEdad;

                try {
                    nuevaEdad = Integer.parseInt(posibleNuevaEdad);
                } catch (NumberFormatException e) {
                    etEditarEdad.setError("Escribe un número");
                    etEditarEdad.requestFocus();
                    return;
                }

                // Una vez validamos, entonces nos vamos a la class que contiene los metodos y añadimos los datos a la base de datos y se actualiza la lista

                Usuario usuarioConNuevosCambios = new Usuario(nuevoNombre, nuevaEdad, usuario.getId());
                int filasModificadas = metodosParaOperarBotones.guardarCambios(usuarioConNuevosCambios);

                //Si se modifica mas de una fila, nos marcara el error
                if (filasModificadas != 1) {

                    Toast.makeText(EditarUsuario.this, "Error guardando cambios. Intente de nuevo.", Toast.LENGTH_SHORT).show();
                } else {

                    // Sino, cerramos la actividad con finish

                    finish();
                }
            }
        });
    }
}
