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

public class sayaActivity extends AppCompatActivity {

    CatatanInterface catatanInterface;
    SharedPreferences pref;
    ArrayList<Data> iniList, filtered;
    RecyclerView rec;
    String check;
    TextView nama;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saya);

        pref = getSharedPreferences(getString(R.string.SharedPref), Context.MODE_PRIVATE);
        catatanInterface = APIClient.getClient().create(CatatanInterface.class);
        rec = findViewById(R.id.recycleSaya);
        check = pref.getString(getString(R.string.SharedPref), "");
        filtered = new ArrayList<Data>();

        nama = findViewById(R.id.textSayaUser);


    }

    @Override
    protected void onResume() {
        super.onResume();

        nama.setText("Saya :\n"+check);

        Call<List<Data>> getData = catatanInterface.getData("tulis", -1);
        getData.enqueue(new Callback<List<Data>>() {
            @Override
            public void onResponse(Call<List<Data>> call, Response<List<Data>> response) {

                filtered = new ArrayList<Data>();
                iniList = (ArrayList<Data>) response.body();

                for (int i = 0; i < iniList.size(); i++) {
                    if (iniList.get(i).getUsername().equals(check)) {
                        filtered.add(iniList.get(i));
                    }
                }

                rec.setAdapter(new RecAdapter(filtered));
                rec.setLayoutManager(new LinearLayoutManager(getBaseContext()));

            }

            @Override
            public void onFailure(Call<List<Data>> call, Throwable t) {
                Log.d("Makan", "iki mung jajal tok 2");
                Log.e("Error_Datane", t.getMessage());

            }
        });
    }

    public void tblTambah(View view){
        Intent it = new Intent(this, ActivityBuat.class);
        startActivity(it);
    }

    public void tblSetting(View v){
        Intent i = new Intent(this, personalisasiActivity.class);
        startActivity(i);
    }
}