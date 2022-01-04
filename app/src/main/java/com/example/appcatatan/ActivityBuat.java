package com.example.appcatatan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appcatatan.apiclient.APIClient;
import com.example.appcatatan.apiclient.CatatanInterface;
import com.example.appcatatan.apiclient.Data;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityBuat extends AppCompatActivity {

    EditText judul, catatan;
    CatatanInterface ci;
    SharedPreferences pref;
    String jdl, isi, user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buat);

        judul = findViewById(R.id.editEditJudul);
        catatan = findViewById(R.id.editEditCatatan);
        pref = getSharedPreferences(getString(R.string.SharedPref), Context.MODE_PRIVATE);
        ci = APIClient.getClient().create(CatatanInterface.class);

    }

    public void tbInputkan(View v){
        jdl = judul.getText().toString();
        isi = catatan.getText().toString();
        user = pref.getString(getString(R.string.SharedPref), "");

        Log.d("Lihat_inputan", jdl);
        Log.d("Lihat_inputan", isi);
        Log.d("Lihat_inputan", user);



        Call<Data> kirimData = ci.postData("tulis", user, jdl, isi);
        kirimData.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                Log.d("Data berhasil", response.toString());

                Toast t = Toast.makeText(getBaseContext(), "Berhasil ditambahkan", Toast.LENGTH_SHORT);
                t.show();

                Intent inten = new Intent(getBaseContext(), MainActivity.class);
                startActivity(inten);
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                Log.d("DataGagal", t.getMessage());
            }
        });


    }

    public void tbKembali(View v){
        Intent p = new Intent(this, MainActivity.class);
        startActivity(p);

    }
}