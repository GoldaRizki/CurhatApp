package com.example.appcatatan.apiclient;


import com.google.gson.annotations.SerializedName;

public class Data {
    @SerializedName("id")
    private int id;
    @SerializedName("aksi")
    private String aksi;
    @SerializedName("username")
    private String username;
    @SerializedName("judul")
    private String judul;
    @SerializedName("isi")
    private String isi;

    public Data(int id, String aksi, String username, String judul, String isi) {
        this.id = id;
        this.aksi = aksi;
        this.username = username;
        this.judul = judul;
        this.isi = isi;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAksi() {
        return aksi;
    }

    public void setAksi(String aksi) {
        this.aksi = aksi;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getIsi() {
        return isi;
    }

    public void setIsi(String isi) {
        this.isi = isi;
    }
}
