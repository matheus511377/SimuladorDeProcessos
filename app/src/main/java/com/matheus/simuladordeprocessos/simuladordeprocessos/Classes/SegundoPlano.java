package com.matheus.simuladordeprocessos.simuladordeprocessos.Classes;

/**
 * Created by Matheus on 13/09/2016.
 */
import android.content.Context;
import android.os.AsyncTask;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;
import com.matheus.simuladordeprocessos.BuildConfig;

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
    private int teste;
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
    private long executandoParaApto = 0;
    private long tempoEspera = 0 ;

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
        this.txtRelatorio.setText(Html.fromHtml("" +
                "N\u00ba de processos criados: "+ this.NumeroDeProcessos + "<br>" +
                "Tempo total em ciclos: " + this.ciclos + "<br>" +
                "Tempo Total m\u00e9dio por processo: " + tempoTotalMedioCiclos() + "<br>" +
                "Qtd. de processos em cada estado: <br>" +
                "APTO:" + this.NumeroDeProcessos +
                "<br>EXECU\u00c7\u00c3O: " + this.NumeroDeProcessos + "<br>" +
                "HD: " + this.qtdHD + "<br>" +
                "IMPRESSORA: " + BuildConfig.FLAVOR + this.qtdImpressora + "<br>" +
                "VIDEO: " + this.qtdVideo + "<br>" +
                "Tempo espera medio APTOS: " + tempoEspera + "<br>" +
                "Tempo Exedido: " + executandoParaApto));

    }

    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        this.adaptador.notifyDataSetChanged();
        this.texto.setText(this.status);
    }

    protected Void doInBackground(Integer... params) {
        while (this.filaDestruido.size() < this.NumeroDeProcessos) {
            this.ciclos++;
            tempoEspera ++;
            try {
                Thread.sleep(this.Tempo * 200);
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
                processo.setTempoEspera(tempoEspera);
                this.listaProcesso.add(processo);
                this.numeroFilaAptos++;
                this.filaAptos.add(new Fila(this.id, this.numeroFilaAptos));
            }
            if (filaAptos.size() > 0 || this.processadorExecutando) {
                if (!processadorExecutando) {
                    posicaoProcesso = buscaPosicaoListaProcessos((this.filaAptos.get(0)).getId());
                    status = Integer.toString((filaAptos.get(0)).getId());
                    ultmPID = (filaAptos.get(0)).getId();
                    filaAptos.remove(0);
                    processadorExecutando = true;
                    listaProcesso.get(posicaoProcesso).setTempoEspera(tempoEspera - listaProcesso.get(posicaoProcesso).getTempoEspera());
                }
                if (this.posicaoProcesso != -1) {
                    ( this.listaProcesso.get(this.posicaoProcesso)).setEstado("EM EXECU\u00c7\u00c3O");
                    ( this.listaProcesso.get(this.posicaoProcesso)).setTempoExecutando(( this.listaProcesso.get(this.posicaoProcesso)).getTempoExecutando() + 1);
                    ( this.listaProcesso.get(this.posicaoProcesso)).setNumeroCiclosExecutando(( this.listaProcesso.get(this.posicaoProcesso)).getNumeroCiclosExecutando() + 1);
                    if (this.random.nextInt(100) <= 1) {
                        this.randomTemp = this.random.nextInt(3);
                        if (this.randomTemp == 1) {
                            if (!(this.listaProcesso.get(this.posicaoProcesso)).isSolicitouHD()) {
                               listaProcesso.get(this.posicaoProcesso).setSolicitouHD(true);
                               qtdHD++;
                            }
                            listaProcesso.get(this.posicaoProcesso).setRecursoSolicitado("HD");
                            (this.listaProcesso.get(this.posicaoProcesso)).setTempoTotalES(this.random.nextInt(200) + 100);
                            this.numeroFilaHD++;
                            this.filaHD.add(new Fila((listaProcesso.get(this.posicaoProcesso)).getId(), this.numeroFilaHD));

                        } else if (this.randomTemp == 2) {
                            if (!listaProcesso.get(this.posicaoProcesso).isSolicitouVideo()) {
                                listaProcesso.get(posicaoProcesso).setSolicitouVideo(true);
                                this.qtdVideo++;
                            }
                            listaProcesso.get(this.posicaoProcesso).setRecursoSolicitado("VID");
                            listaProcesso.get(this.posicaoProcesso).setTempoTotalES(this.random.nextInt(100) + 100);
                            this.numeroFilaVideo++;
                            this.filaVideo.add(new Fila(( listaProcesso.get(this.posicaoProcesso)).getId(), this.numeroFilaVideo));

                        } else {
                            if (!(listaProcesso.get(this.posicaoProcesso)).isSolicitouImpressora()) {
                                listaProcesso.get(this.posicaoProcesso).setSolicitouImpressora(true);
                                this.qtdImpressora++;
                            }
                            (this.listaProcesso.get(this.posicaoProcesso)).setRecursoSolicitado("IMP");
                            (this.listaProcesso.get(this.posicaoProcesso)).setTempoTotalES(this.random.nextInt(100) + 500);
                            this.numeroFilaImpressora++;
                            this.filaImpressora.add(new Fila(((Processo) this.listaProcesso.get(this.posicaoProcesso)).getId(), this.numeroFilaImpressora));
                        }
                        listaProcesso.get(this.posicaoProcesso).setEstado("BLOQUEADO");
                        listaProcesso.get(this.posicaoProcesso).setNumeroCiclosExecutando(0);
                        listaProcesso.get(this.posicaoProcesso).setTempoExecutandoTotalES(0);

                        numeroFilaBloqueados++;
                        filaBloqueados.add(new Fila(((Processo) this.listaProcesso.get(this.posicaoProcesso)).getId(), this.numeroFilaBloqueados));
                        processadorExecutando = false;
                        status = "RECURSO DE ENTRADA E SAIDA SOLICITADO";
                    }
                    if ((listaProcesso.get(this.posicaoProcesso)).getTempoExecutando() < this.listaProcesso.get(posicaoProcesso).getTempoTotal()) {
                        this.status = "PID: " + this.ultmPID + "  TE: " + listaProcesso.get(this.posicaoProcesso).getTempoExecutando() + " T: " + ( this.listaProcesso.get(this.posicaoProcesso)).getTempoTotal();
                        if (( this.listaProcesso.get(this.posicaoProcesso)).getNumeroCiclosExecutando() == 50) {
                            executandoParaApto ++;
                            (this.listaProcesso.get(this.posicaoProcesso)).setEstado("APTO");
                            (this.listaProcesso.get(this.posicaoProcesso)).setNumeroCiclosExecutando(0);

                            numeroFilaAptos++;
                            filaAptos.add(new Fila(( this.listaProcesso.get(this.posicaoProcesso)).getId(), this.numeroFilaAptos));
                            listaProcesso.get(posicaoProcesso).setTempoEspera(tempoEspera);
                            processadorExecutando = false;
                        }
                    } else {
                        listaProcesso.get(posicaoProcesso).setEstado("DESTRUIDO");

                        numeroFilaDestruido++;
                        filaDestruido.add(new Fila((listaProcesso.get(this.posicaoProcesso)).getId(), this.numeroFilaDestruido));
                        processadorExecutando = false;
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
                    (listaProcesso.get(this.posicaoProcessoHD)).setTempoExecutandoTotalES((listaProcesso.get(posicaoProcessoHD)).getTempoExecutandoTotalES() + 1);
                    if ((listaProcesso.get(this.posicaoProcessoHD)).getTempoTotalES() <= (listaProcesso.get(this.posicaoProcessoHD)).getTempoExecutandoTotalES()) {
                        this.numeroFilaAptos++;
                        (this.listaProcesso.get(this.posicaoProcessoHD)).setEstado("APTO");
                        ( this.listaProcesso.get(this.posicaoProcessoHD)).setTempoExecutandoTotalES(0);
                        this.filaAptos.add(new Fila((this.listaProcesso.get(this.posicaoProcessoHD)).getId(), this.numeroFilaAptos));
                        listaProcesso.get(posicaoProcesso).setTempoEspera(tempoEspera);
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
                    (this.listaProcesso.get(this.posicaoProcessoVideo)).setTempoExecutandoTotalES((this.listaProcesso.get(this.posicaoProcessoVideo)).getTempoExecutandoTotalES() + 1);
                    if (((Processo) this.listaProcesso.get(this.posicaoProcessoVideo)).getTempoTotalES() <= ((Processo) this.listaProcesso.get(this.posicaoProcessoVideo)).getTempoExecutandoTotalES()) {
                        this.numeroFilaAptos++;
                        ((Processo) this.listaProcesso.get(this.posicaoProcessoVideo)).setEstado("APTO");
                        ((Processo) this.listaProcesso.get(this.posicaoProcessoVideo)).setTempoExecutandoTotalES(0);
                        this.filaAptos.add(new Fila(((Processo) this.listaProcesso.get(this.posicaoProcessoVideo)).getId(), this.numeroFilaAptos));
                        listaProcesso.get(posicaoProcesso).setTempoEspera(tempoEspera);
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
                        listaProcesso.get(posicaoProcesso).setTempoEspera(tempoEspera);
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
        tempoEspera = 0;
        while (this.f3i < this.listaProcesso.size()) {
            this.tempoTotalMedioCiclos = ((long) ((Processo) this.listaProcesso.get(this.f3i)).getTempoTotal()) + this.tempoTotalMedioCiclos;
            tempoEspera += listaProcesso.get(f3i).getTempoEspera();
            this.f3i++;
        }
        tempoEspera = tempoEspera/NumeroDeProcessos;
        return this.tempoTotalMedioCiclos / ((long) this.NumeroDeProcessos);
    }


}

