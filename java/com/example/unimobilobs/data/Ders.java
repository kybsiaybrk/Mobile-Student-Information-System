package com.example.unimobilobs.data;


public class Ders {
    private String dersAdi;
    private String harfNotu;

    public Ders(String dersAdi, String harfNotu) {
        this.dersAdi = dersAdi;
        this.harfNotu = harfNotu;
    }

    public String getDersAdi() {
        return dersAdi;
    }

    public String getHarfNotu() {
        return harfNotu;
    }
}

