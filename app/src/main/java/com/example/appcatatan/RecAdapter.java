package com.example.appcatatan;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appcatatan.apiclient.Data;

import java.util.ArrayList;
import java.util.List;

public class RecAdapter extends RecyclerView.Adapter<RecAdapter.ViewHolder> {

    ArrayList<Data> ListData;
    public RecAdapter(ArrayList<Data> list) {
        this.ListData = list;
    }
    Context konteks;

    @NonNull
    @Override
    public RecAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        konteks = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(konteks);
        ViewHolder holder = new ViewHolder(inflater.inflate(R.layout.recycle_layout, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecAdapter.ViewHolder holder, int position) {

            Data d = ListData.get(position);

            holder.user.setText(d.getUsername());
            holder.judul.setText(d.getJudul());
            holder.pesan.setText(d.getIsi());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent it = new Intent(konteks, pilihanActivity.class);
                    it.putExtra("id", d.getId());
                    konteks.startActivity(it);
                }
            });

    }

    @Override
    public int getItemCount() {
        return ListData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView user, pesan, judul;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            judul = itemView.findViewById(R.id.textJudul);
            user = itemView.findViewById(R.id.textPengguna);
            pesan = itemView.findViewById(R.id.textCatatan);

        }
    }
}
