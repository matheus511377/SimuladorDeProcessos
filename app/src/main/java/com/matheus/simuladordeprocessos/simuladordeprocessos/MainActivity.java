package com.matheus.simuladordeprocessos.simuladordeprocessos;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.matheus.simuladordeprocessos.R;
import com.matheus.simuladordeprocessos.simuladordeprocessos.Classes.SegundoPlano;

/**
 * Created by Matheus on 13/09/2016.
 */
public class MainActivity extends AppCompatActivity {
    private Button btnVai;
    private EditText edtProcesso;
    private EditText edtTempo;
    public ExpandableListView lstView;
    private TextView txtExecutando;
    private TextView txtRelatorio;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.txtExecutando = (TextView) findViewById(R.id.execucao);
        this.edtProcesso = (EditText) findViewById(R.id.edtProcessos);
        this.edtTempo = (EditText) findViewById(R.id.edtTempo);
        this.btnVai = (Button) findViewById(R.id.btnOk);
        this.txtRelatorio = (TextView) findViewById(R.id.txtRelatorio);
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    public void click(View view) {
        this.lstView = (ExpandableListView) findViewById(R.id.lstView);
        if (TextUtils.isEmpty(this.edtProcesso.getText())) {
            this.edtProcesso.requestFocus();
            this.edtProcesso.setError("Obrigat\u00f3rio");
        } else if (TextUtils.isEmpty(this.edtTempo.getText())) {
            this.edtTempo.requestFocus();
            this.edtTempo.setError("Obrigat\u00f3rio");
        } else {
            new SegundoPlano(this, this.txtExecutando, this.lstView, Integer.valueOf(this.edtProcesso.getText().toString()).intValue(), (long) Integer.valueOf(this.edtTempo.getText().toString()).intValue(), this.btnVai, this.txtRelatorio).execute(new Integer[]{Integer.valueOf(1)});
        }
    }
}