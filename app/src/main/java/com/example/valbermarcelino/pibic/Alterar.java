package com.example.valbermarcelino.pibic;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Alterar extends AppCompatActivity {

    EditText txtNome4, txtSobrenome4, txtEmail4, txtDdd4, txtTelefone4, txtRua4, txtBairro4, txtCep4;
    Button btOk4, btVoltar4;
    Spinner spnEstado;

    ArrayAdapter<String> adpEstado;

    String url = "",nasc;
    String parametros = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frm_cad_fisica);

        txtNome4 = (EditText) findViewById(R.id.txtNome4);
        txtSobrenome4 = (EditText) findViewById(R.id.txtSobrenome4);
        txtEmail4 = (EditText) findViewById(R.id.txtEmail4);
        txtDdd4 = (EditText) findViewById(R.id.txtDdd4);
        txtTelefone4 = (EditText) findViewById(R.id.txtTelefone4);
        txtRua4 = (EditText) findViewById(R.id.txtRua4);
        txtBairro4 = (EditText) findViewById(R.id.txtBairro4);
        txtCep4 = (EditText) findViewById(R.id.txtCep4);
        btOk4 = (Button) findViewById(R.id.btOk4);
        btVoltar4 = (Button) findViewById(R.id.btVoltar4);

        spnEstado = (Spinner) findViewById(R.id.spnEstado);

        adpEstado = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
        adpEstado.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spnEstado.setAdapter(adpEstado);

        String states[] = {"AC","AL","AP","AP","AM","BA","CE","DF","ES","GO","MA","MT","MS","MG","PA","PB","PR","PE","PI","RJ","RN","RS","RO","RR","SC","SP","SE","TO"};
        for (int i = 0; i <= 27; i++) {
            adpEstado.add(states[i]);
        }

        btOk4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean santos = false;
                if (txtNome4.getText().toString().isEmpty() || txtSobrenome4.getText().toString().isEmpty() || txtEmail4.getText().toString().isEmpty() || txtDdd4.getText().toString().isEmpty() || txtTelefone4.getText().toString().isEmpty() || txtRua4.getText().toString().isEmpty() || txtBairro4.getText().toString().isEmpty() ||  txtCep4.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Preencha todos os campos!", Toast.LENGTH_LONG).show();
                } else if (txtCep4.getText().toString().length() < 8) {
                        txtCep4.setTextColor(Color.RED);
                        santos = true;
                    } else {
                        txtCep4.setTextColor(Color.BLACK);
                    }
                    if (txtDdd4.getText().toString().length() < 2) {
                        txtDdd4.setTextColor(Color.RED);
                        santos = true;
                    } else {
                        txtDdd4.setTextColor(Color.BLACK);
                    }
                    String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
                    java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
                    java.util.regex.Matcher m = p.matcher(txtEmail4.getText().toString());

                    if (m.matches() == false) {
                        txtEmail4.setTextColor(Color.RED);
                        santos = true;
                    } else if (isValidEmail2(txtEmail4.getText().toString()) == false) {
                        txtEmail4.setTextColor(Color.RED);
                        santos = true;
                    } else {
                        txtEmail4.setTextColor(Color.BLACK);
                    }

                    if (santos) {
                        Toast.makeText(getApplicationContext(), "ERRO: Dados inválidos detectados!", Toast.LENGTH_LONG).show();
                    } else {
                            ConnectivityManager connMgr = (ConnectivityManager)
                                    getSystemService(Context.CONNECTIVITY_SERVICE);
                            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                            if (networkInfo != null && networkInfo.isConnected()) {

                                String nome = txtNome4.getText().toString();
                                String sobrenome = txtSobrenome4.getText().toString();
                                String email = txtEmail4.getText().toString();
                                String ddd = txtDdd4.getText().toString();
                                String telefone = txtTelefone4.getText().toString();
                                String rua = txtRua4.getText().toString();
                                String bairro = txtBairro4.getText().toString();
                                String cep = txtCep4.getText().toString();


                                url = "http://voluntariadobbvolu.16mb.com/PIBICapp/cadastrar.php";
                                parametros = "nome=" + nome + "&email=" + email + "&sobrenome=" + sobrenome +"&nasc=" + nasc + "&ddd=" + ddd + "&telefone=" + telefone + "&rua=" + rua + "&bairro=" + bairro + "&estado=" + adpEstado.getItem(spnEstado.getSelectedItemPosition()) + "&cep=" + cep + "&latitude=1&longitude=1";
                                new SolicitaDados2().execute(url);


                            } else {
                                Toast.makeText(getApplicationContext(), "Não há conexão", Toast.LENGTH_LONG).show();
                            }

                    }
                }
        });


        btVoltar4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private class SolicitaDados2 extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            return Conexao.postDados(urls[0], parametros);

        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String resultado) {
            if (resultado.contains("usuario ja cadastrado")) {
                Toast.makeText(getApplicationContext(), "Usúario já cadastrado", Toast.LENGTH_LONG).show();
            } else if (resultado.contains("registrado com sucesso")) {
                Toast.makeText(getApplicationContext(), "Registrado com sucesso!", Toast.LENGTH_LONG).show();
                Intent abreFormInicio = new Intent(Alterar.this, MainActivity.class);
                startActivity(abreFormInicio);
            } else {
                Toast.makeText(getApplicationContext(), "Erro ao cadastrar", Toast.LENGTH_LONG).show();
            }

        }
    }

    public final static boolean isValidEmail2(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }




}
