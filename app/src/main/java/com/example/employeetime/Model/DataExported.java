package com.example.employeetime.Model;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class DataExported {

    private String EMPCODE;
    private String TRANSKIND;
    private String LA;
    private String LO;

    public DataExported() {

    }

    public DataExported(String EMPCODE, String TRANSKIND, String LA, String LO) {
        this.EMPCODE = EMPCODE;
        this.TRANSKIND = TRANSKIND;
        this.LA = LA;
        this.LO = LO;
    }

    public String getEMPCODE() {
        return EMPCODE;
    }

    public void setEMPCODE(String EMPCODE) {
        this.EMPCODE = EMPCODE;
    }

    public String getTRANSKIND() {
        return TRANSKIND;
    }

    public void setTRANSKIND(String TRANSKIND) {
        this.TRANSKIND = TRANSKIND;
    }

    public String getLA() {
        return LA;
    }

    public void setLA(String LA) {
        this.LA = LA;
    }

    public String getLO() {
        return LO;
    }

    public void setLO(String LO) {
        this.LO = LO;
    }

    public JSONObject getJSONObject2() { // for server
        JSONObject obj = new JSONObject();
        try {

            obj.put("EMPCODE", EMPCODE);
            obj.put("TRANSKIND", TRANSKIND);
            obj.put("LA", LA);
            obj.put("LO", LO);


        } catch (JSONException e) {
            Log.e("Tag" , "JSONException");
        }
        return obj;
    }
}
