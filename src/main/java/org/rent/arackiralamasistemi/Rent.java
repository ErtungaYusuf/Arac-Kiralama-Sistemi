package org.rent.arackiralamasistemi;

public class Rent {
    private int kiraID;
    private int musteriID;
    private int aracID;
    private String baslangicTarihi;
    private String bitisTarihi;
    private double toplamUcret;

    public Rent(int kiraID, int musteriID, int aracID, String baslangicTarihi, String bitisTarihi, double toplamUcret) {
        this.kiraID = kiraID;
        this.musteriID = musteriID;
        this.aracID = aracID;
        this.baslangicTarihi = baslangicTarihi;
        this.bitisTarihi = bitisTarihi;
        this.toplamUcret = toplamUcret;
    }

    public int getKiraID() {
        return kiraID;
    }

    public int getMusteriID() {
        return musteriID;
    }

    public int getAracID() {
        return aracID;
    }

    public String getBaslangicTarihi() {
        return baslangicTarihi;
    }

    public String getBitisTarihi() {
        return bitisTarihi;
    }

    public double getToplamUcret() {
        return toplamUcret;
    }
}
