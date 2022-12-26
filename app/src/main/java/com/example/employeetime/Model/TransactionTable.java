package com.example.employeetime.Model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "TRANSACTION_TABLE")
public class TransactionTable {


    //{"SERIALNO":"1","TRANSKIND":"1","TRANSNAMEA":"دخول مقر العمل\/بداية الدوام"
    // ,"TRANSNAMEE":"CLOCK WORK IN","TLNO":"43574","INOUT":"1","SUMTOWRK":"0","Show":""}
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "SERIAL_NO")
    @SerializedName("SERIALNO")
    private String SERIAL_NO;
    @ColumnInfo(name = "TRANS_KIND")
    @SerializedName("TRANSKIND")
    private String TRANS_KIND;
    @SerializedName("TRANSNAMEA")
    @ColumnInfo(name = "TRANS_NAMEA")
    private String TRANS_NAMEA;
    @SerializedName("TRANSNAMEE")
    @ColumnInfo(name = "TRANS_NAMEE")
    private String TRANS_NAMEE;
    @SerializedName("TLNO")
    @ColumnInfo(name = "TL_NO")
    private String TL_NO;
    @SerializedName("INOUT")
    @ColumnInfo(name = "IN_OUT")
    private String IN_OUT;
    @SerializedName("SUMTOWRK")
    @ColumnInfo(name = "SUM_TO_WRK")
    private String SUM_TO_WRK;
    @SerializedName("Show")
    @ColumnInfo(name = "Show")
    private String Show;

    @ColumnInfo(name = "ShowInDay" , defaultValue = "0")
    private String ShowInDay;
    @NonNull
    public String getSERIAL_NO() {
        return SERIAL_NO;
    }

    public void setSERIAL_NO(@NonNull String SERIAL_NO) {
        this.SERIAL_NO = SERIAL_NO;
    }

    public String getTRANS_KIND() {
        return TRANS_KIND;
    }

    public void setTRANS_KIND(String TRANS_KIND) {
        this.TRANS_KIND = TRANS_KIND;
    }

    public String getTRANS_NAMEA() {
        return TRANS_NAMEA;
    }

    public void setTRANS_NAMEA(String TRANS_NAMEA) {
        this.TRANS_NAMEA = TRANS_NAMEA;
    }

    public String getTRANS_NAMEE() {
        return TRANS_NAMEE;
    }

    public void setTRANS_NAMEE(String TRANS_NAMEE) {
        this.TRANS_NAMEE = TRANS_NAMEE;
    }

    public String getTL_NO() {
        return TL_NO;
    }

    public void setTL_NO(String TL_NO) {
        this.TL_NO = TL_NO;
    }

    public String getIN_OUT() {
        return IN_OUT;
    }

    public void setIN_OUT(String IN_OUT) {
        this.IN_OUT = IN_OUT;
    }

    public String getSUM_TO_WRK() {
        return SUM_TO_WRK;
    }

    public void setSUM_TO_WRK(String SUM_TO_WRK) {
        this.SUM_TO_WRK = SUM_TO_WRK;
    }

    public String getShow() {
        return Show;
    }

    public void setShow(String show) {
        Show = show;
    }

    public String getShowInDay() {
        return ShowInDay;
    }

    public void setShowInDay(String showInDay) {
        ShowInDay = showInDay;
    }
}
