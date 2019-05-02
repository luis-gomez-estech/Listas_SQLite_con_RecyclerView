package com.luisgomez.listas_sqlite_con_recyclerview;

public class Usuario {

    private String nombreUsuario_C;
    private int edadUsuario_C;

    private long id; // El ID de la BD

    public Usuario(String nombre, int edad) {
        this.nombreUsuario_C = nombre;
        this.edadUsuario_C = edad;
    }

    // Constructor para cuando instanciamos desde la BD
    public Usuario(String nombre, int edad, long id) {
        this.nombreUsuario_C = nombre;
        this.edadUsuario_C = edad;
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombreUsuario_C() {
        return nombreUsuario_C;
    }

    public void setNombreUsuario_C(String nombreUsuario_C) {
        this.nombreUsuario_C = nombreUsuario_C;
    }

    public int getEdadUsuario_C() {
        return edadUsuario_C;
    }

    public void setEdadUsuario_C(int edadUsuario_C) {
        this.edadUsuario_C = edadUsuario_C;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "nombreUsuario_C='" + nombreUsuario_C + '\'' +
                ", edad=" + edadUsuario_C +
                '}';
    }
}
