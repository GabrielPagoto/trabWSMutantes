package com.example.trabwsmutantes.Controller;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.trabwsmutantes.Adapter.AdapterMutantes;
import com.example.trabwsmutantes.ApiMutants.RetrofitConfig;
import com.example.trabwsmutantes.Model.Mutant;
import com.example.trabwsmutantes.Model.Mutante;
import com.example.trabwsmutantes.R;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditarMutanteActivity extends AppCompatActivity implements Serializable {
    ImageView img;
    TextView nome;
    Bitmap bitmap;
    TextView habilidade1;
    TextView habilidade2;
    TextView habilidade3;
    EditText imgTitle;
    Intent intent = getIntent();
    private static final int IMAGE = 100;
    Mutant mutant;
    static String url = "https://08b1-2804-7f4-378e-dc86-ed30-ec7c-e28e-1505.sa.ngrok.io/";
    Intent it = getIntent();
    Bundle paramsNew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_mutante);
        Intent intent = getIntent();

        img = findViewById(R.id.imageMutante);
        nome = findViewById(R.id.nomeMutante);
        habilidade1 = findViewById(R.id.habilidade1Mutante);
        habilidade2 = findViewById(R.id.habilidade2Mutante);
        habilidade3 = findViewById(R.id.habilidade3Mutante);
        if (intent != null) {
            paramsNew = intent.getExtras();
            if (paramsNew != null) {
                Call<Mutant> call = new RetrofitConfig().getMutantService().getMutant(paramsNew.getInt("id"));
                call.enqueue(new Callback<Mutant>() {
                    @Override
                    public void onResponse(Call<Mutant> call, Response<Mutant> response) {
                        if (response.code() == 204) {
                            AlertDialog.Builder alertDialog = new AlertDialog.Builder(EditarMutanteActivity.this);
                            alertDialog.setTitle("Atenção !!");
                            alertDialog.setMessage("Não existe nenhum Mutante com o ID fornecido (trocar pra nome depois)!");
                            alertDialog.setNegativeButton("Fechar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });
                            alertDialog.create().show();
                        } else if (response.code() == 200) {
                            mutant = response.body();
                            nome.setText(mutant.getName());
                            habilidade1.setText(mutant.getAbilits().get(0));
                            if (mutant.getAbilits().size() >= 2) {
                                habilidade2.setText(mutant.getAbilits().get(1));
                            }
                            if (mutant.getAbilits().size() == 3) {
                                habilidade3.setText(mutant.getAbilits().get(2));
                            }
                            Bundle bundle = getIntent().getExtras();
                            if (bundle != null) {
                                try {
                                    new AdapterMutantes.DownloadImageFromInternet((ImageView) img).execute(url+mutant.getPhoto());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        } else {
                            AlertDialog.Builder alertDialog = new AlertDialog.Builder(EditarMutanteActivity.this);
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
        /*mutante = (Mutante) getIntent().getSerializableExtra("mutante");
        //img.setImageResource(mutante.getImg());
        nome.setText(mutante.getNome());
        habilidade1.setText(mutante.getHabilidade1());
        habilidade2.setText(mutante.getHabilidade2());
        habilidade3.setText(mutante.getHabilidade3());*/
    }

    public void realizarAlteracao(View view) {
        img = findViewById(R.id.imageMutante);
        nome = findViewById(R.id.nomeMutante);
        habilidade1 = findViewById(R.id.habilidade1Mutante);
        habilidade2 = findViewById(R.id.habilidade2Mutante);
        habilidade3 = findViewById(R.id.habilidade3Mutante);

        if (img.toString() != null && nome.getText().toString() != null && habilidade1.getText().toString() != null) {
            Mutante mutanteNovo = new Mutante();
            //CORRIGIR mutanteNovo.setImg(Integer.parseInt(img.toString()));
            mutanteNovo.setNome(nome.getText().toString());
            mutanteNovo.setHabilidade1(habilidade1.getText().toString());
            if (habilidade2.getText().toString() != null) {
                mutanteNovo.setHabilidade2(habilidade2.getText().toString());
            }
            if (habilidade3.getText().toString() != null) {
                mutanteNovo.setHabilidade3(habilidade3.getText().toString());
            }
            Intent intent = new Intent(EditarMutanteActivity.this, DetalheMutanteActivity.class);
            Bundle params = new Bundle();
            params.putString("nome", "object");
            intent.putExtras(params);
            intent.putExtra("mutante", mutanteNovo);
            startActivity(intent);
            finish();

        }

    }

    public void voltar(View view) {
        Intent it = new Intent(EditarMutanteActivity.this, DetalheMutanteActivity.class);
        Bundle paramBack = new Bundle();
        paramBack.putInt("id", mutant.getId());
        it.putExtras(paramBack);
        startActivity(it);
        finish();
    }

    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMAGE);
    }

    private String convertToString() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imgByte = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgByte, Base64.DEFAULT);
    }

    private void uploadImage() {

        String image = convertToString();
        String imageName = imgTitle.getText().toString();

        Call<Mutant> call = new RetrofitConfig().getMutantService().getMutant();

        call.enqueue(new Callback<Mutant>() {
            @Override
            public void onResponse(Call<Mutant> call, Response<Mutant> response) {

                Mutant img_pojo = response.body();
                Log.d("Server Response", "" + img_pojo);

            }

            @Override
            public void onFailure(Call<Mutant> call, Throwable t) {
                Log.d("Server Response", "" + t.toString());

            }
        });

    }

}