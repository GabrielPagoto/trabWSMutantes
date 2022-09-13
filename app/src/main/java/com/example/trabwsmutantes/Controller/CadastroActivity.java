package com.example.trabwsmutantes.Controller;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;

import com.example.trabwsmutantes.R;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;

public class CadastroActivity extends AppCompatActivity implements Serializable {
    int REQUEST_IMAGE_CAPTURE = 1;
    ImageView imgMutante;
    String fotoEmString; //será usado para jogar no banco

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
                    Bitmap fotoRedimensionada = Bitmap.createScaledBitmap(fotoRegistrada, 256,256, true);
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
        else if(requestCode == 2){
            if(resultCode == RESULT_OK){
                try{
                    Uri imageUri = dados.getData();

                    Bitmap fotoBuscada = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                    Bitmap fotoRedimensionada = Bitmap.createScaledBitmap(fotoBuscada, 256,256, true);
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
        AlertDialog.Builder selecionaFoto = new AlertDialog.Builder(CadastroActivity.this);
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
        selecionaFoto.create().show();
    }

    public void voltar(View view){
        Intent it = new Intent(CadastroActivity.this,DashboardActivity.class);
        startActivity(it);
        finish();
    }
}