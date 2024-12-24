package org.rent.arackiralamasistemi;

public class Customer {
    private int musteriID;
    private String tcKimlikNo;
    private String ehliyetNo;
    private String isim;
    private String soyisim;
    private String telefon;

    public Customer(int musteriID, String tcKimlikNo, String ehliyetNo, String isim, String soyisim, String telefon) {
        this.musteriID = musteriID;
        this.tcKimlikNo = tcKimlikNo;
        this.ehliyetNo = ehliyetNo;
        this.isim = isim;
        this.soyisim = soyisim;
        this.telefon = telefon;
    }

    public int getMusteriID() {
        return musteriID;
    }

    public String getTcKimlikNo() {
        return tcKimlikNo;
    }

    public String getEhliyetNo() {
        return ehliyetNo;
    }

    public String getIsim() {
        return isim;
    }

    public String getSoyisim() {
        return soyisim;
    }

    public String getTelefon() {
        return telefon;
    }
}

