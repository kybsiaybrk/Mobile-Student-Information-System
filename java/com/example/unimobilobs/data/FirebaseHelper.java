package com.example.unimobilobs.data;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseHelper {
    private DatabaseReference mDatabase;

    public FirebaseHelper() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public void addOgrenci(Ogrenci ogrenci) {
        mDatabase.child("ogrenciler").child(ogrenci.okulNo).setValue(ogrenci);
    }
}

