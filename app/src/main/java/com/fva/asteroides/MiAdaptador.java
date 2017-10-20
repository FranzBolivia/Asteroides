package com.fva.asteroides;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Vector;

/**
 * Created by DTIC-Dir on 19/10/2017.
 */

public class MiAdaptador extends RecyclerView.Adapter<MiAdaptador.ViewHolder> {
    private LayoutInflater inflador;
    private Vector<String> lista;

    public MiAdaptador(Context context, Vector<String> lista) {
        this.lista = lista;
        inflador = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflador.inflate(R.layout.elemento_lista, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int i) {
        holder.titulo.setText(lista.get(i));
        switch (Math.round((float) Math.random() * 3)) {
            case 0:
                holder.icon.setImageResource(R.drawable.asteroid);
                break;
            case 1:
                holder.icon.setImageResource(R.drawable.asteroid);
                break;
            default:
                holder.icon.setImageResource(R.drawable.asteroid);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView titulo, subtitutlo;
        public ImageView icon;

        ViewHolder(View itemView) {
            super(itemView);
            titulo = (TextView) itemView.findViewById(R.id.titulo);
            subtitutlo = (TextView) itemView.findViewById(R.id.subtitulo);
            icon = (ImageView) itemView.findViewById(R.id.icono);
        }
    }
}
