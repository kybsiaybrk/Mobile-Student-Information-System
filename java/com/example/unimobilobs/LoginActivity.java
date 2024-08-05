package com.example.unimobilobs;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;
import android.content.SharedPreferences;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.unimobilobs.data.FirebaseHelper;
import com.example.unimobilobs.data.Ogrenci;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        Window window = getWindow();
        int appStatusColor = ContextCompat.getColor(this, R.color.appstatusbar);
        getWindow().setStatusBarColor(appStatusColor);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setNavigationBarColor(getResources().getColor(R.color.appbackground));

        TextInputEditText editTextOkulNo = findViewById(R.id.editTextId);
        TextInputEditText editTextSifre = findViewById(R.id.editTextSifre);
        Button buttonLogin = findViewById(R.id.btnLogin);

        FirebaseHelper firebaseHelper = new FirebaseHelper();

        List<String> verilenDersler = Arrays.asList("Matematik", "Fizik", "Bilgisayar Müh. Giriş");
        List<String> alinanDersler = Arrays.asList("Programlama", "Veri Yapıları", "Bilgisayar Ağları","Veri Tabanı", "Otomata", "Yazılım Mühendisliği");
        List<String> gelecekDersler = Arrays.asList("Veri Tabanı", "Otomata", "Yazılım Poje Yönetimi");
        Map<String, String> dersNotlari = new HashMap<>();
        dersNotlari.put("Matematik", "AA");
        dersNotlari.put("Fizik", "BB");
        dersNotlari.put("Bilgisayar Müh Giriş", "CC");
        dersNotlari.put("Fizik Lab", "FF");
        dersNotlari.put("İngilizce", "FF");
        dersNotlari.put("Kimya Lab", "DD");


        Ogrenci ogrenci = new Ogrenci("123", "123", alinanDersler, verilenDersler, gelecekDersler, "Dr. Danisman", dersNotlari, "2", "2.2", "Ayberk Köybasi", "Teknoloji Fakultesi", "Bilgisayar Mühendisligi", "2019-09-01");

        firebaseHelper.addOgrenci(ogrenci);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String okulNo = editTextOkulNo.getText().toString().trim();
                String sifre = editTextSifre.getText().toString().trim();

                if (okulNo.isEmpty() || sifre.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Lütfen tüm alanları doldurun", Toast.LENGTH_SHORT).show();
                    return;
                }

                login(okulNo, sifre);
            }
        });
    }

    private void login(final String okulNo, final String sifre) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("ogrenciler").child(okulNo);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Ogrenci ogrenci = snapshot.getValue(Ogrenci.class);
                    if (ogrenci != null && ogrenci.sifre.equals(sifre)) {
                        // Giriş başarılı
                        Toast.makeText(LoginActivity.this, "Giriş başarılı", Toast.LENGTH_SHORT).show();

                        // Öğrenci numarasını SharedPreferences ile sakla
                        SharedPreferences sharedPreferences = getSharedPreferences("OgrenciPrefs", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("ogrenciNo", okulNo);
                        editor.apply();

                        // MainActivity'e geçiş yap
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        // Şifre yanlış
                        Toast.makeText(LoginActivity.this, "Şifre yanlış", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Öğrenci bulunamadı
                    Toast.makeText(LoginActivity.this, "Öğrenci bulunamadı", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "Veritabanı hatası: " + error.getMessage());
            }
        });
    }
}
