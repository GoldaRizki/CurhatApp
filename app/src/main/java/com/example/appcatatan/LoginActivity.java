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
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    SharedPreferences pref;
    EditText username, pass;
    String usr, password, cekStr;
    CatatanInterface cinter;
    ArrayList<Data> listSaya;
    Boolean b;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        pref = getSharedPreferences(getString(R.string.SharedPref), Context.MODE_PRIVATE);

        username = findViewById(R.id.editLoginUsername);
        pass = findViewById(R.id.editLoginPassword);
        cinter = APIClient.getClient().create(CatatanInterface.class);

        cekStr = pref.getString(getString(R.string.SharedPref), "");

        if (cekStr.isEmpty() == false){
            Intent in = new Intent(getBaseContext(), MainActivity.class);
            in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(in);
        }

    }

    public void keReg(View v){
        Intent it = new Intent(this, RegisterActivity.class);
        startActivity(it);
    }

    public void Login(View v){
        // ngechek sek seko database..
        usr = username.getText().toString().toLowerCase();
        password = pass.getText().toString();
        checkLogin(usr,password);


    }

    private void checkLogin(String username, String password){
        b = false;
        Call<List<Data>> getdata = cinter.getData("reg", -1);

        getdata.enqueue(new Callback<List<Data>>() {
            @Override
            public void onResponse(Call<List<Data>> call, Response<List<Data>> response) {
                listSaya = (ArrayList<Data>) response.body();
                String a = "";
                for(int i = 0; i<listSaya.size(); i++){

                    // khusus string nggowone equals() gawe comparison e
                    if (listSaya.get(i).getUsername().equals(username) && listSaya.get(i).getJudul().equals(password)){
                        b = true;
                        a = listSaya.get(i).getUsername();
                    }
                }

                if (b){
                    // nek bener login e

                    pref.edit().putString(getString(R.string.SharedPref), a).commit();


                    // saya kasih flag. ning mbalik e jjal delok meneh nek ora iso langsung start activity
                    Intent in = new Intent(getBaseContext(), MainActivity.class);
                    in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(in);
                }else{
                    // kon baleni
                    Toast t = Toast.makeText(getBaseContext(), "Pastikan Username dan Password anda sudah benar!", Toast.LENGTH_SHORT);
                    t.show();

                }
            }

            @Override
            public void onFailure(Call<List<Data>> call, Throwable t) {
                Log.e("Error_Datane", t.getMessage());
            }

        });


    }

}