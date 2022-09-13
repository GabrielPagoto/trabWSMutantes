package com.example.trabwsmutantes.Controller;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.trabwsmutantes.R;

public class LoginActivity extends AppCompatActivity {
    EditText login;
    EditText senha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }

    public void acessarAplicativo(View view){
        login = findViewById(R.id.login);
        senha = findViewById(R.id.senha);

        if (!login.getText().toString().isEmpty() && !senha.getText().toString().isEmpty()){
            Intent it = new Intent(LoginActivity.this,DashboardActivity.class);
            startActivity(it);
            finish();
        }
        else {
            AlertDialog.Builder selecionaFoto = new AlertDialog.Builder(LoginActivity.this);
            selecionaFoto.setTitle("Atenção !!");
            selecionaFoto.setMessage("Todos os dados devem ser informados.");
            selecionaFoto.setNegativeButton("Fechar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            selecionaFoto.create().show();
        }


    }
}