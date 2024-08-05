package com.example.unimobilobs;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.unimobilobs.fragment.BottomSheetFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Window window = getWindow();
        int appStatusColor = ContextCompat.getColor(this, R.color.appstatusbar);
        getWindow().setStatusBarColor(appStatusColor);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setNavigationBarColor(getResources().getColor(R.color.appbackground));

        TextView txtGpa = findViewById(R.id.txtAgno);
        TextView txtSinif = findViewById(R.id.txtSinif);
        TextView txtTarih = findViewById(R.id.txtTarih);
        TextView txtAd = findViewById(R.id.txtİsim);
        TextView txtFak = findViewById(R.id.txtFakulteIsim);
        TextView txtBolum = findViewById(R.id.txtBolum);


        BottomSheetFragment bottomSheetFragment = new BottomSheetFragment();


        SharedPreferences sharedPreferences = this.getSharedPreferences("OgrenciPrefs", Context.MODE_PRIVATE);
        String ogrenciNo = sharedPreferences.getString("ogrenciNo", "");

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("ogrenciler").child(ogrenciNo);


        setTextFromDatabase(databaseReference.child("fullName"), txtAd);
        setTextFromDatabase(databaseReference.child("faculty"), txtFak);
        setTextFromDatabase(databaseReference.child("department"), txtBolum);
        setTextFromDatabase(databaseReference.child("gradeLevel"), txtSinif);
        setTextFromDatabase(databaseReference.child("gpa"), txtGpa);
        setTextFromDatabase(databaseReference.child("enrollmentDate"), txtTarih);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button btnTakvim = findViewById(R.id.btntakvim);
        Button btnSonuc = findViewById(R.id.btnSonuclar);
        Button btndersler = findViewById(R.id.btnDersler);
        Button btnDanisman = findViewById(R.id.btnDanisman);
        Button btnAyarlar = findViewById(R.id.btnAyarlar);
        Button btnSecenekler = findViewById(R.id.btnSecenekler);



        btnSecenekler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                startActivity(intent);
            }
        });

        btnAyarlar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
            }
        });

        btnTakvim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TakvimActivity.class);
                startActivity(intent);
            }
        });

        btnSonuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SinavSonuclariActivity.class);
                startActivity(intent);
            }
        });


        btnDanisman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DanismanActivity.class);
                startActivity(intent);
            }
        });

        btndersler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DerslerActivity.class);
                startActivity(intent);
            }
        });


    }
    private void setTextFromDatabase (DatabaseReference databaseReference, TextView textView){
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String value = snapshot.getValue(String.class);
                textView.setText(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Hata durumunda ne yapılacağını buraya yazabilirsiniz
            }
        });


    }
}