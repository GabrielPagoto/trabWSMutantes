package com.example.trabwsmutantes.Controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.trabwsmutantes.Adapter.AdapterMutantes;
import com.example.trabwsmutantes.Model.Mutante;
import com.example.trabwsmutantes.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PesquisarActivity extends AppCompatActivity implements Serializable {
    private RecyclerView listaMutantes;
    private List<Mutante> mutanteList = new ArrayList<Mutante>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesquisar);

        this.createMutante();
    }

    public void procurarHabilidade(View view) {
        listaMutantes = findViewById(R.id.listagemMutantes);
        AdapterMutantes adapterMutante = new AdapterMutantes(mutanteList);
        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(getApplicationContext());
        listaMutantes.setLayoutManager(layoutManager);
        listaMutantes.setHasFixedSize(true);
        listaMutantes.setAdapter(adapterMutante);
    }

    private void createMutante() {
       /* Mutante obj = new Mutante(R.drawable.img,"Teste 1", "Teste 1");
        mutanteList.add(obj);
        obj = new Mutante(R.drawable.ic_kablam_super_hero_flame,"Teste 2", "Teste 2");
        mutanteList.add(obj);
        obj = new Mutante(R.drawable.ic_launcher_background,"Teste 3", "Teste 3");
        mutanteList.add(obj);*/
    }

    public void voltar(View view){
        Intent it = new Intent(PesquisarActivity.this,DashboardActivity.class);
        startActivity(it);
        finish();
    }
}