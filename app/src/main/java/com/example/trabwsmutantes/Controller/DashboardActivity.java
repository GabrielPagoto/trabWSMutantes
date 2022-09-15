package com.example.trabwsmutantes.Controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.trabwsmutantes.R;

import java.io.Serializable;

public class DashboardActivity extends AppCompatActivity implements Serializable {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
    }

    public void sair(View view){
        SharedPreferences sharedPref = getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.clear();
        editor.commit();
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
        //Intent it = new Intent(DashboardActivity.this,DetalheMutanteActivity.class);
        startActivity(it);
        finish();
    }
}