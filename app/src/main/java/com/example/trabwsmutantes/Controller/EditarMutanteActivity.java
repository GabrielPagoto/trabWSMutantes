package com.example.trabwsmutantes.Controller;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import com.example.trabwsmutantes.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
    Bitmap fotoRedimensionada;
    File imageFileName;
    String fotoEmString;

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
    }

    public void alterarImagem(View view){
        AlertDialog.Builder selecionaFoto = new AlertDialog.Builder(EditarMutanteActivity.this);
        selecionaFoto.setTitle("Origem da foto");
        selecionaFoto.setMessage("Por favor, selecione a origem da foto");
        selecionaFoto.setPositiveButton("Galeria", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,3);
            }
        });
        selecionaFoto.create().show();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent dados) {

        super.onActivityResult(requestCode, resultCode, dados);
        if(requestCode == 3){
            if(resultCode == RESULT_OK){
                try{
                    Uri imageUri = dados.getData();

                    Bitmap fotoBuscada = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                    fotoRedimensionada = Bitmap.createScaledBitmap(fotoBuscada, 256,256, true);
                    File imageFileFolder = new File(getCacheDir(),"Avatar");
                    if( !imageFileFolder.exists() ){
                        imageFileFolder.mkdir();
                    }

                    FileOutputStream out = null;

                    imageFileName = new File(imageFileFolder, "avatar-" + System.currentTimeMillis() + ".jpg");
                    try {
                        out = new FileOutputStream(imageFileName);
                        fotoBuscada.compress(Bitmap.CompressFormat.JPEG, 100, out);
                        out.flush();
                    } catch (IOException e) {
                        Log.e("img", "Failed to convert image to JPEG", e);
                    } finally {
                        try {
                            if (out != null) {
                                out.close();
                            }
                        } catch (IOException e) {
                            Log.e("img", "Failed to close output stream", e);
                        }
                    }
                    img.setImageBitmap(fotoRedimensionada);
                    byte[] fotoEmBytes;
                    ByteArrayOutputStream streamFotoEmBytes = new ByteArrayOutputStream();
                    fotoRedimensionada.compress(Bitmap.CompressFormat.PNG, 70, streamFotoEmBytes);
                    fotoEmBytes = streamFotoEmBytes.toByteArray();
                    fotoEmString = Base64.encodeToString(fotoEmBytes,Base64.DEFAULT);

                }catch (Exception e){
                    e.printStackTrace();
                }
            } else {
                img.setImageResource(R.drawable.img);
                fotoEmString = null;
            }
        }
    }

    public void realizarAlteracao(View view) {
        img = findViewById(R.id.imageMutante);
        nome = findViewById(R.id.nomeMutante);
        habilidade1 = findViewById(R.id.habilidade1Mutante);
        habilidade2 = findViewById(R.id.habilidade2Mutante);
        habilidade3 = findViewById(R.id.habilidade3Mutante);

        if (img.toString().isEmpty() || nome.getText().toString().isEmpty() || (
                habilidade1.getText().toString().isEmpty() &&
                        habilidade2.getText().toString().isEmpty() &&
                            habilidade3.getText().toString().isEmpty())) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(EditarMutanteActivity.this);
            alertDialog.setTitle("Erro !!");
            alertDialog.setMessage("Você deve preencher no mínimo o Nome, uma Habilidade e informar a Foto do Mutante!");
            alertDialog.setNegativeButton("Fechar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            alertDialog.create().show();


        }
        else{
            Intent intent = new Intent(EditarMutanteActivity.this, DetalheMutanteActivity.class);
            Bundle params = new Bundle();
            params.putInt("id", mutant.getId());
            intent.putExtras(params);
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