package com.example.trabwsmutantes.Controller;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.trabwsmutantes.ApiMutants.RetrofitConfig;
import com.example.trabwsmutantes.Model.Mutant;
import com.example.trabwsmutantes.Model.Mutante;
import com.example.trabwsmutantes.R;

import java.io.Serializable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalheMutanteActivity extends AppCompatActivity implements Serializable {
    Mutante mutante;
    static String url = "https://7a3b-2804-7f4-378e-dc86-a49f-d767-d316-473c.sa.ngrok.io/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_mutante);

        Intent it = getIntent();
        TextView nome = findViewById(R.id.nome);
        TextView habilidade1 = findViewById(R.id.habilidade1);
        TextView habilidade2 = findViewById(R.id.habilidade2);
        TextView habilidade3 = findViewById(R.id.habilidade3);
        TextView nomeCriador = findViewById(R.id.nomeCriador);
        ImageView img = findViewById(R.id.imgV);


        if (it != null) {
            Bundle params = it.getExtras();
            if (params != null) {
                Call<Mutant> call = new RetrofitConfig().getMutantService().getMutant(params.getInt("id"));
                call.enqueue(new Callback<Mutant>() {
                    @Override
                    public void onResponse(Call<Mutant> call, Response<Mutant> response) {
                        if (response.code() == 204) {
                            AlertDialog.Builder alertDialog = new AlertDialog.Builder(DetalheMutanteActivity.this);
                            alertDialog.setTitle("Atenção !!");
                            alertDialog.setMessage("Não existe nenhum Mutante com o ID fornecido (trocar pra nome depois)!");
                            alertDialog.setNegativeButton("Fechar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });
                            alertDialog.create().show();
                        } else if (response.code() == 200) {
                            SharedPreferences sharedPref = getSharedPreferences(
                                    getString(R.string.preference_file_key), Context.MODE_PRIVATE);
                            String nomeC = sharedPref.getString("email", "");
                            Mutant mutant = response.body();
                            nome.setText(mutant.getName());
                            habilidade1.setText(mutant.getAbilits().get(0));
                            if (mutant.getAbilits().size() == 2) {
                                habilidade2.setText(mutant.getAbilits().get(1));
                            }
                            if (mutant.getAbilits().size() == 3) {
                                habilidade3.setText(mutant.getAbilits().get(2));
                            }
                            nomeCriador.setText(nomeC);
                        } else {
                            AlertDialog.Builder alertDialog = new AlertDialog.Builder(DetalheMutanteActivity.this);
                            alertDialog.setTitle("Erro !!");
                            alertDialog.setMessage("Erro interno, tente novamente mais tarde");
                            alertDialog.setNegativeButton("Fechar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });
                            alertDialog.create().show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Mutant> call, Throwable t) {
                        Log.e("erro", t.getMessage());
                    }
                });
            }
        }
    }

    public void editarMutante(View view){
        //Intent intentGo = getIntent();
        //Bundle var = intentGo.getExtras();
        Intent intent = new Intent(DetalheMutanteActivity.this,EditarMutanteActivity.class);
        //Bundle params = new Bundle();
        intent.putExtra("mutante", mutante);
        /*params.putString("nome", var.getString("nome"));
        params.putString("imagem", "ic_kablam_super_hero_flame");
        params.putString("habilidade1", var.getString("nome"));
        params.putString("habilidade2", var.getString("nome"));
        params.putString("habilidade3", var.getString("nome"));*/
        //intent.putExtras(params);
        startActivity(intent);
        finish();
    }

    public void excluirMutante(View view){
        AlertDialog.Builder selecionaFoto = new AlertDialog.Builder(DetalheMutanteActivity.this);
        selecionaFoto.setTitle("Atenção !!");
        selecionaFoto.setMessage("Deseja realmente excluir o mutante " + mutante.getNome() + "?");
        selecionaFoto.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent it = new Intent(DetalheMutanteActivity.this,ListarTodosActivity.class);
                startActivity(it);
                finish();
            }
        });
        selecionaFoto.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        selecionaFoto.create().show();
    }

    public void voltar(View view){
        Intent it = new Intent(DetalheMutanteActivity.this,ListarTodosActivity.class);
        startActivity(it);
        finish();
    }
}