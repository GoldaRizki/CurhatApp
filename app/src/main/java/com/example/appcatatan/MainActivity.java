package com.example.appcatatan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.appcatatan.apiclient.APIClient;
import com.example.appcatatan.apiclient.CatatanInterface;
import com.example.appcatatan.apiclient.Data;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {


    String judul;
    TextView nama;
    CatatanInterface catatanInterface;
    RecyclerView rec;
    ArrayList<Data> iniList;
    SharedPreferences pref;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pref = getSharedPreferences(getString(R.string.SharedPref), Context.MODE_PRIVATE);
        judul = pref.getString(getString(R.string.SharedPref), "");


        nama = findViewById(R.id.textSayaUser);
        nama.setText(judul);
        nama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent in = new Intent(getBaseContext(), sayaActivity.class);
                startActivity(in);

            }
        });

        catatanInterface = APIClient.getClient().create(CatatanInterface.class);
        rec = findViewById(R.id.recycleSaya);
        //tampilkan();
    }

    @Override
    protected void onResume() {
        super.onResume();
        tampilkan();

    }

    private void tampilkan(){

        Call<List<Data>> getData = catatanInterface.getData("tulis", -1);
        getData.enqueue(new Callback<List<Data>>() {
            @Override
            public void onResponse(Call<List<Data>> call, Response<List<Data>> response) {

                iniList = (ArrayList<Data>) response.body();

                rec.setAdapter(new RecAdapter(iniList));
                rec.setLayoutManager(new LinearLayoutManager(getBaseContext()));

            }

            @Override
            public void onFailure(Call<List<Data>> call, Throwable t) {
                Log.d("Makan", "iki mung jajal tok 2");
                Log.e("Error_Datane", t.getMessage());
            }
        });


    }

    public void tbTambah(View v){
        Intent it = new Intent(this, ActivityBuat.class);
        startActivity(it);
    }

    public void tbLogOut(View v){
        pref.edit().clear().commit();
        Intent in = new Intent(this, LoginActivity.class);
        in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(in);
    }
}