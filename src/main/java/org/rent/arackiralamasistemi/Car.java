package org.rent.arackiralamasistemi;


public class Car {
    private int aracID;
    private String plaka;
    private String marka;
    private String model;
    private String renk;
    private String yakitTipi;
    private String durum;
    private double gundelikKiraBedeli;

    public Car(int aracID, String plaka, String marka, String model, String renk, String yakitTipi, String durum, double gundelikKiraBedeli) {
        this.aracID = aracID;
        this.plaka = plaka;
        this.marka = marka;
        this.model = model;
        this.renk = renk;
        this.yakitTipi = yakitTipi;
        this.durum = durum;
        this.gundelikKiraBedeli = gundelikKiraBedeli;
    }

    public int getAracID() {
        return aracID;
    }

    public String getPlaka() {
        return plaka;
    }

    public String getMarka() {
        return marka;
    }

    public String getModel() {
        return model;
    }

    public String getRenk() {
        return renk;
    }

    public String getYakitTipi() {
        return yakitTipi;
    }

    public String getDurum() {
        return durum;
    }

    public double getGundelikKiraBedeli() {
        return gundelikKiraBedeli;
    }
}
