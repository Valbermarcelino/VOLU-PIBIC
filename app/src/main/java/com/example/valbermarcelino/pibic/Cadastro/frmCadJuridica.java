package com.example.valbermarcelino.pibic.Cadastro;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.valbermarcelino.pibic.Conexao;
import com.example.valbermarcelino.pibic.R;
import com.example.valbermarcelino.pibic.frmLogin;

public class frmCadJuridica extends AppCompatActivity {

    EditText txtNome, txtEmail3, txtCnpj, txtCnaeFiscal, txtTelefone, txtLogradouro, txtBairro, txtEstado, txtCep, txtSenha3, txtConf1;
    Button btOk2, btVoltar2;
    Spinner txtPorte;

    String url = "";
    String parametros = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frm_cad_juridica);


        txtNome = (EditText) findViewById(R.id.txtNome);
        txtPorte = (Spinner) findViewById(R.id.txtPorte);
        txtEmail3 = (EditText) findViewById(R.id.txtEmail2);
        txtCnpj = (EditText) findViewById(R.id.txtCnpj);
        txtCnaeFiscal = (EditText) findViewById(R.id.txtCnaeFiscal);
        txtTelefone = (EditText) findViewById(R.id.txtTelefone);
        txtLogradouro = (EditText) findViewById(R.id.txtLogradouro);
        txtBairro = (EditText) findViewById(R.id.txtBairro);
        txtEstado = (EditText) findViewById(R.id.txtEstado);
        txtCep = (EditText) findViewById(R.id.txtCep);
        txtSenha3 = (EditText) findViewById(R.id.txtSenha2);
        txtConf1 = (EditText) findViewById(R.id.txtConf);
        btOk2 = (Button) findViewById(R.id.btOk2);
        btVoltar2 = (Button) findViewById(R.id.btVoltar2);

        ArrayAdapter porte = ArrayAdapter.createFromResource(this, R.array.porte, android.R.layout.simple_spinner_item);
        txtPorte.setAdapter(porte);

        btOk2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtNome.getText().toString().isEmpty() || txtEmail3.getText().toString().isEmpty() || txtCnpj.getText().toString().isEmpty() || txtCnaeFiscal.getText().toString().isEmpty() || txtTelefone.getText().toString().isEmpty() || txtLogradouro.getText().toString().isEmpty() || txtBairro.getText().toString().isEmpty() || txtEstado.getText().toString().isEmpty() || txtCep.getText().toString().isEmpty() || txtSenha3.getText().toString().isEmpty() || txtConf1.getText().toString().isEmpty() ){
                    Toast.makeText(getApplicationContext(), "Preencha todos os campos!", Toast.LENGTH_LONG).show();
                }else if(txtSenha3.getText().toString().equals(txtConf1.getText().toString())) {
                    boolean lemes = false;
                    if (txtCnpj.getText().toString().length() < 11) {
                        txtCnpj.setTextColor(Color.RED);
                        lemes = true;
                    } else {
                        txtCnpj.setTextColor(Color.BLACK);
                    }
                    if (txtCep.getText().toString().length() < 8) {
                        txtCep.setTextColor(Color.RED);
                        lemes = true;
                    } else {
                        txtCep.setTextColor(Color.BLACK);
                    }
                    if (txtEstado.getText().toString().length() < 2) {
                        txtEstado.setTextColor(Color.RED);
                        lemes = true;
                    } else {
                        txtEstado.setTextColor(Color.BLACK);
                    }
                    if (lemes) {
                        Toast.makeText(getApplicationContext(), "ERRO: Dados inválidos detectados!", Toast.LENGTH_LONG).show();
                    } else {
                        if (txtSenha3.getText().toString().length() < 4 || txtSenha3.getText().toString().length() > 16) {
                            AlertDialog.Builder dlg = new AlertDialog.Builder(frmCadJuridica.this);
                            dlg.setMessage("A senha deve conter entre 4 e 8 caracteres!");
                            dlg.setNeutralButton("OK", null);
                            dlg.show();
                        }else{
                            ConnectivityManager connMgr = (ConnectivityManager)
                                    getSystemService(Context.CONNECTIVITY_SERVICE);
                            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                            if (networkInfo != null && networkInfo.isConnected()) {

                                String nome = txtNome.getText().toString();
                                String porte = txtPorte.getSelectedItem().toString();
                                String email = txtEmail3.getText().toString();
                                String CNPJ = txtCnpj.getText().toString();
                                String CNAEFiscal = txtCnaeFiscal.getText().toString();
                                String telefone = txtTelefone.getText().toString();
                                String logradouro = txtLogradouro.getText().toString();
                                String bairro = txtBairro.getText().toString();
                                String estado = txtEstado.getText().toString();
                                String cep = txtCep.getText().toString();
                                String senha = txtSenha3.getText().toString();

                                url = "http://voluntariadobbvolu.16mb.com/PIBICapp/cadastrarJuridica.php";
                                parametros = "$nome=" + nome + "&porte=" + porte + "&email=" + email + "&senha=" + senha + "&cnpj="+ CNPJ + "&cnaefiscal="+ CNAEFiscal + "&telefone=" + telefone + "&logradouro=" + logradouro + " &bairro=" + bairro + "&estado=" + estado + " &cep=" + cep + " &latitude=1&longitude=1";
                                new SolicitaDados().execute(url);


                            } else {
                                Toast.makeText(getApplicationContext(), "Não há conexão", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "As senhas não correspondem!", Toast.LENGTH_LONG).show();
                }
            }
        });




        btVoltar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private class SolicitaDados extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            return Conexao.postDados(urls[0], parametros);

        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String resultado) {
            if (resultado.contains("empresa ja cadastrada")) {
                Toast.makeText(getApplicationContext(), "Empresa já cadastrada", Toast.LENGTH_LONG).show();
            } else if (resultado.contains("registrado com sucesso")) {
                Toast.makeText(getApplicationContext(), "Registrado com sucesso!", Toast.LENGTH_LONG).show();
                Intent abreFormInicio = new Intent(frmCadJuridica.this, frmLogin.class);
                startActivity(abreFormInicio);
            } else {
                Toast.makeText(getApplicationContext(), "Erro ao cadastrar", Toast.LENGTH_LONG).show();
            }

        }
    }


}
