package com.luisgomez.listas_sqlite_con_recyclerview;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity  {

    public List<Usuario> listaDeUsuarios;
    private UsuariosAdapter usuariosAdapter;
    private MetodosParaOperarBotones metodosParaOperarBotones; // Para acceder a la class que contiene todos los metodos


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Definimos la class metodosParaOperarBotones para acceder a ella la cual va a contener todos los metodos
        metodosParaOperarBotones = new MetodosParaOperarBotones(MainActivity.this);

        RecyclerView recyclerView = findViewById(R.id.recyclerViewUsuarios);

        //Boton flotante AgregarUsuario

        FloatingActionButton btnFlotanteAgregarUsuario = findViewById(R.id.btnFlotanteAgregarUsuario);

        //Boton flotante AgregarUsuario

        Button btnAgregarUsuario = findViewById(R.id.btnAgregarUsuario);

        //Boton eliminar todoo

        Button btnEliminarTodo = findViewById(R.id.btnEliminarTodo);

        listaDeUsuarios = new ArrayList<>();
        usuariosAdapter = new UsuariosAdapter(listaDeUsuarios);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(usuariosAdapter);

        // Ahora actualizamos la lista del RecyclerView y le ponemos los datos de la BD

        refrescarListaDeUsuarios();

        // Caudno hacemos click encima de un item

        recyclerView.addOnItemTouchListener(new RVUsuarios(getApplicationContext(), recyclerView, new RVUsuarios.ClickListener() {

            // Si es una pulsacion corta, se va a ir al layout editar_usuario para poder modificar los datos

            @Override
            public void onClick(View view, int position) {
                // Pasar a la actividad EditarUsuario.java
                Usuario usuarioSeleccionado = listaDeUsuarios.get(position);
                Intent intent = new Intent(MainActivity.this, EditarUsuario.class);
                intent.putExtra("idUsuario", usuarioSeleccionado.getId());
                intent.putExtra("nombreUsuario", usuarioSeleccionado.getRvNombreUsuario());
                intent.putExtra("edadUsuario", usuarioSeleccionado.getRvEdadUsuario());
                startActivity(intent);
            }

            // Si es una pulsacion mas larga, nos va a preguntar si estamos seguros de eliminar

                @Override
            public void onLongClick(View view, int position) {
                final Usuario usuarioParaEliminar = listaDeUsuarios.get(position);
                AlertDialog dialog = new AlertDialog
                        .Builder(MainActivity.this)
                        .setPositiveButton("Sí, eliminar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                metodosParaOperarBotones.eliminarUsuario(usuarioParaEliminar);
                                refrescarListaDeUsuarios();
                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setTitle("Confirmar")
                        .setMessage("¿Eliminar el usuario " + usuarioParaEliminar.getRvNombreUsuario() + "?")
                        .create();
                dialog.show();

            }
        }));


        // Boton flotante que aparece en la parte inferior derecha, para añadir un nuevo Usuario

        btnFlotanteAgregarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Simplemente cambiamos de actividad
                Intent intent = new Intent(MainActivity.this, AgregarUsuario.class);
                startActivity(intent);
            }
        });

        //Si se presiona un tiempo mayor, aparece un Alert con un texto, muy util para aportar informacion a ciertos elementos en otras apps

        btnFlotanteAgregarUsuario.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Informacion")
                        .setMessage("Realizado por Luis Gomez")
                        .setNegativeButton("Cerrar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogo, int which) {
                                dialogo.dismiss();
                            }
                        })

                        //Icono-boton para ir a mi perfil

                        .setPositiveButton("Mi perfil", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent abrirNavegador = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.luisgomezlinares.es/"));
                                startActivity(abrirNavegador);
                            }
                        })
                        .create()
                        .show();
                return false;
            }
        });

        // Boton Agregar usuario

        btnAgregarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Para irnos al activity AgregarUsuario
                Intent intent = new Intent(MainActivity.this, AgregarUsuario.class);
                startActivity(intent);
            }
        });

        // Boton para eliminar todos los usuarios, lo que hacems es ir al metodo borrarTodosDatos() de la class  metodosParaOperarBotones

        btnEliminarTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Nos vamos al metodo para borrarla
                metodosParaOperarBotones.borrarTodosDatos();

                // Y actualizamos la lista del recyclerView
                refrescarListaDeUsuarios();
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        refrescarListaDeUsuarios();
    }

    public void refrescarListaDeUsuarios() {

        // Aqui se obtiene la lista o array de la BD y se la pasamos al RecyclerView

        if (usuariosAdapter == null) return;
        listaDeUsuarios = metodosParaOperarBotones.obtenerUsuarios();
        usuariosAdapter.setListaDeUsuarios(listaDeUsuarios);
        usuariosAdapter.notifyDataSetChanged();
    }


}
