package com.example.trabwsmutantes.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Mutant {
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("photo")
    private String photo;
    @SerializedName("abilits")
    private List<String> abilits;

    public Mutant(int id, String name, String photo) {
        this.id = id;
        this.name = name;
        this.photo = photo;
        this.abilits = abilits;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public List<String> getAbilits() {
        return abilits;
    }

    public void setAbilits(List<String> abilits) {
        this.abilits = abilits;
    }
}
