package org.rent.arackiralamasistemi;

import java.sql.Date;

public class Insurance {
    private int sigortaID;
    private int aracID;
    private String policeNo;
    private String sigortaTuru;
    private Date baslangicTarihi;
    private Date bitisTarihi;
    private double maliyet;

    // Constructor
    public Insurance(int sigortaID, int aracID, String policeNo, String sigortaTuru, Date baslangicTarihi, Date bitisTarihi, double maliyet) {
        this.sigortaID = sigortaID;
        this.aracID = aracID;
        this.policeNo = policeNo;
        this.sigortaTuru = sigortaTuru;
        this.baslangicTarihi = baslangicTarihi;
        this.bitisTarihi = bitisTarihi;
        this.maliyet = maliyet;
    }

    // Getters
    public int getSigortaID() {
        return sigortaID;
    }

    public int getAracID() {
        return aracID;
    }

    public String getPoliceNo() {
        return policeNo;
    }

    public String getSigortaTuru() {
        return sigortaTuru;
    }

    public Date getBaslangicTarihi() {
        return baslangicTarihi;
    }

    public Date getBitisTarihi() {
        return bitisTarihi;
    }

    public double getMaliyet() {
        return maliyet;
    }
}
