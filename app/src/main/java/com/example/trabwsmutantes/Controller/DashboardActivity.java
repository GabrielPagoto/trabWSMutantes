package com.example.trabwsmutantes.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.trabwsmutantes.R;

import java.io.Serializable;

public class DashboardActivity extends AppCompatActivity implements Serializable {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
    }

    public void sair(View view){
        finish();
    }

    public void pesquisarMutante(View view){
        Intent it = new Intent(DashboardActivity.this,PesquisarActivity.class);
        startActivity(it);
        finish();
    }

    public void activityCadastro(View view){
        Intent it = new Intent(DashboardActivity.this,CadastroActivity.class);
        startActivity(it);
        finish();
    }

    public void activityListarTodos(View view){
        Intent it = new Intent(DashboardActivity.this,ListarTodosActivity.class);
        startActivity(it);
        finish();
    }
}