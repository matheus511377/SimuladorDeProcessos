package com.matheus.simuladordeprocessos.simuladordeprocessos.Classes;

/**
 * Created by Matheus on 13/09/2016.
 */import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.matheus.simuladordeprocessos.R;

import java.util.List;

public class AdapterProcesso extends BaseAdapter {
    private List<Processo> ListaProcessos;
    private List<Fila> filas;
    private LayoutInflater inflater;
    private String qualFila;

    private static class ViewHolder {
        public TextView txt3;
        public TextView txtPID;
        public TextView txtT;
        public TextView txtTE;

        private ViewHolder() {
        }
    }

    public AdapterProcesso(Context context, List<Fila> filas, List<Processo> processos, String qualFila) {
        this.filas = filas;
        this.ListaProcessos = processos;
        this.qualFila = qualFila;
        this.inflater = LayoutInflater.from(context);
    }

    public int getCount() {
        return this.filas.size();
    }

    public Object getItem(int position) {
        return this.filas.get(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.adapter_processos, parent, false);
            vh = new ViewHolder();
            vh.txtPID = (TextView) view.findViewById(R.id.txtID);
            vh.txtTE = (TextView) view.findViewById(R.id.txtTE);
            vh.txtT = (TextView) view.findViewById(R.id.txtT);
            vh.txt3 = (TextView) view.findViewById(R.id.txt3);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) view.getTag();
        }
        Fila fila = filas.get(position);
        vh.txtPID.setText(String.valueOf(fila.getId()));
        if (qualFila == "APTOS") {
            vh.txtTE.setText(String.valueOf((ListaProcessos.get(buscaPosicaoListaProcessos(fila.getId()))).getTempoExecutando()));
            vh.txtT.setText(String.valueOf((ListaProcessos.get(buscaPosicaoListaProcessos(fila.getId()))).getTempoTotal()));
        } else if (qualFila == "BLOQUEADOS") {
            vh.txtTE.setText(String.valueOf((ListaProcessos.get(buscaPosicaoListaProcessos(fila.getId()))).getTempoExecutandoTotalES()));
            vh.txtT.setText(String.valueOf((ListaProcessos.get(buscaPosicaoListaProcessos(fila.getId()))).getTempoTotalES()));
        } else if (qualFila == "DESTRUIDOS") {
            vh.txtTE.setText(String.valueOf((ListaProcessos.get(buscaPosicaoListaProcessos(fila.getId()))).getTempoTotal()));
            vh.txtT.setText(String.valueOf((ListaProcessos.get(buscaPosicaoListaProcessos(fila.getId()))).getTempoTotal()));
        }
        return view;
    }

    private int buscaPosicaoListaProcessos(int id) {
        for (int i = 0; i <= this.ListaProcessos.size(); i++) {
            if ((ListaProcessos.get(i)).getId() == id) {
                return i;
            }
        }
        return -1;
    }
}

