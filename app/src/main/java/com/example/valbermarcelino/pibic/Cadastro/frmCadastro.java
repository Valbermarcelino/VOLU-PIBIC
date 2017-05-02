package com.example.valbermarcelino.pibic.Cadastro;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ToggleButton;

import com.example.valbermarcelino.pibic.R;

public class frmCadastro extends AppCompatActivity {

    ToggleButton btPessoa;
    Button btOk, btVoltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frm_cadastro);

        btPessoa = (ToggleButton) findViewById(R.id.btPessoa);
        btOk = (Button) findViewById(R.id.btOk);
        btVoltar = (Button) findViewById(R.id.btVoltar);

        btVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btPessoa.getText().toString().equalsIgnoreCase("Pessoa Física")) {
                    Intent abreFisica = new Intent(frmCadastro.this, frmCadFisica.class);
                    startActivity(abreFisica);
                } else if (btPessoa.getText().toString().equalsIgnoreCase("Pessoa Jurídica")) {
                    Intent abreJuridica = new Intent(frmCadastro.this, frmCadJuridica.class);
                    startActivity(abreJuridica);
                }
            }
        });
    }
}
