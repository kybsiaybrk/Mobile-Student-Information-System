package com.example.unimobilobs;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.unimobilobs.fragment.FragmentDersler;
import com.example.unimobilobs.fragment.FragmentGecmisDersler;
import com.example.unimobilobs.fragment.FragmentGelecekDersler;

public class DerslerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dersler);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Window window = getWindow();
        int appStatusColor = ContextCompat.getColor(this, R.color.appstatusbar);
        getWindow().setStatusBarColor(appStatusColor);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setNavigationBarColor(getResources().getColor(R.color.appbackground));

        Button btnBudonemDersleri = findViewById(R.id.budonemdersler);
        Button btnGelecekDersler = findViewById(R.id.gelecekdersler);
        Button btnGecmisDersler = findViewById(R.id.gecmisdersler);

        btnGecmisDersler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentcagir(new FragmentGecmisDersler());
            }
        });


         btnBudonemDersleri.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 fragmentcagir(new FragmentDersler());
            }
         });

       btnGelecekDersler.setOnClickListener(new View.OnClickListener() {
             @Override
            public void onClick(View v) {
                fragmentcagir(new FragmentGelecekDersler());
             }
         });
    }

    public void fragmentcagir(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container_view, fragment);
        transaction.commit();
    }
}
