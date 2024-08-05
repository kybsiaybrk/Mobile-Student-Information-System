package com.example.unimobilobs.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unimobilobs.R;

import java.util.List;

public class SinavSonuclariAdapter extends RecyclerView.Adapter<SinavSonuclariAdapter.DersViewHolder> {
    private List<String> dersListesi;

    public SinavSonuclariAdapter(List<String> dersListesi) {
        this.dersListesi = dersListesi;
    }

    @NonNull
    @Override
    public DersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.sinav_sonuclari, parent, false);
        return new DersViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DersViewHolder holder, int position) {
        String ders = dersListesi.get(position);
        holder.textViewDersAdi.setText(ders);
    }

    @Override
    public int getItemCount() {
        return dersListesi.size();
    }

    public static class DersViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewDersAdi;

        public DersViewHolder(View view) {
            super(view);
            textViewDersAdi = view.findViewById(R.id.textViewDers);
        }
    }
}
