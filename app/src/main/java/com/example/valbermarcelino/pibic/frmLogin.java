package com.example.valbermarcelino.pibic;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.valbermarcelino.pibic.Cadastro.frmCadastro;


public class frmLogin extends AppCompatActivity {

    EditText txtEmail1, txtSenha1;
    Button btEntrar;
    TextView lblCadastro;

    String url = "";
    String parametros = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frm_login);

        txtEmail1 = (EditText) findViewById(R.id.txtEmail1);
        txtSenha1 = (EditText) findViewById(R.id.txtSenha1);
        btEntrar = (Button) findViewById(R.id.btEntrar);
        lblCadastro = (TextView) findViewById(R.id.txtCadastro);

        lblCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent abreCadastro = new Intent(frmLogin.this, frmCadastro.class);
                startActivity(abreCadastro);
            }
        });


        btEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connMgr = (ConnectivityManager)
                        getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {

                    String email = txtEmail1.getText().toString();
                    String senha = txtSenha1.getText().toString();

                    if (email.isEmpty() || senha.isEmpty()){
                        Toast.makeText(getApplicationContext(), "preencha os campos", Toast.LENGTH_LONG).show();
                    } else {
                        url = "http://voluntariadobbvolu.16mb.com/PIBICapp/login.php";
                        parametros = "email="+ email + "&senha=" + senha;
                        new SolicitaDados().execute(url);
                    }


                } else {
                    Toast.makeText(getApplicationContext(), "Não há conexão", Toast.LENGTH_LONG).show();
                }
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
            //textView.setText(result);
            if(resultado.contains("logado com sucesso?")){

                String[] dados = resultado.split(",");

                Intent abreFormInicio = new Intent(frmLogin.this, MainActivity.class);
                /*abreFormInicio.putExtra("id_usuario", dados[1]);
                abreFormInicio.putExtra("nome_usuario", dados[2]);*/
                startActivity(abreFormInicio);
            }else{
                Toast.makeText(getApplicationContext(), "Email e/ou senha incorreto(s)", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
