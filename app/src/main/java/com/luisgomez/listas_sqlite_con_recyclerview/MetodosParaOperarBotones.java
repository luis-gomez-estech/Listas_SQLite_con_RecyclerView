package com.luisgomez.listas_sqlite_con_recyclerview;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;


public class MetodosParaOperarBotones {

    private UsuariosSQLIteHelper ayudanteBaseDeDatos;
    private String NOMBRE_TABLA = "usuarios";


    public MetodosParaOperarBotones(Context contexto) {
        ayudanteBaseDeDatos = new UsuariosSQLIteHelper(contexto);

    }


    public int eliminarUsuario(Usuario usuario) {

        SQLiteDatabase baseDeDatos = ayudanteBaseDeDatos.getWritableDatabase();
        String[] argumentos = {String.valueOf(usuario.getId())};
        return baseDeDatos.delete(NOMBRE_TABLA, "id = ?", argumentos);
    }

    public long nuevoUsuario(Usuario usuario) {
        // wInsertamos
        SQLiteDatabase baseDeDatos = ayudanteBaseDeDatos.getWritableDatabase();
        ContentValues valoresParaInsertar = new ContentValues();
        valoresParaInsertar.put("nombre", usuario.getNombreUsuario_C());
        valoresParaInsertar.put("edad", usuario.getEdadUsuario_C());
        return baseDeDatos.insert(NOMBRE_TABLA, null, valoresParaInsertar);
    }

    public int guardarCambios(Usuario usuarioEditado) {
        SQLiteDatabase baseDeDatos = ayudanteBaseDeDatos.getWritableDatabase();
        ContentValues valoresParaActualizar = new ContentValues();
        valoresParaActualizar.put("nombre", usuarioEditado.getNombreUsuario_C());
        valoresParaActualizar.put("edad", usuarioEditado.getEdadUsuario_C());

        String campoParaActualizar = "id = ?";

        String[] argumentosParaActualizar = {String.valueOf(usuarioEditado.getId())};
        return baseDeDatos.update(NOMBRE_TABLA, valoresParaActualizar, campoParaActualizar, argumentosParaActualizar);
    }

    public void borrarTodosDatos() {

        //Obtenemos la base de datos
        SQLiteDatabase baseDeDatos = ayudanteBaseDeDatos.getReadableDatabase();

        //y la borramos
        baseDeDatos.delete(NOMBRE_TABLA, null, null);

        //Adaptamos la lista



    }

    public ArrayList<Usuario> obtenerUsuarios() {
        ArrayList<Usuario> usuarios = new ArrayList<>();

        SQLiteDatabase baseDeDatos = ayudanteBaseDeDatos.getReadableDatabase();
        // SELECT nombre, edad, id
        String[] columnasAConsultar = {"nombre", "edad", "id"};
        Cursor cursor = baseDeDatos.query(
                NOMBRE_TABLA,//from usuarios
                columnasAConsultar,
                null,
                null,
                null,
                null,
                null
        );

        if (cursor == null) {

            return usuarios;

        }

        // Si no hay datos, igualmente regresamos la lista vacía
        if (!cursor.moveToFirst()) return usuarios;

        // En caso de que sí haya, iteramos y vamos agregando los
        // datos a la lista de usuarios
        do {
            // El 0 es el número de la columna, como seleccionamos
            // nombre, edad,id entonces el nombre es 0, edad 1 e id es 2
            String nombreObtenidoDeBD = cursor.getString(0);
            int edadObtenidaDeBD = cursor.getInt(1);
            long idUsuario = cursor.getLong(2);
            Usuario UsuarioObtenidoDeBD = new Usuario(nombreObtenidoDeBD, edadObtenidaDeBD, idUsuario);
            usuarios.add(UsuarioObtenidoDeBD);
        } while (cursor.moveToNext());

        // Fin del ciclo. Cerramos cursor y regresamos la lista de usuarios
        cursor.close();
        return usuarios;
    }





}