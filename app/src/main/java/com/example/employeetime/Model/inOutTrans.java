package com.example.employeetime.Model;

import androidx.room.ColumnInfo;

public class inOutTrans {
    @ColumnInfo(name = "k")
    private String inOut;
    @ColumnInfo(name = "a")
    private String tranceKind;

    public inOutTrans(String inOut, String tranceKind) {
        this.inOut = inOut;
        this.tranceKind = tranceKind;
    }
//    public String getInOut() {
//        return inOut;
//    }
    public String getInOut() {
        return inOut;
    }

    public void setInOut(String inOut) {
        this.inOut = inOut;
    }

    public String getTranceKind() {
        return tranceKind;
    }

    public void setTranceKind(String tranceKind) {
        this.tranceKind = tranceKind;
    }
}
