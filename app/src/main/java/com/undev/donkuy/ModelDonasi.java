package com.undev.donkuy;

import android.os.Parcel;
import android.os.Parcelable;

public class ModelDonasi implements Parcelable {

    String judul,subjudul,terkumpul,targetterkumpul,donasi,sisahari;
    int img;

    public ModelDonasi() {
    }

    protected ModelDonasi(Parcel in) {
        judul = in.readString();
        subjudul = in.readString();
        terkumpul = in.readString();
        targetterkumpul = in.readString();
        donasi = in.readString();
        sisahari = in.readString();
        img = in.readInt();
    }

    public static final Creator<ModelDonasi> CREATOR = new Creator<ModelDonasi>() {
        @Override
        public ModelDonasi createFromParcel(Parcel in) {
            return new ModelDonasi(in);
        }

        @Override
        public ModelDonasi[] newArray(int size) {
            return new ModelDonasi[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getSubjudul() {
        return subjudul;
    }

    public void setSubjudul(String subjudul) {
        this.subjudul = subjudul;
    }

    public String getTerkumpul() {
        return terkumpul;
    }

    public void setTerkumpul(String terkumpul) {
        this.terkumpul = terkumpul;
    }

    public String getTargetterkumpul() {
        return targetterkumpul;
    }

    public void setTargetterkumpul(String targetterkumpul) {
        this.targetterkumpul = targetterkumpul;
    }

    public String getDonasi() {
        return donasi;
    }

    public void setDonasi(String donasi) {
        this.donasi = donasi;
    }

    public String getSisahari() {
        return sisahari;
    }

    public void setSisahari(String sisahari) {
        this.sisahari = sisahari;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(judul);
        dest.writeString(subjudul);
        dest.writeString(terkumpul);
        dest.writeString(targetterkumpul);
        dest.writeString(donasi);
        dest.writeString(sisahari);
        dest.writeInt(img);
    }
}
