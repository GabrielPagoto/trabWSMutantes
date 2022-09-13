package com.example.trabwsmutantes.Model;

import android.app.Activity;

import java.io.Serializable;
import java.util.List;

public class Mutante extends Activity implements Serializable {
    private int img;
    private String nome;
    private String habilidade1;
    private String habilidade2;
    private String habilidade3;

    public Mutante(int img, String nome, String habilidade1) {
        this.img = img;
        this.nome = nome;
        this.habilidade1 = habilidade1;
    }

    public Mutante(int img, String nome, String habilidade1, String habilidade2) {
        this.img = img;
        this.nome = nome;
        this.habilidade1 = habilidade1;
        this.habilidade2 = habilidade2;
    }

    public Mutante(int img, String nome, String habilidade1, String habilidade2, String habilidade3) {
        this.img = img;
        this.nome = nome;
        this.habilidade1 = habilidade1;
        this.habilidade2 = habilidade2;
        this.habilidade3 = habilidade3;
    }

    public Mutante() {

    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getHabilidade1() {
        return habilidade1;
    }

    public void setHabilidade1(String habilidade1) {
        this.habilidade1 = habilidade1;
    }

    public String getHabilidade2() {
        return habilidade2;
    }

    public void setHabilidade2(String habilidade2) {
        this.habilidade2 = habilidade2;
    }

    public String getHabilidade3() {
        return habilidade3;
    }

    public void setHabilidade3(String habilidade3) {
        this.habilidade3 = habilidade3;
    }
}
