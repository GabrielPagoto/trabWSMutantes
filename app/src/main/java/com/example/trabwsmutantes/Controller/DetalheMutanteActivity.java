package com.example.trabwsmutantes.Controller;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.trabwsmutantes.Adapter.AdapterMutantes;
import com.example.trabwsmutantes.ApiMutants.RetrofitConfig;
import com.example.trabwsmutantes.Model.Mutant;
import com.example.trabwsmutantes.R;

import java.io.InputStream;
import java.io.Serializable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalheMutanteActivity extends AppCompatActivity implements Serializable {
    static String url = "https://08b1-2804-7f4-378e-dc86-ed30-ec7c-e28e-1505.sa.ngrok.io/";
    Mutant mutant;
    Intent it;
    Bundle params;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_mutante);

        it = getIntent();
        TextView nome = findViewById(R.id.nome);
        TextView habilidade1 = findViewById(R.id.habilidade1);
        TextView habilidade2 = findViewById(R.id.habilidade2);
        TextView habilidade3 = findViewById(R.id.habilidade3);
        TextView nomeCriador = findViewById(R.id.nomeCriador);
        ImageView img = findViewById(R.id.imgV);


        if (it != null) {
            params = it.getExtras();
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
                            mutant = response.body();
                            nome.setText(mutant.getName());
                            habilidade1.setText(mutant.getAbilits().get(0));
                            if (mutant.getAbilits().size() >= 2) {
                                habilidade2.setText(mutant.getAbilits().get(1));
                            }
                            if (mutant.getAbilits().size() == 3) {
                                habilidade3.setText(mutant.getAbilits().get(2));
                            }
                            nomeCriador.setText(nomeC);
                            Bundle bundle = getIntent().getExtras();
                            if (bundle != null) {
                                try {
                                    new AdapterMutantes.DownloadImageFromInternet((ImageView) img).execute(url+mutant.getPhoto());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
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

    private class DownloadImageFromInternet extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;
        public DownloadImageFromInternet(ImageView imageView) {
            this.imageView=imageView;
        }
        protected Bitmap doInBackground(String... urls) {
            String imageURL=urls[0];
            Bitmap bimage=null;
            try {
                InputStream in=new java.net.URL(imageURL).openStream();
                bimage=BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error Message", e.getMessage());
                e.printStackTrace();
            }
            return bimage;
        }
        protected void onPostExecute(Bitmap result) {
            imageView.setImageBitmap(result);
        }
    }

    public void editarMutante(View view){
        //Intent intentGo = getIntent();
        //Bundle var = intentGo.getExtras();
        Intent intentNew = new Intent(DetalheMutanteActivity.this,EditarMutanteActivity.class);
        Bundle paramsNew = new Bundle();
        //intent.putExtra("mutante", mutant);
        /*params.putString("nome", var.getString("nome"));
        params.putString("imagem", "ic_kablam_super_hero_flame");
        params.putString("habilidade1", var.getString("nome"));
        params.putString("habilidade2", var.getString("nome"));
        params.putString("habilidade3", var.getString("nome"));*/
        //intent.putExtras(params);
        paramsNew.putInt("id", mutant.getId());
        intentNew.putExtras(params);
        System.out.println("PASSOU O ID: "+ mutant.getId());
        startActivity(intentNew);
        finish();
    }

    public void excluirMutante(View view){
        AlertDialog.Builder selecionaFoto = new AlertDialog.Builder(DetalheMutanteActivity.this);
        selecionaFoto.setTitle("Atenção !!");
        selecionaFoto.setMessage("Deseja realmente excluir o mutante " + mutant.getName() + " ?");
        selecionaFoto.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Call<Mutant> call = new RetrofitConfig().getMutantService().deleteMutant(params.getInt("id"));
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
                            Intent intentNova = new Intent(DetalheMutanteActivity.this,ListarTodosActivity.class);
                            startActivity(intentNova);
                            finish();
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
        System.out.println("CLICOU AQUI!");
        selecionaFoto.create().show();

    }

    public void voltar(View view){
        Intent it = new Intent(DetalheMutanteActivity.this,ListarTodosActivity.class);
        startActivity(it);
        finish();
    }
}