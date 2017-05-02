package com.example.valbermarcelino.pibic.Cadastro;

import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.valbermarcelino.pibic.Conexao;
import com.example.valbermarcelino.pibic.R;
import com.example.valbermarcelino.pibic.frmLogin;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class frmCadFisica extends AppCompatActivity {

    EditText txtNome, txtSobrenome, txtEmail2, txtCpf, txtNasc1, txtDdd, txtTelefone, txtRua, txtBairro, txtCep, txtSenha2, txtConf;
    Button btOk1, btVoltar1;
    Spinner spnEstado;

    ArrayAdapter<String> adpEstado;

    String url = "",nasc;
    String parametros = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frm_cad_fisica);

        txtNome = (EditText) findViewById(R.id.txtNome);
        txtSobrenome = (EditText) findViewById(R.id.txtSobrenome);
        txtEmail2 = (EditText) findViewById(R.id.txtEmail2);
        txtCpf = (EditText) findViewById(R.id.txtCpf);
        txtNasc1 = (EditText) findViewById(R.id.txtNasc1);
        txtDdd = (EditText) findViewById(R.id.txtDdd);
        txtTelefone = (EditText) findViewById(R.id.txtTelefone);
        txtRua = (EditText) findViewById(R.id.txtRua);
        txtBairro = (EditText) findViewById(R.id.txtBairro);
        txtCep = (EditText) findViewById(R.id.txtCep);
        txtSenha2 = (EditText) findViewById(R.id.txtSenha2);
        txtConf = (EditText) findViewById(R.id.txtConf);
        btOk1 = (Button) findViewById(R.id.btOk1);
        btVoltar1 = (Button) findViewById(R.id.btVoltar1);

        spnEstado = (Spinner) findViewById(R.id.spnEstado);

        adpEstado = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
        adpEstado.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spnEstado.setAdapter(adpEstado);

        String states[] = {"AC","AL","AP","AP","AM","BA","CE","DF","ES","GO","MA","MT","MS","MG","PA","PB","PR","PE","PI","RJ","RN","RS","RO","RR","SC","SP","SE","TO"};
        for (int i = 0; i <= 27; i++) {
            adpEstado.add(states[i]);
        }

        ExibeDataListener listener = new ExibeDataListener();

        txtNasc1.setOnClickListener(listener);
        txtNasc1.setOnFocusChangeListener(listener);
        txtNasc1.setKeyListener(null);

        btOk1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtNome.getText().toString().isEmpty() || txtSobrenome.getText().toString().isEmpty() || txtEmail2.getText().toString().isEmpty() || txtCpf.getText().toString().isEmpty() || txtNasc1.getText().toString().isEmpty() || txtDdd.getText().toString().isEmpty() || txtTelefone.getText().toString().isEmpty() || txtRua.getText().toString().isEmpty() || txtBairro.getText().toString().isEmpty() ||  txtCep.getText().toString().isEmpty() || txtSenha2.getText().toString().isEmpty() || txtConf.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Preencha todos os campos!", Toast.LENGTH_LONG).show();
                } else if (txtSenha2.getText().toString().equals(txtConf.getText().toString())) {
                    boolean jonathan = false;
                    if (!VerificarCPF.isCPF(txtCpf.getText().toString())) {
                        txtCpf.setTextColor(Color.RED);
                        jonathan = true;
                    } else {
                        txtCpf.setTextColor(Color.BLACK);
                    }
                    if (txtCep.getText().toString().length() < 8) {
                        txtCep.setTextColor(Color.RED);
                        jonathan = true;
                    } else {
                        txtCep.setTextColor(Color.BLACK);
                    }
                    if (txtDdd.getText().toString().length() < 2) {
                        txtDdd.setTextColor(Color.RED);
                        jonathan = true;
                    } else {
                        txtDdd.setTextColor(Color.BLACK);
                    }
                    String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
                    java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
                    java.util.regex.Matcher m = p.matcher(txtEmail2.getText().toString());

                    if (m.matches() == false) {
                        txtEmail2.setTextColor(Color.RED);
                        jonathan = true;
                    } else if (isValidEmail(txtEmail2.getText().toString()) == false) {
                        txtEmail2.setTextColor(Color.RED);
                        jonathan = true;
                    } else {
                        txtEmail2.setTextColor(Color.BLACK);
                    }

                    if (jonathan) {
                        Toast.makeText(getApplicationContext(), "ERRO: Dados inválidos detectados!", Toast.LENGTH_LONG).show();
                    } else {
                        if (txtSenha2.getText().toString().length() < 4 || txtSenha2.getText().toString().length() > 16) {
                            AlertDialog.Builder dlg = new AlertDialog.Builder(frmCadFisica.this);
                            dlg.setMessage("A senha deve conter entre 4 e 8 caracteres!");
                            dlg.setNeutralButton("OK", null);
                            dlg.show();
                        } else {
                            ConnectivityManager connMgr = (ConnectivityManager)
                                    getSystemService(Context.CONNECTIVITY_SERVICE);
                            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                            if (networkInfo != null && networkInfo.isConnected()) {

                                String nome = txtNome.getText().toString();
                                String sobrenome = txtSobrenome.getText().toString();
                                String email = txtEmail2.getText().toString();
                                String CPF = txtCpf.getText().toString();
                                String ddd = txtDdd.getText().toString();
                                String telefone = txtTelefone.getText().toString();
                                String rua = txtRua.getText().toString();
                                String bairro = txtBairro.getText().toString();
                                String cep = txtCep.getText().toString();
                                String senha = txtSenha2.getText().toString();


                                url = "http://voluntariadobbvolu.16mb.com/PIBICapp/cadastrar.php";
                                parametros = "nome=" + nome + "&email=" + email + "&senha=" + senha + "&sobrenome=" + sobrenome + "&cpf=" + CPF + "&nasc=" + nasc + "&ddd=" + ddd + "&telefone=" + telefone + "&rua=" + rua + "&bairro=" + bairro + "&estado=" + adpEstado.getItem(spnEstado.getSelectedItemPosition()) + "&cep=" + cep + "&latitude=1&longitude=1";
                                new SolicitaDados().execute(url);


                            } else {
                                Toast.makeText(getApplicationContext(), "Não há conexão", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "As senhas não correspondem!", Toast.LENGTH_LONG).show();
                }
            }
        });


        btVoltar1.setOnClickListener(new View.OnClickListener() {
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
            if (resultado.contains("usuario ja cadastrado")) {
                Toast.makeText(getApplicationContext(), "Usúario já cadastrado", Toast.LENGTH_LONG).show();
            } else if (resultado.contains("registrado com sucesso")) {
                Toast.makeText(getApplicationContext(), "Registrado com sucesso!", Toast.LENGTH_LONG).show();
                Intent abreFormInicio = new Intent(frmCadFisica.this, frmLogin.class);
                startActivity(abreFormInicio);
            } else {
                Toast.makeText(getApplicationContext(), "Erro ao cadastrar", Toast.LENGTH_LONG).show();
            }

        }
    }

    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    private void exibeData() {

        Calendar calendar = Calendar.getInstance();

        int ano = calendar.get(Calendar.YEAR);
        int mes = calendar.get(Calendar.MONTH);
        int dia = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dlg = new DatePickerDialog(this, new SelecionaDataListener(), ano, mes, dia);
        dlg.show();

    }

    private class ExibeDataListener implements View.OnClickListener, View.OnFocusChangeListener {


        @Override
        public void onClick(View v) {
            exibeData();
        }

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus) {
                exibeData();
            }
        }
    }


    private class SelecionaDataListener implements DatePickerDialog.OnDateSetListener {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            Calendar calendar = Calendar.getInstance();

            calendar.set(year, monthOfYear, dayOfMonth);

            Date data = calendar.getTime();

            DateFormat format = DateFormat.getDateInstance(DateFormat.DEFAULT);
            String dt = format.format(data);

            txtNasc1.setText(dt);

            nasc = year + "-" + String.format("%02d" , (monthOfYear + 1)) + "-" + dayOfMonth;

        }
    }

}
