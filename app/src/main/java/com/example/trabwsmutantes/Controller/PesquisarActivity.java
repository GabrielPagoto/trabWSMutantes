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
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trabwsmutantes.Adapter.AdapterMutantes;
import com.example.trabwsmutantes.ApiMutants.RetrofitConfig;
import com.example.trabwsmutantes.Model.Mutant;
import com.example.trabwsmutantes.R;

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PesquisarActivity extends AppCompatActivity implements Serializable {
    static String url = "https://08b1-2804-7f4-378e-dc86-ed30-ec7c-e28e-1505.sa.ngrok.io/";
    private RecyclerView listaMutantes;
    private List<Mutant> mutanteList = new ArrayList<Mutant>();
    EditText habilidadeMutante;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesquisar);
        listaMutantes = findViewById(R.id.listagemMutantes);
        habilidadeMutante = findViewById(R.id.habilidadeMutante);
    }

    public void procurarHabilidade(View view) {
        SharedPreferences sharedPref = getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        int idLogado = sharedPref.getInt("id", 0);

        if(habilidadeMutante.getText().toString().isEmpty()){
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(PesquisarActivity.this);
            alertDialog.setTitle("Atenção !!");
            alertDialog.setMessage("Por favor, informar uma habilidade válida!");
            alertDialog.setNegativeButton("Fechar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            alertDialog.create().show();
        }
        else{
            Call<List<Mutant>> call = new RetrofitConfig().getMutantService()
                    .getMutantListHabilities(idLogado, habilidadeMutante.getText().toString());
            call.enqueue(new Callback<List<Mutant>>() {
                @Override
                public void onResponse(Call<List<Mutant>> call, Response<List<Mutant>> response) {
                    if(response.code() == 204)
                    {
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(PesquisarActivity.this);
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
                        System.out.println("LISTA: "+ mutanteList.toString());
                        AdapterMutantes adapterMutante = new AdapterMutantes(mutanteList);
                        RecyclerView.LayoutManager layoutManager =
                                new LinearLayoutManager(getApplicationContext());
                        listaMutantes.setLayoutManager(layoutManager);
                        listaMutantes.setHasFixedSize(true);
                        listaMutantes.setAdapter(adapterMutante);
                    }
                    else
                    {
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(PesquisarActivity.this);
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

                }
            });
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
                bimage= BitmapFactory.decodeStream(in);
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

    public void voltar(View view){
        Intent it = new Intent(PesquisarActivity.this,DashboardActivity.class);
        startActivity(it);
        finish();
    }
}