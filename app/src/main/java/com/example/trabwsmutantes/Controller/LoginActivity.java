package com.example.trabwsmutantes.Controller;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.trabwsmutantes.ApiMutants.RetrofitConfig;
import com.example.trabwsmutantes.Model.User;
import com.example.trabwsmutantes.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    EditText login;
    EditText senha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }

    public void acessarAplicativo(View view){
        login = findViewById(R.id.login);
        senha = findViewById(R.id.senha);

        if (!login.getText().toString().isEmpty() && !senha.getText().toString().isEmpty()){
            Intent it = new Intent(LoginActivity.this,DashboardActivity.class);
            Call<User> call = new RetrofitConfig().getLoginService().getUser(login.getText().toString(),senha.getText().toString());
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if(response.code() == 204)
                    {
                            AlertDialog.Builder alertDialog = new AlertDialog.Builder(LoginActivity.this);
                            alertDialog.setTitle("Atenção !!");
                            alertDialog.setMessage("Senha ou email incorretos");
                            alertDialog.setNegativeButton("Fechar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });
                            alertDialog .create().show();
                    }
                    else if(response.code() == 200){
                        User usuario = response.body();
                        SharedPreferences sharedPref = getSharedPreferences(
                                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString("email", usuario.getEmail());
                        editor.putInt("id", usuario.getId());
                        editor.commit();
                        startActivity(it);
                        finish();
                    }
                    else
                    {
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(LoginActivity.this);
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
                public void onFailure(Call<User> call, Throwable t) {
                    Log.e("erro", t.getMessage());
                }
            });
        }
        else {
            AlertDialog.Builder selecionaFoto = new AlertDialog.Builder(LoginActivity.this);
            selecionaFoto.setTitle("Atenção !!");
            selecionaFoto.setMessage("Todos os dados devem ser informados.");
            selecionaFoto.setNegativeButton("Fechar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            selecionaFoto.create().show();
        }


    }
}