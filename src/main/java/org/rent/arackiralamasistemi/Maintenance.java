package org.rent.arackiralamasistemi;

public class Maintenance {
    private int bakimID;
    private int aracID;
    private String bakimTarihi;
    private double maliyet;
    private String aciklama;

    public Maintenance(int bakimID, int aracID, String bakimTarihi, double maliyet, String aciklama) {
        this.bakimID = bakimID;
        this.aracID = aracID;
        this.bakimTarihi = bakimTarihi;
        this.maliyet = maliyet;
        this.aciklama = aciklama;
    }

    public int getBakimID() {
        return bakimID;
    }

    public int getAracID() {
        return aracID;
    }

    public String getBakimTarihi() {
        return bakimTarihi;
    }

    public double getMaliyet() {
        return maliyet;
    }

    public String getAciklama() {
        return aciklama;
    }
}

