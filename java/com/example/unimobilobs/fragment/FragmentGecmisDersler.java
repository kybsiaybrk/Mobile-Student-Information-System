package com.example.unimobilobs.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unimobilobs.R;
import com.example.unimobilobs.adapter.DersAdapter;
import com.example.unimobilobs.data.Ders;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class FragmentGecmisDersler extends Fragment {

    private RecyclerView recyclerView;
    private DersAdapter dersAdapter;
    private List<Ders> dersListesi;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gecmis_dersler, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewGecmisDersler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        dersListesi = new ArrayList<>();
        dersAdapter = new DersAdapter(dersListesi);
        recyclerView.setAdapter(dersAdapter);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("OgrenciPrefs", Context.MODE_PRIVATE);
        String ogrenciNo = sharedPreferences.getString("ogrenciNo", "");


        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("ogrenciler").child(ogrenciNo).child("dersNotlari");


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dersListesi.clear();
                for (DataSnapshot dersSnapshot : snapshot.getChildren()) {
                    String dersAdi = dersSnapshot.getKey();
                    String harfNotu = dersSnapshot.getValue(String.class);
                    dersListesi.add(new Ders(dersAdi, harfNotu));
                }
                dersAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Hata durumu
            }
        });

        return view;
    }
}
