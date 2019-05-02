package com.luisgomez.listas_sqlite_con_recyclerview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


public class UsuariosAdapter extends RecyclerView.Adapter<UsuariosAdapter.MyViewHolder> {

    private List<Usuario> listaDeUsuarios;

    public void setListaDeUsuarios(List<Usuario> listaDeUsuarios) {
        this.listaDeUsuarios = listaDeUsuarios;
    }

    public UsuariosAdapter(List<Usuario> usuarios) {
        this.listaDeUsuarios = usuarios;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View filaUsuario = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_usuarios, viewGroup, false);
        return new MyViewHolder(filaUsuario);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        // Obtener el usuario de nuestra lista con el índice i
        Usuario usuario = listaDeUsuarios.get(i);

        // Obtener los datos de la lista
        String nombreUsuario_B = usuario.getNombreUsuario_C();
        int edadUsuario_B = usuario.getEdadUsuario_C();
        // Y poner a los TextView los datos con setText
        myViewHolder.nombre.setText(nombreUsuario_B);
        myViewHolder.edad.setText(String.valueOf(edadUsuario_B));
    }

    @Override
    public int getItemCount() {
        return listaDeUsuarios.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nombre, edad;

        MyViewHolder(View itemView) {
            super(itemView);
            this.nombre = itemView.findViewById(R.id.tvNombreUsuario);
            this.edad = itemView.findViewById(R.id.tvedadUsuario);
        }
    }
}
