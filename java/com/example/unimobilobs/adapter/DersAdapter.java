package com.example.unimobilobs.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.unimobilobs.R;
import com.example.unimobilobs.data.Ders;
import java.util.List;

public class DersAdapter extends RecyclerView.Adapter<DersAdapter.DersViewHolder> {
    private List<Ders> dersListesi;

    public DersAdapter(List<Ders> dersListesi) {
        this.dersListesi = dersListesi;
    }

    @NonNull
    @Override
    public DersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ders, parent, false);
        return new DersViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DersViewHolder holder, int position) {
        Ders ders = dersListesi.get(position);
        holder.textViewDersAdi.setText(ders.getDersAdi());
        holder.textViewHarfNotu.setText(ders.getHarfNotu());

        if (ders.getHarfNotu().equals("FF") || ders.getHarfNotu().equals("FD")) {
            holder.imageView1.setImageResource(R.drawable.derskaldibtn);
        } else {
            holder.imageView1.setImageResource(R.drawable.dersigectibuton);
        }

    }

    @Override
    public int getItemCount() {
        return dersListesi.size();
    }

    public static class DersViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewDersAdi;
        public TextView textViewHarfNotu;
        public ImageView imageView1;


        public DersViewHolder(View view) {
            super(view);
            textViewDersAdi = view.findViewById(R.id.textViewDers);
            textViewHarfNotu = view.findViewById(R.id.textViewGpa);
            imageView1 = view.findViewById(R.id.dersgectikaldirenk);

        }
    }
}
