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
import android.widget.ImageView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        imgMutante = findViewById(R.id.imageMutante);
        imgMutante.setImageResource(R.drawable.img);
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
       /* AlertDialog.Builder selecionaFoto = new AlertDialog.Builder(CadastroActivity.this);
        selecionaFoto.setTitle("Atenção !!");
        selecionaFoto.setMessage("Utilizar aqui uma mensagem informando se foi possível cadastrar o mutante.");
        selecionaFoto.setNegativeButton("Fechar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent it = new Intent(CadastroActivity.this,DashboardActivity.class);
                startActivity(it);
                finish();
            }
        });
        selecionaFoto.create().show();*/
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), imageFileName);
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("photo", imageFileName.getName(), requestFile);
        RequestBody name = RequestBody.create(MediaType.parse("multipart/form-data"),"Macaco" + System.currentTimeMillis());
        RequestBody abilities_one  = RequestBody.create(MediaType.parse("multipart/form-data"), "odio");
        RequestBody abilities_two = RequestBody.create(MediaType.parse("multipart/form-data"),"");
        RequestBody abilities_tree = RequestBody.create(MediaType.parse("multipart/form-data"),"");
        RequestBody professorId  = RequestBody.create(MediaType.parse("multipart/form-data"),"2");
        Call<String> call =  new RetrofitConfig().getMutantService().
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
                if(response.isSuccessful()){
                    System.out.println("subiu");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }

    public void voltar(View view){
        Intent it = new Intent(CadastroActivity.this,DashboardActivity.class);
        startActivity(it);
        finish();
    }
}