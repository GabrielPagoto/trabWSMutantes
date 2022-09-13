package com.example.trabwsmutantes.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.trabwsmutantes.Model.Mutante;
import com.example.trabwsmutantes.R;

import java.io.Serializable;

public class EditarMutanteActivity extends AppCompatActivity implements Serializable {
    ImageView img;
    TextView nome;
    TextView habilidade1;
    TextView habilidade2;
    TextView habilidade3;
    Intent intent = getIntent();
    Mutante mutante;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_mutante);
        img = findViewById(R.id.imageMutante);
        nome = findViewById(R.id.nomeMutante);
        habilidade1 = findViewById(R.id.habilidade1Mutante);
        habilidade2 = findViewById(R.id.habilidade2Mutante);
        habilidade3 = findViewById(R.id.habilidade3Mutante);

        mutante = (Mutante) getIntent().getSerializableExtra("mutante");
        img.setImageResource(mutante.getImg());
        nome.setText(mutante.getNome());
        habilidade1.setText(mutante.getHabilidade1());
        habilidade2.setText(mutante.getHabilidade2());
        habilidade3.setText(mutante.getHabilidade3());
    }

    public void realizarAlteracao(View view){
        img = findViewById(R.id.imageMutante);
        nome = findViewById(R.id.nomeMutante);
        habilidade1 = findViewById(R.id.habilidade1Mutante);
        habilidade2 = findViewById(R.id.habilidade2Mutante);
        habilidade3 = findViewById(R.id.habilidade3Mutante);

        if (img.toString() != null && nome.getText().toString() != null && habilidade1.getText().toString() != null){
            Mutante mutanteNovo = new Mutante();
            //CORRIGIR mutanteNovo.setImg(Integer.parseInt(img.toString()));
            mutanteNovo.setNome(nome.getText().toString());
            mutanteNovo.setHabilidade1(habilidade1.getText().toString());
            if(habilidade2.getText().toString() != null){
                mutanteNovo.setHabilidade2(habilidade2.getText().toString());
            }
            if(habilidade3.getText().toString() != null){
                mutanteNovo.setHabilidade3(habilidade3.getText().toString());
            }
            Intent intent = new Intent(EditarMutanteActivity.this,DetalheMutanteActivity.class);
            Bundle params = new Bundle();
            params.putString("nome", "object");
            intent.putExtras(params);
            intent.putExtra("mutante", mutanteNovo);
            startActivity(intent);
            finish();

        }

    }

    public void voltar(View view){
        Intent it = new Intent(EditarMutanteActivity.this,DetalheMutanteActivity.class);
        Bundle paramBack = new Bundle();
        paramBack.putString("nome", mutante.getNome());
        it.putExtras(paramBack);
        startActivity(it);
        finish();
    }
}