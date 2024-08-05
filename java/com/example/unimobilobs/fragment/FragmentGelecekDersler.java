package com.example.unimobilobs.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.unimobilobs.R;
import com.example.unimobilobs.adapter.DersAdapter;
import com.example.unimobilobs.adapter.DonemDersleriAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FragmentGelecekDersler extends Fragment {

    private RecyclerView recyclerView;
    private DonemDersleriAdapter dersAdapter;
    private List<String> dersListesi;
    private static final String TAG = "FragmentGelecekDersler";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gelecek_dersler, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewGelecekDersler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        dersListesi = new ArrayList<>();
        dersAdapter = new DonemDersleriAdapter(dersListesi);
        recyclerView.setAdapter(dersAdapter);


        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("OgrenciPrefs", Context.MODE_PRIVATE);
        String ogrenciNo = sharedPreferences.getString("ogrenciNo", "");


        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("ogrenciler").child(ogrenciNo).child("gelecekDersler");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dersListesi.clear();
                for (DataSnapshot dersSnapshot : snapshot.getChildren()) {
                    String dersAdi = dersSnapshot.getValue(String.class);
                    Log.d(TAG, "Ders Adı: " + dersAdi); // Verileri loga yaz
                    dersListesi.add(dersAdi);
                }
                Log.d(TAG, "Toplam Ders Sayısı: " + dersListesi.size()); // Listenin boyutunu loga yaz
                dersAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "Veritabanı hatası: " + error.getMessage());
            }
        });

        return view;
    }
}
