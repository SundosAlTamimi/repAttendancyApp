package com.example.employeetime.Model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "DATA_STATUS_TABLE")
public class DataStatus {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "EVENT_NO")
    private String motionNo;
    @ColumnInfo(name = "DATE")
    private String date;
    @ColumnInfo(name = "LAST_EVENT")
    private String lastMotion;


    public DataStatus() {
    }

    public DataStatus(String lastMotion, String date, String motionNo) {
        this.lastMotion = lastMotion;
        this.date = date;
        this.motionNo = motionNo;
    }

    public String getLastMotion() {
        return lastMotion;
    }

    public void setLastMotion(String lastMotion) {
        this.lastMotion = lastMotion;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMotionNo() {
        return motionNo;
    }

    public void setMotionNo(String motionNo) {
        this.motionNo = motionNo;
    }



}
