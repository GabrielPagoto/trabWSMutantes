package com.example.trabwsmutantes.Controller;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.trabwsmutantes.ApiMutants.RetrofitConfig;
import com.example.trabwsmutantes.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CadastroActivity extends AppCompatActivity implements Serializable {
    int REQUEST_IMAGE_CAPTURE = 1;
    ImageView imgMutante;
    String fotoEmString; //será usado para jogar no banco
    Bitmap fotoRedimensionada;
    File imageFileName;
    TextView nomeMutante;
    TextView habilidade1;
    TextView habilidade2;
    TextView habilidade3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        imgMutante = findViewById(R.id.imageMutante);
        imgMutante.setImageResource(R.drawable.img);

        nomeMutante = findViewById(R.id.nomeMutante);
        habilidade1 = findViewById(R.id.habilidade1Mutante);
        habilidade2 = findViewById(R.id.habilidade2Mutante);
        habilidade3 = findViewById(R.id.habilidade3Mutante);
    }

    public void attFotoMutante(View view){
        AlertDialog.Builder selecionaFoto = new AlertDialog.Builder(CadastroActivity.this);
        selecionaFoto.setTitle("Origem da foto");
        selecionaFoto.setMessage("Por favor, selecione a origem da foto");
        selecionaFoto.setPositiveButton("Câmera", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,1);
            }
        });
        selecionaFoto.setNegativeButton("Galeria", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,2);
            }
        });
        selecionaFoto.create().show();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent dados) {

        super.onActivityResult(requestCode, resultCode, dados);
        if(requestCode == 1){
            if(resultCode == RESULT_OK){
                try{
                    Bitmap fotoRegistrada = (Bitmap) dados.getExtras().get("data");
                    fotoRedimensionada = Bitmap.createScaledBitmap(fotoRegistrada, 256,256, true);
                    File imageFileFolder = new File(getCacheDir(),"Avatar");
                    if( !imageFileFolder.exists() ){
                        imageFileFolder.mkdir();
                    }

                    FileOutputStream out = null;

                    imageFileName = new File(imageFileFolder, "avatar-" + System.currentTimeMillis() + ".jpg");
                    try {
                        out = new FileOutputStream(imageFileName);
                        fotoRegistrada.compress(Bitmap.CompressFormat.JPEG, 100, out);
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
                    imgMutante.setImageBitmap(fotoRedimensionada);
                    byte[] fotoEmBytes;
                    ByteArrayOutputStream streamFotoEmBytes = new ByteArrayOutputStream();
                    fotoRedimensionada.compress(Bitmap.CompressFormat.PNG, 70, streamFotoEmBytes);
                    fotoEmBytes = streamFotoEmBytes.toByteArray();
                    fotoEmString = Base64.encodeToString(fotoEmBytes,Base64.DEFAULT);
                    System.out.println("foto" + fotoEmString);

                }catch (Exception e){
                    e.printStackTrace();
                }
            } else {
                imgMutante.setImageResource(R.drawable.img);
                fotoEmString = null;
            }
        }
        else if(requestCode == 2){
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
                    imgMutante.setImageBitmap(fotoRedimensionada);
                    byte[] fotoEmBytes;
                    ByteArrayOutputStream streamFotoEmBytes = new ByteArrayOutputStream();
                    fotoRedimensionada.compress(Bitmap.CompressFormat.PNG, 70, streamFotoEmBytes);
                    fotoEmBytes = streamFotoEmBytes.toByteArray();
                    fotoEmString = Base64.encodeToString(fotoEmBytes,Base64.DEFAULT);

                }catch (Exception e){
                    e.printStackTrace();
                }
            } else {
                imgMutante.setImageResource(R.drawable.img);
                fotoEmString = null;
            }
        }
    }

    public void realizarCadastro(View view){
        SharedPreferences sharedPref = getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        int idLogado = sharedPref.getInt("id", 0);

        if(nomeMutante.getText().toString().isEmpty() || imageFileName == null ||
                (habilidade1.getText().toString().isEmpty() &&
                    habilidade2.getText().toString().isEmpty() &&
                        habilidade3.getText().toString().isEmpty())){
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(CadastroActivity.this);
            alertDialog.setTitle("Erro !!");
            alertDialog.setMessage("Você deve preencher no mínimo o Nome, uma Habilidade e informar a Foto do Mutante!");
            alertDialog.setNegativeButton("Fechar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            alertDialog.create().show();
        }
        else {
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), imageFileName);
            MultipartBody.Part filePart = MultipartBody.Part.createFormData("photo", imageFileName.getName(), requestFile);
            RequestBody name = RequestBody.create(MediaType.parse("multipart/form-data"), nomeMutante.getText().toString());
            RequestBody abilities_one = RequestBody.create(MediaType.parse("multipart/form-data"), habilidade1.getText().toString());
            RequestBody abilities_two = RequestBody.create(MediaType.parse("multipart/form-data"), habilidade2.getText().toString());
            RequestBody abilities_tree = RequestBody.create(MediaType.parse("multipart/form-data"), habilidade3.getText().toString());
            RequestBody professorId = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(idLogado));
            Call<String> call = new RetrofitConfig().getMutantService().
                    uploadAttachment(
                            filePart,
                            name,
                            abilities_one,
                            abilities_two,
                            abilities_tree,
                            professorId);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.code() == 200) {
                        Intent intentNova = new Intent(CadastroActivity.this, ListarTodosActivity.class);
                        startActivity(intentNova);
                        finish();
                    } else if (response.code() == 404) {
                        AlertDialog.Builder alertDialogErro = new AlertDialog.Builder(CadastroActivity.this);
                        alertDialogErro.setTitle("Erro !!");
                        alertDialogErro.setMessage("Já existe um mutante com o nome de: "+ nomeMutante.getText().toString());
                        alertDialogErro.setNegativeButton("Fechar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                        alertDialogErro.create().show();
                    } else{
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(CadastroActivity.this);
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
                public void onFailure(Call<String> call, Throwable t) {
                    System.out.println(t.getMessage());
                }
            });
            Intent it = new Intent(CadastroActivity.this, DashboardActivity.class);
            startActivity(it);
            finish();
        }
    }

    public void voltar(View view){
        Intent it = new Intent(CadastroActivity.this,DashboardActivity.class);
        startActivity(it);
        finish();
    }
}