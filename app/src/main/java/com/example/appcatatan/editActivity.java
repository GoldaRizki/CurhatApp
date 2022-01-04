package com.example.appcatatan;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.appcatatan.apiclient.APIClient;
import com.example.appcatatan.apiclient.CatatanInterface;
import com.example.appcatatan.apiclient.Data;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.PUT;

public class editActivity extends AppCompatActivity {

    Bundle b;
    EditText judul, isi;
    CatatanInterface ci;
    int id;
    String jdl, konten;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        judul = findViewById(R.id.editEditJudul);
        isi = findViewById(R.id.editEditCatatan);

        b = getIntent().getExtras();
        id = b.getInt("id");
        jdl = b.getString("judul");
        konten = b.getString("konten");

        judul.setText(jdl);
        isi.setText(konten);

        ci = APIClient.getClient().create(CatatanInterface.class);


    }

    public void tblUpdate(View v){
        Call<Data> mintaUpdate = ci.updateData(id, judul.getText().toString(), isi.getText().toString());
        mintaUpdate.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                finish();
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                Log.e("ErrorPasUpdate", t.getMessage());
            }
        });
    }

    public void tblKembali(View v){
        finish();
    }
}