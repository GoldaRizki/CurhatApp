package com.example.appcatatan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appcatatan.apiclient.APIClient;
import com.example.appcatatan.apiclient.CatatanInterface;
import com.example.appcatatan.apiclient.Data;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    EditText user, password, konformasiPass;
    CatatanInterface ci;
    ArrayList<Data> l;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        user = findViewById(R.id.editRegisterNama);
        password = findViewById(R.id.editRegisterPassword);
        konformasiPass = findViewById(R.id.editRegisterPasswordKonfirmasi);

        ci = APIClient.getClient().create(CatatanInterface.class);


    }

    public void tbDaftarkan(View view){
        Call<List<Data>> ambilData = ci.getData("reg", -1);
        ambilData.enqueue(new Callback<List<Data>>() {
            @Override
            public void onResponse(Call<List<Data>> call, Response<List<Data>> response) {

                l = new ArrayList<Data>();
                Log.d("Jajal delok", response.body().toString());
                l = (ArrayList<Data>) response.body();
                cekDaftar(l);

            }

            @Override
            public void onFailure(Call<List<Data>> call, Throwable t) {
                Log.e("Error_Datane", t.getMessage());
            }
        });


    }

    private void cekDaftar(ArrayList<Data> d){
        String usr = user.getText().toString().toLowerCase();
         boolean a = false;
         for(int i = 0; i<d.size(); i++){
             if (d.get(i).getUsername().equals(usr)){
                 a = true;
                 break;
             }
        }

         if (a){
             Toast t = Toast.makeText(this, "Nama username sudah dipakai, sialahkan cari yang lain!", Toast.LENGTH_SHORT);
             t.show();
         }else{
             // dicek password e podo pora

             String pass = password.getText().toString();
             String passk = konformasiPass.getText().toString();

             if(pass.equals(passk) && !pass.isEmpty()){
                 // lanjut langkah selanjutnya
                 // berarti langsung di isikke ng db ne

                 Call<Data> kirimData = ci.postData("reg", usr, pass, "");
                 kirimData.enqueue(new Callback<Data>() {
                     @Override
                     public void onResponse(Call<Data> call, Response<Data> response) {
                         Log.d("Data berhasil", response.toString());
                         user.setText("");
                         password.setText("");
                         konformasiPass.setText("");
                         Toast t = Toast.makeText(getBaseContext(), "Berhasil di daftarkan!", Toast.LENGTH_SHORT);
                         t.show();
                         finish();
                     }

                     @Override
                     public void onFailure(Call<Data> call, Throwable t) {
                         Log.d("DataGagal", t.getMessage());
                     }
                 });

             }else{
                 Toast t = Toast.makeText(this, "Pastikan password sudah benar!", Toast.LENGTH_SHORT);
                 t.show();
             }

         }

    }

    public void keLogin(View v){
        Intent it = new Intent(this, LoginActivity.class);
        startActivity(it);
    }
}