package com.example.unimobilobs.data;

import java.util.List;
import java.util.Map;

public class Ogrenci {
    public String okulNo;
    public String sifre;
    public List<String> alinanDersler;
    public List<String> verilenDersler;
    public List<String> gelecekDersler;
    public String danismanHoca;
    public Map<String, String> dersNotlari;
    public String gradeLevel;   // Yeni eklenen
    public String gpa;          // Yeni eklenen
    public String fullName;     // Yeni eklenen
    public String faculty;      // Yeni eklenen
    public String department;   // Yeni eklenen
    public String enrollmentDate; // Yeni eklenen

    public Ogrenci() {
        // Default constructor required for calls to DataSnapshot.getValue(Ogrenci.class)
    }

    public Ogrenci(String okulNo, String sifre, List<String> alinanDersler, List<String> verilenDersler, List<String> gelecekDersler, String danismanHoca, Map<String, String> dersNotlari, String gradeLevel, String gpa, String fullName, String faculty, String department, String enrollmentDate) {
        this.okulNo = okulNo;
        this.sifre = sifre;
        this.alinanDersler = alinanDersler;
        this.verilenDersler = verilenDersler;
        this.gelecekDersler = gelecekDersler;
        this.danismanHoca = danismanHoca;
        this.dersNotlari = dersNotlari;
        this.gradeLevel = gradeLevel;
        this.gpa = gpa;
        this.fullName = fullName;
        this.faculty = faculty;
        this.department = department;
        this.enrollmentDate = enrollmentDate;
    }
}
