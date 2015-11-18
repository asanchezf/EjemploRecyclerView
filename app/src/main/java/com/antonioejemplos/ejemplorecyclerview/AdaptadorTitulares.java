package com.antonioejemplos.ejemplorecyclerview;

import android.support.v7.widget.RecyclerView;//Necesario añadir en el gradle: compile 'com.android.support:recyclerview-v7:+'
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/*
 Este adaptador deberá extender a la clase RecyclerView.Adapter, de la cual tendremos que sobrescribir principalmente tres métodos:
 -onCreateViewHolder(). Encargado de crear los nuevos objetos ViewHolder necesarios para los elementos de la colección.
 -onBindViewHolder(). Encargado de actualizar los datos dun ViewHolder ya existente.
 -onItemCount(). Indica el número de elementos de la colección de datos.
 */

public class AdaptadorTitulares extends RecyclerView.Adapter<AdaptadorTitulares.TitularesViewHolder>
        implements View.OnClickListener {

    private View.OnClickListener listener;
    private ArrayList<Titular> datos;


    @Override
    public TitularesViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.listitem_titular, viewGroup, false);

        itemView.setOnClickListener(this);
        //android:background="?android:attr/selectableItemBackground"

        TitularesViewHolder tvh = new TitularesViewHolder(itemView);

        return tvh;
    }

    @Override
    public void onBindViewHolder(TitularesViewHolder viewHolder, int pos) {
        Titular item = datos.get(pos);

        viewHolder.bindTitular(item);
    }

    @Override
    public int getItemCount() {
        return datos.size();
    }

    //Para implementar el patrón viewHolder...
    public static class TitularesViewHolder extends RecyclerView.ViewHolder {

        private TextView txtTitulo;
        private TextView txtSubtitulo;

        public TitularesViewHolder(View itemView) {
            super(itemView);

            txtTitulo = (TextView)itemView.findViewById(R.id.LblTitulo);
            txtSubtitulo = (TextView)itemView.findViewById(R.id.LblSubTitulo);
        }

        public void bindTitular(Titular t) {
            txtTitulo.setText(t.getTitulo());
            txtSubtitulo.setText(t.getSubtitulo());
        }
    }

    public AdaptadorTitulares(ArrayList<Titular> datos) {
        this.datos = datos;
    }



    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if(listener != null)
            listener.onClick(view);
    }
}