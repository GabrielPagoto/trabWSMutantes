package com.example.trabwsmutantes.Controller;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.trabwsmutantes.Model.Mutante;
import com.example.trabwsmutantes.R;

import java.io.Serializable;

public class DetalheMutanteActivity extends AppCompatActivity implements Serializable {
    Mutante mutante;

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
                String sNome = params.getString("nome");

                /*
                 * AQUI FAZER A BUSCA E JOGAR OS DADOS NA TELA, CODIGO ABAIXO FOI CRIADO APENAS PARA TESTES.
                 * */

                switch (sNome) {
                    case "Teste 1":
                        mutante = new Mutante(R.drawable.img, sNome, "Habilidade 1");
                        img.setImageResource(R.drawable.img);
                        nome.setText(mutante.getNome());
                        habilidade1.setText("Habilidade 1");
                        //habilidade2.setText("Habilidade 2");
                        //habilidade3.setText("Habilidade 3");
                        break;
                    case "Teste 2":
                        mutante = new Mutante(R.drawable.ic_kablam_super_hero_flame, sNome,
                                "Habilidade 1", "Habilidade 2");
                        nome.setText(mutante.getNome());
                        img.setImageResource(R.drawable.ic_kablam_super_hero_flame);
                        habilidade1.setText("Habilidade 1");
                        habilidade2.setText("Habilidade 2");
                        break;
                    case "Teste 3":
                        mutante = new Mutante(R.drawable.ic_launcher_background, sNome,
                                "Habilidade 1", "Habilidade 2", "Habilidade 3");
                        nome.setText(mutante.getNome());
                        img.setImageResource(R.drawable.ic_launcher_background);
                        habilidade1.setText("Habilidade 1");
                        habilidade2.setText("Habilidade 2");
                        habilidade3.setText("Habilidade 3");
                        break;
                    case "object":
                        mutante = (Mutante) getIntent().getSerializableExtra("mutante");
                        nome.setText(mutante.getNome());
                        img.setImageResource(mutante.getImg());
                        habilidade1.setText(mutante.getHabilidade1());
                        habilidade2.setText(mutante.getHabilidade2());
                        habilidade3.setText(mutante.getHabilidade3());
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + sNome);
                }
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