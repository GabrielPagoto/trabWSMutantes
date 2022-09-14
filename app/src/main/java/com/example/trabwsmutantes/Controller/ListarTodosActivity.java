package com.example.trabwsmutantes.Controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.trabwsmutantes.Adapter.AdapterMutantes;
import com.example.trabwsmutantes.Model.Mutante;
import com.example.trabwsmutantes.R;
import com.example.trabwsmutantes.Utils.ItemOffsetDecoration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListarTodosActivity extends AppCompatActivity implements Serializable {
    private RecyclerView listagemMutantes;
    private List<Mutante> mutanteList = new ArrayList<Mutante>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_todos);

        listagemMutantes = findViewById(R.id.listaMutantes);
        this.createMutante();
        AdapterMutantes adapterMutante = new AdapterMutantes(mutanteList);
        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(getApplicationContext());
        listagemMutantes.setLayoutManager(layoutManager);
        listagemMutantes.setHasFixedSize(true);
        listagemMutantes.setAdapter(adapterMutante);
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

        String n = nome.getText().toString();

        Intent it = new Intent(this, DetalheMutanteActivity.class);
        Bundle params = new Bundle();
        params.putString("nome", n);
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