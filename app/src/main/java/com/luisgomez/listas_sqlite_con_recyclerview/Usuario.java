package com.luisgomez.listas_sqlite_con_recyclerview;

public class Usuario {

    private String rvNombreUsuario;
    private int rvEdadUsuario;

    private long id; // El ID de la BD

    public Usuario(String nombre, int edad) {
        this.rvNombreUsuario = nombre;
        this.rvEdadUsuario = edad;
    }

    // Constructor para cuando instanciamos desde la BD
    public Usuario(String nombre, int edad, long id) {
        this.rvNombreUsuario = nombre;
        this.rvEdadUsuario = edad;
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRvNombreUsuario() {
        return rvNombreUsuario;
    }

    public void setRvNombreUsuario(String rvNombreUsuario) {
        this.rvNombreUsuario = rvNombreUsuario;
    }

    public int getRvEdadUsuario() {
        return rvEdadUsuario;
    }

    public void setRvEdadUsuario(int rvEdadUsuario) {
        this.rvEdadUsuario = rvEdadUsuario;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "rvNombreUsuario='" + rvNombreUsuario + '\'' +
                ", edad=" + rvEdadUsuario +
                '}';
    }
}
