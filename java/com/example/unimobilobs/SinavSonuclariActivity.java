package com.example.unimobilobs;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unimobilobs.adapter.SinavSonuclariAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SinavSonuclariActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SinavSonuclariAdapter dersAdapter;
    private List<String> dersListesi;
    private static final String TAG = "SinavSonuclariActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sinav_sonuclari);

        Window window = getWindow();
        int appStatusColor = ContextCompat.getColor(this, R.color.appstatusbar);
        getWindow().setStatusBarColor(appStatusColor);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setNavigationBarColor(getResources().getColor(R.color.appbackground));

        recyclerView = findViewById(R.id.recyclerViewSonuclar);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        dersListesi = new ArrayList<>();
        dersAdapter = new SinavSonuclariAdapter(dersListesi);
        recyclerView.setAdapter(dersAdapter);

        SharedPreferences sharedPreferences = this.getSharedPreferences("OgrenciPrefs", Context.MODE_PRIVATE);
        String ogrenciNo = sharedPreferences.getString("ogrenciNo", "");

        if (!ogrenciNo.isEmpty()) {
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("ogrenciler").child(ogrenciNo).child("alinanDersler");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    dersListesi.clear();
                    for (DataSnapshot dersSnapshot : snapshot.getChildren()) {
                        String dersAdi = dersSnapshot.getValue(String.class);
                        Log.d(TAG, "Ders Adı: " + dersAdi); // Verileri loga yaz
                        dersListesi.add(dersAdi);
                    }
                    dersAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.e(TAG, "Veritabanı hatası: " + error.getMessage());
                }
            });
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
