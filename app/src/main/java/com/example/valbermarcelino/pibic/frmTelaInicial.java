package com.example.valbermarcelino.pibic;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class frmTelaInicial extends AppCompatActivity {

    TextView lblNome,lblId;
    String nomeUsuario, idUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frm_tela_inicial);
        lblNome = (TextView)findViewById(R.id.lblNome);
        lblId = (TextView)findViewById(R.id.lblId);

        nomeUsuario = getIntent().getExtras().getString("nome_usuario");
        idUsuario = getIntent().getExtras().getString("id_usuario");

        lblNome.setText(nomeUsuario);
        lblId.setText(idUsuario);
    }
}
