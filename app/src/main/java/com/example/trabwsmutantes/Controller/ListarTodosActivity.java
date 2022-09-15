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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trabwsmutantes.Adapter.AdapterMutantes;
import com.example.trabwsmutantes.ApiMutants.RetrofitConfig;
import com.example.trabwsmutantes.Model.Mutant;
import com.example.trabwsmutantes.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListarTodosActivity extends AppCompatActivity implements Serializable {
    private RecyclerView listagemMutantes;
    private List<Mutant> mutanteList = new ArrayList<Mutant>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_todos);

        listagemMutantes = findViewById(R.id.listaMutantes);
        //this.createMutante();


        SharedPreferences sharedPref = getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        int idLogado = sharedPref.getInt("id", 0);
        Call<List<Mutant>> call = new RetrofitConfig().getMutantService().getMutantList(idLogado);
        call.enqueue(new Callback<List<Mutant>>() {

            @Override
            public void onResponse(Call<List<Mutant>> call, Response<List<Mutant>> response) {
                if(response.code() == 204)
                {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(ListarTodosActivity.this);
                    alertDialog.setTitle("Atenção !!");
                    alertDialog.setMessage("Você ainda não possui Mutantes cadastrados!");
                    alertDialog.setNegativeButton("Fechar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    alertDialog .create().show();
                }
                else if(response.code() == 200){
                    mutanteList = response.body();
                    AdapterMutantes adapterMutante = new AdapterMutantes(mutanteList);
                    RecyclerView.LayoutManager layoutManager =
                            new LinearLayoutManager(getApplicationContext());
                    listagemMutantes.setLayoutManager(layoutManager);
                    listagemMutantes.setHasFixedSize(true);
                    listagemMutantes.setAdapter(adapterMutante);
                }
                else
                {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(ListarTodosActivity.this);
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
            public void onFailure(Call<List<Mutant>> call, Throwable t) {
                Log.e("erro", t.getMessage());
            }
        });
    }

    private void createMutante() {
        /*Mutante obj = new Mutante(R.drawable.img,"Teste 1", "Teste 1");
        mutanteList.add(obj);
        obj = new Mutante(R.drawable.ic_kablam_super_hero_flame,"Teste 2", "Teste 2");
        mutanteList.add(obj);
        obj = new Mutante(R.drawable.ic_launcher_background,"Teste 3", "Teste 3");
        mutanteList.add(obj);*/
    }

    public void onClick(@NonNull View view){
        TextView nome = view.findViewById(R.id.Name);
        ImageView img = view.findViewById(R.id.imageView);
        TextView idMutante = view.findViewById(R.id.idMutante);

        Intent it = new Intent(this, DetalheMutanteActivity.class);
        Bundle params = new Bundle();
        params.putInt("id", Integer.parseInt(idMutante.getText().toString()));
        it.putExtras(params);
        startActivity(it);
        finish();
    }

    public void voltar(View view){
        Intent it = new Intent(ListarTodosActivity.this,DashboardActivity.class);
        startActivity(it);
        finish();
    }
}