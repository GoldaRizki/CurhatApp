package com.example.appcatatan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.appcatatan.apiclient.APIClient;
import com.example.appcatatan.apiclient.CatatanInterface;
import com.example.appcatatan.apiclient.Data;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class pilihanActivity extends AppCompatActivity {

    SharedPreferences pref;
    CatatanInterface ci;
    Bundle bandel;
    TextView nama, judul, isi;
    int kons1;
    String usr;
    LinearLayout li;
    ArrayList<Data> l;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilihan);

        pref = getSharedPreferences(getString(R.string.SharedPref), Context.MODE_PRIVATE);
        ci = APIClient.getClient().create(CatatanInterface.class);
        bandel = getIntent().getExtras();

        nama = findViewById(R.id.textSayaUser);
        judul = findViewById(R.id.textPilihanJudul);
        isi = findViewById(R.id.textPilihanCatatan);
        li = findViewById(R.id.linearLayoutPilihan);

        kons1 = bandel.getInt("id");
        usr = pref.getString(getString(R.string.SharedPref), "");




    }

    @Override
    protected void onResume() {
        super.onResume();

        Call<List<Data>> ambilData = ci.getData("tulis", kons1);
        ambilData.enqueue(new Callback<List<Data>>() {
            @Override
            public void onResponse(Call<List<Data>> call, Response<List<Data>> response) {

                l = (ArrayList<Data>) response.body();

                nama.setText(l.get(0).getUsername());
                judul.setText(l.get(0).getJudul());
                isi.setText(l.get(0).getIsi());



                if(usr.equals(l.get(0).getUsername()) == false){
                    li.setVisibility(View.GONE); // dihilangkan nek jebul dudu wekke user
                }

            }

            @Override
            public void onFailure(Call<List<Data>> call, Throwable t) {
                Log.d("Error_data", t.getMessage());
            }
        });
    }

    public void tblHapus(View v){
        Call<Data> perintahHapus = ci.hapusData("tulis", kons1, "");
        perintahHapus.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                Log.d("delete_curhat", response.toString());
                finish();
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                Log.d("delete_cuthat", t.getMessage());
            }
        });
    }

    public void tblEdit(View v){
        Intent it = new Intent(getBaseContext(), editActivity.class);
        it.putExtra("id", l.get(0).getId());
        it.putExtra("judul", l.get(0).getJudul());
        it.putExtra("konten", l.get(0).getIsi());
        startActivity(it);
    }

}