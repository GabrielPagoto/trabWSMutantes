package com.example.trabwsmutantes.Controller;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.trabwsmutantes.ApiMutants.RetrofitConfig;
import com.example.trabwsmutantes.Model.Dashboard;
import com.example.trabwsmutantes.R;

import java.io.Serializable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardActivity extends AppCompatActivity implements Serializable {
    TextView numMutantes;
    TextView habilidade1;
    TextView habilidade2;
    TextView habilidade3;
    Dashboard dashboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        numMutantes = findViewById(R.id.numMutantes);
        habilidade1 = findViewById(R.id.firstHability);
        habilidade2 = findViewById(R.id.secondHability);
        habilidade3 = findViewById(R.id.thirdHability);

        SharedPreferences sharedPref = getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        int idLogado = sharedPref.getInt("id", 0);
        Call<Dashboard> call = new RetrofitConfig().getMutantService().getMutantDashboard(idLogado);
        call.enqueue(new Callback<Dashboard>() {
            @Override
            public void onResponse(Call<Dashboard> call, Response<Dashboard> response) {
                if(response.code() == 200){
                    dashboard = response.body();
                    numMutantes.setText(String.valueOf(dashboard.getNum()));
                    habilidade1.setText(dashboard.getTop3().get(0));
                    if (dashboard.getTop3().size() >= 2) {
                        habilidade2.setText(dashboard.getTop3().get(1));
                    }
                    if (dashboard.getTop3().size() == 3) {
                        habilidade3.setText(dashboard.getTop3().get(2));
                    }
                }
                else
                {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(DashboardActivity.this);
                    alertDialog.setTitle("Erro !!");
                    alertDialog.setMessage("Este usuário não possui mutantes cadastrados.");
                    alertDialog.setNegativeButton("Fechar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    alertDialog.create().show();
                }
            }

            @Override
            public void onFailure(Call<Dashboard> call, Throwable t) {

            }
        });
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