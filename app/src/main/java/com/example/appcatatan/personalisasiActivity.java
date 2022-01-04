package com.example.appcatatan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.ConditionVariable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.appcatatan.apiclient.APIClient;
import com.example.appcatatan.apiclient.CatatanInterface;
import com.example.appcatatan.apiclient.Data;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class personalisasiActivity extends AppCompatActivity {

    SharedPreferences pref;
    TextView tnama;
    CatatanInterface ci;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personalisasi);

        pref = getSharedPreferences(getString(R.string.SharedPref), Context.MODE_PRIVATE);
        username = pref.getString(getString(R.string.SharedPref), "");
        tnama = findViewById(R.id.textPersonalisasiNama);
        tnama.setText(username);
        ci = APIClient.getClient().create(CatatanInterface.class);

    }

    public void hapusAkun(View view){
        Call<Data> hapusAkun = ci.hapusData("reg", -1, username);
        hapusAkun.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                pref.edit().clear().commit();
                Intent it = new Intent(getBaseContext(), LoginActivity.class);
                it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(it);

            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                Log.d("Delete Gagal", t.getMessage());
            }
        });
    }

    public void logOut(View view){
        pref.edit().clear().commit();
        Intent in = new Intent(this, LoginActivity.class);
        in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(in);
    }

    public void kembali(View view){
        finish();
    }

}