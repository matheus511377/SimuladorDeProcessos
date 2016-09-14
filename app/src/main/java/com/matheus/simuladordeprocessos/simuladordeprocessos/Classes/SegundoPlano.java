package com.matheus.simuladordeprocessos.simuladordeprocessos.Classes;

/**
 * Created by Matheus on 13/09/2016.
 */import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.helper.ItemTouchHelper.Callback;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;
import com.matheus.simuladordeprocessos.BuildConfig;
import com.matheus.simuladordeprocessos.R;
import com.matheus.simuladordeprocessos.simuladordeprocessos.Classes.Fila;
import com.matheus.simuladordeprocessos.simuladordeprocessos.Classes.Processo;
import com.matheus.simuladordeprocessos.simuladordeprocessos.Classes.adapterListViewEspancivel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class SegundoPlano extends AsyncTask<Integer, Integer, Void> {
    private int NumeroDeProcessos;
    private int NumeroDeProcessosCriados;
    private long Tempo;
    private adapterListViewEspancivel adaptador;
    private Button btnOK;
    private int ciclos;
    private Context context;
    public List<Fila> filaAptos;
    public List<Fila> filaBloqueados;
    public List<Fila> filaDestruido;
    public List<Fila> filaHD;
    public List<Fila> filaImpressora;
    public List<Fila> filaVideo;
    private int f3i;
    private int id;
    private ExpandableListView listViewEspancivel;
    public List<Processo> listaProcesso;
    public List<String> lstGrupo;
    public int numeroFilaAptos;
    public int numeroFilaBloqueados;
    public int numeroFilaDestruido;
    public int numeroFilaHD;
    public int numeroFilaImpressora;
    public int numeroFilaVideo;
    private int posicaoProcesso;
    private int posicaoProcessoHD;
    private int posicaoProcessoImpressora;
    private int posicaoProcessoVideo;
    private boolean processadorExecutando;
    private boolean processadorExecutandoHD;
    private boolean processadorExecutandoImpressora;
    private boolean processadorExecutandoVideo;
    private int qtdHD;
    private int qtdImpressora;
    private int qtdVideo;
    public Random random;
    private int randomTemp;
    private String status;
    private long tempoTotalMedioCiclos;
    private TextView texto;
    private TextView txtRelatorio;
    private int ultmPID;

    public SegundoPlano(Context context, TextView txtExecutando, ExpandableListView lstView, int NumeroDeProcessos, long tempo, Button btnOk, TextView txtRelatorio) {
        this.numeroFilaAptos = 0;
        this.numeroFilaBloqueados = 0;
        this.numeroFilaHD = 0;
        this.numeroFilaVideo = 0;
        this.numeroFilaImpressora = 0;
        this.numeroFilaDestruido = 0;
        this.lstGrupo = new ArrayList();
        this.filaAptos = new ArrayList();
        this.listaProcesso = new ArrayList();
        this.filaHD = new ArrayList();
        this.filaVideo = new ArrayList();
        this.filaImpressora = new ArrayList();
        this.filaBloqueados = new ArrayList();
        this.filaDestruido = new ArrayList();
        this.posicaoProcesso = 0;
        this.posicaoProcessoHD = 0;
        this.posicaoProcessoVideo = 0;
        this.posicaoProcessoImpressora = 0;
        this.ciclos = 0;
        this.id = 0;
        this.random = new Random();
        this.processadorExecutandoHD = false;
        this.processadorExecutandoVideo = false;
        this.processadorExecutandoImpressora = false;
        this.processadorExecutando = false;
        this.randomTemp = 0;
        this.f3i = 0;
        this.NumeroDeProcessos = 0;
        this.NumeroDeProcessosCriados = 0;
        this.Tempo = 0;
        this.tempoTotalMedioCiclos = 0;
        this.qtdHD = 0;
        this.qtdImpressora = 0;
        this.qtdVideo = 0;
        this.context = context;
        this.texto = txtExecutando;
        this.listViewEspancivel = lstView;
        this.NumeroDeProcessos = NumeroDeProcessos;
        this.Tempo = tempo;
        this.btnOK = btnOk;
        this.txtRelatorio = txtRelatorio;
        this.lstGrupo.add("APTOS");
        this.lstGrupo.add("DESTRUIDOS");
        this.lstGrupo.add("HD");
        this.lstGrupo.add("VIDEO");
        this.lstGrupo.add("IMPRESSORA");
        HashMap<String, List<Fila>> lstItensGrupo = new HashMap();
        lstItensGrupo.put(this.lstGrupo.get(0), this.filaAptos);
        lstItensGrupo.put(this.lstGrupo.get(1), this.filaDestruido);
        lstItensGrupo.put(this.lstGrupo.get(2), this.filaHD);
        lstItensGrupo.put(this.lstGrupo.get(3), this.filaVideo);
        lstItensGrupo.put(this.lstGrupo.get(4), this.filaImpressora);
        this.adaptador = new adapterListViewEspancivel(context, this.lstGrupo, lstItensGrupo, this.listaProcesso);
        this.listViewEspancivel.setAdapter(this.adaptador);
    }

    protected void onPreExecute() {
        super.onPreExecute();
        this.btnOK.setEnabled(false);
        this.listViewEspancivel.setVisibility(View.VISIBLE);
        this.txtRelatorio.setText(BuildConfig.FLAVOR);
    }

    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        this.btnOK.setEnabled(true);
        this.listViewEspancivel.setVisibility(View.INVISIBLE);
        this.txtRelatorio.setText(Html.fromHtml("N\u00ba de processos criados: " + this.NumeroDeProcessos + "<br>" + "Tempo total em ciclos: " + this.ciclos + "<br>" + "Tempo Total m\u00e9dio por processo: " + tempoTotalMedioCiclos() + "<br>" + "Qtd. de processos em cada estado: <br> APTO:" + this.NumeroDeProcessos + "<br>EXECU\u00c7\u00c3O: " + this.NumeroDeProcessos + "<br>HD: " + this.qtdHD + "<br>IMPRESSORA: " + BuildConfig.FLAVOR + this.qtdImpressora + "<br>VIDEO: " + this.qtdVideo));
    }

    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        this.adaptador.notifyDataSetChanged();
        this.texto.setText(this.status);
    }

    protected Void doInBackground(Integer... params) {
        while (this.filaDestruido.size() < this.NumeroDeProcessos) {
            this.ciclos++;
            try {
                Thread.sleep(this.Tempo * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if ((this.random.nextInt(100) <= 10 || this.ciclos == 1 || (this.filaAptos.size() <= 0 && !this.processadorExecutando)) && this.NumeroDeProcessosCriados < this.NumeroDeProcessos) {
                this.id++;
                this.NumeroDeProcessosCriados++;
                Processo processo = new Processo();
                processo.setId(this.id);
                processo.setEstado("APTO");
                processo.setTempoTotal(this.random.nextInt(200) + 100);
                processo.setTempoExecutando(0);
                processo.setNumeroCiclosExecutando(0);
                this.listaProcesso.add(processo);
                this.numeroFilaAptos++;
                this.filaAptos.add(new Fila(this.id, this.numeroFilaAptos));
            }
            if (this.filaAptos.size() > 0 || this.processadorExecutando) {
                if (!this.processadorExecutando) {
                    this.posicaoProcesso = buscaPosicaoListaProcessos(((Fila) this.filaAptos.get(0)).getId());
                    this.status = Integer.toString(((Fila) this.filaAptos.get(0)).getId());
                    this.ultmPID = ((Fila) this.filaAptos.get(0)).getId();
                    this.filaAptos.remove(0);
                    this.processadorExecutando = true;
                }
                if (this.posicaoProcesso != -1) {
                    ((Processo) this.listaProcesso.get(this.posicaoProcesso)).setEstado("EM EXECU\u00c7\u00c3O");
                    ((Processo) this.listaProcesso.get(this.posicaoProcesso)).setTempoExecutando(((Processo) this.listaProcesso.get(this.posicaoProcesso)).getTempoExecutando() + 1);
                    ((Processo) this.listaProcesso.get(this.posicaoProcesso)).setNumeroCiclosExecutando(((Processo) this.listaProcesso.get(this.posicaoProcesso)).getNumeroCiclosExecutando() + 1);
                    if (this.random.nextInt(100) <= 1) {
                        this.randomTemp = this.random.nextInt(3);
                        if (this.randomTemp == 1) {
                            if (((Processo) this.listaProcesso.get(this.posicaoProcesso)).isSolicitouHD()) {
                                ((Processo) this.listaProcesso.get(this.posicaoProcesso)).setSolicitouHD(true);
                            } else {
                                this.qtdHD++;
                            }
                            ((Processo) this.listaProcesso.get(this.posicaoProcesso)).setRecursoSolicitado("HD");
                            ((Processo) this.listaProcesso.get(this.posicaoProcesso)).setTempoTotalES(this.random.nextInt(200) + 100);
                            this.numeroFilaHD++;
                            this.filaHD.add(new Fila(((Processo) this.listaProcesso.get(this.posicaoProcesso)).getId(), this.numeroFilaHD));
                        } else if (this.randomTemp == 2) {
                            if (((Processo) this.listaProcesso.get(this.posicaoProcesso)).isSolicitouVideo()) {
                                ((Processo) this.listaProcesso.get(this.posicaoProcesso)).setSolicitouVideo(true);
                            } else {
                                this.qtdVideo++;
                            }
                            ((Processo) this.listaProcesso.get(this.posicaoProcesso)).setRecursoSolicitado("VID");
                            ((Processo) this.listaProcesso.get(this.posicaoProcesso)).setTempoTotalES(this.random.nextInt(100) + 100);
                            this.numeroFilaVideo++;
                            this.filaVideo.add(new Fila(((Processo) this.listaProcesso.get(this.posicaoProcesso)).getId(), this.numeroFilaVideo));
                        } else {
                            if (((Processo) this.listaProcesso.get(this.posicaoProcesso)).isSolicitouImpressora()) {
                                ((Processo) this.listaProcesso.get(this.posicaoProcesso)).setSolicitouImpressora(true);
                            } else {
                                this.qtdImpressora++;
                            }
                            ((Processo) this.listaProcesso.get(this.posicaoProcesso)).setRecursoSolicitado("IMP");
                            ((Processo) this.listaProcesso.get(this.posicaoProcesso)).setTempoTotalES(this.random.nextInt(100) + 500);
                            this.numeroFilaImpressora++;
                            this.filaImpressora.add(new Fila(((Processo) this.listaProcesso.get(this.posicaoProcesso)).getId(), this.numeroFilaImpressora));
                        }
                        ((Processo) this.listaProcesso.get(this.posicaoProcesso)).setEstado("BLOQUEADO");
                        ((Processo) this.listaProcesso.get(this.posicaoProcesso)).setNumeroCiclosExecutando(0);
                        ((Processo) this.listaProcesso.get(this.posicaoProcesso)).setTempoExecutandoTotalES(0);
                        this.numeroFilaBloqueados++;
                        this.filaBloqueados.add(new Fila(((Processo) this.listaProcesso.get(this.posicaoProcesso)).getId(), this.numeroFilaBloqueados));
                        this.processadorExecutando = false;
                        this.status = "RECURSO DE ENTRADA E SAIDA SOLICITADO";
                    }
                    if (((Processo) this.listaProcesso.get(this.posicaoProcesso)).getTempoExecutando() < ((Processo) this.listaProcesso.get(this.posicaoProcesso)).getTempoTotal()) {
                        this.status = "PID: " + this.ultmPID + "  TE: " + ((Processo) this.listaProcesso.get(this.posicaoProcesso)).getTempoExecutando() + " T: " + ((Processo) this.listaProcesso.get(this.posicaoProcesso)).getTempoTotal();
                        if (((Processo) this.listaProcesso.get(this.posicaoProcesso)).getNumeroCiclosExecutando() == 50) {
                            ((Processo) this.listaProcesso.get(this.posicaoProcesso)).setEstado("APTO");
                            ((Processo) this.listaProcesso.get(this.posicaoProcesso)).setNumeroCiclosExecutando(0);
                            this.numeroFilaAptos++;
                            this.filaAptos.add(new Fila(((Processo) this.listaProcesso.get(this.posicaoProcesso)).getId(), this.numeroFilaAptos));
                            this.processadorExecutando = false;
                        }
                    } else {
                        ((Processo) this.listaProcesso.get(this.posicaoProcesso)).setEstado("DESTRUIDO");
                        this.numeroFilaDestruido++;
                        this.filaDestruido.add(new Fila(((Processo) this.listaProcesso.get(this.posicaoProcesso)).getId(), this.numeroFilaDestruido));
                        this.processadorExecutando = false;
                    }
                }
            } else {
                this.status = "OCIOSO";
            }
            if (this.processadorExecutandoHD || this.filaHD.size() > 0) {
                if (!this.processadorExecutandoHD) {
                    this.posicaoProcessoHD = buscaPosicaoListaProcessos(((Fila) this.filaHD.get(0)).getId());
                    this.processadorExecutandoHD = true;
                }
                if (this.posicaoProcessoHD != -1) {
                    ((Processo) this.listaProcesso.get(this.posicaoProcessoHD)).setTempoExecutandoTotalES(((Processo) this.listaProcesso.get(this.posicaoProcessoHD)).getTempoExecutandoTotalES() + 1);
                    if (((Processo) this.listaProcesso.get(this.posicaoProcessoHD)).getTempoTotalES() <= ((Processo) this.listaProcesso.get(this.posicaoProcessoHD)).getTempoExecutandoTotalES()) {
                        this.numeroFilaAptos++;
                        ((Processo) this.listaProcesso.get(this.posicaoProcessoHD)).setEstado("APTO");
                        ((Processo) this.listaProcesso.get(this.posicaoProcessoHD)).setTempoExecutandoTotalES(0);
                        this.filaAptos.add(new Fila(((Processo) this.listaProcesso.get(this.posicaoProcessoHD)).getId(), this.numeroFilaAptos));
                        this.filaBloqueados.remove(buscaPosicaoFilaBloqueados(((Fila) this.filaHD.get(0)).getId()));
                        this.filaHD.remove(0);
                        this.processadorExecutandoHD = false;
                    }
                }
            }
            if (this.processadorExecutandoVideo || this.filaVideo.size() > 0) {
                if (!this.processadorExecutandoVideo) {
                    this.posicaoProcessoVideo = buscaPosicaoListaProcessos(((Fila) this.filaVideo.get(0)).getId());
                    this.processadorExecutandoVideo = true;
                }
                if (this.posicaoProcessoVideo != -1) {
                    ((Processo) this.listaProcesso.get(this.posicaoProcessoVideo)).setTempoExecutandoTotalES(((Processo) this.listaProcesso.get(this.posicaoProcessoVideo)).getTempoExecutandoTotalES() + 1);
                    if (((Processo) this.listaProcesso.get(this.posicaoProcessoVideo)).getTempoTotalES() <= ((Processo) this.listaProcesso.get(this.posicaoProcessoVideo)).getTempoExecutandoTotalES()) {
                        this.numeroFilaAptos++;
                        ((Processo) this.listaProcesso.get(this.posicaoProcessoVideo)).setEstado("APTO");
                        ((Processo) this.listaProcesso.get(this.posicaoProcessoVideo)).setTempoExecutandoTotalES(0);
                        this.filaAptos.add(new Fila(((Processo) this.listaProcesso.get(this.posicaoProcessoVideo)).getId(), this.numeroFilaAptos));
                        this.filaBloqueados.remove(buscaPosicaoFilaBloqueados(((Fila) this.filaVideo.get(0)).getId()));
                        this.filaVideo.remove(0);
                        this.processadorExecutandoVideo = false;
                    }
                }
            }
            if (this.processadorExecutandoImpressora || this.filaImpressora.size() > 0) {
                if (!this.processadorExecutandoImpressora) {
                    this.posicaoProcessoImpressora = buscaPosicaoListaProcessos(((Fila) this.filaImpressora.get(0)).getId());
                    this.processadorExecutandoImpressora = true;
                }
                if (this.posicaoProcessoImpressora != -1) {
                    ((Processo) this.listaProcesso.get(this.posicaoProcessoImpressora)).setTempoExecutandoTotalES(((Processo) this.listaProcesso.get(this.posicaoProcessoImpressora)).getTempoExecutandoTotalES() + 1);
                    if (((Processo) this.listaProcesso.get(this.posicaoProcessoImpressora)).getTempoTotalES() <= ((Processo) this.listaProcesso.get(this.posicaoProcessoImpressora)).getTempoExecutandoTotalES()) {
                        this.numeroFilaAptos++;
                        ((Processo) this.listaProcesso.get(this.posicaoProcessoImpressora)).setEstado("APTO");
                        ((Processo) this.listaProcesso.get(this.posicaoProcessoImpressora)).setTempoExecutandoTotalES(0);
                        this.filaAptos.add(new Fila(((Processo) this.listaProcesso.get(this.posicaoProcessoImpressora)).getId(), this.numeroFilaAptos));
                        this.filaBloqueados.remove(buscaPosicaoFilaBloqueados(((Fila) this.filaImpressora.get(0)).getId()));
                        this.filaImpressora.remove(0);
                        this.processadorExecutandoImpressora = false;
                    }
                }
            }
            publishProgress(new Integer[]{Integer.valueOf(1)});
        }
        this.status = "RELAT\u00d3RIO";
        publishProgress(new Integer[]{Integer.valueOf(1)});
        return null;
    }

    private int buscaPosicaoListaProcessos(int id) {
        this.f3i = 0;
        while (this.f3i < this.listaProcesso.size()) {
            if (((Processo) this.listaProcesso.get(this.f3i)).getId() == id) {
                return this.f3i;
            }
            this.f3i++;
        }
        return -1;
    }

    private int buscaPosicaoFilaBloqueados(int id) {
        this.f3i = 0;
        while (this.f3i < this.filaBloqueados.size()) {
            if (((Fila) this.filaBloqueados.get(this.f3i)).getId() == id) {
                return this.f3i;
            }
            this.f3i++;
        }
        return -1;
    }

    private long tempoTotalMedioCiclos() {
        this.f3i = 0;
        while (this.f3i < this.listaProcesso.size()) {
            this.tempoTotalMedioCiclos = ((long) ((Processo) this.listaProcesso.get(this.f3i)).getTempoTotal()) + this.tempoTotalMedioCiclos;
            this.f3i++;
        }
        return this.tempoTotalMedioCiclos / ((long) this.NumeroDeProcessos);
    }


}

