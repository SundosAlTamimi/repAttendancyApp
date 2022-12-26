package com.example.employeetime.Model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "SETTING_TABLE")

public class Setting {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "IP_NO")
    private String IP_NO;
    @ColumnInfo(name = "COMPANY_NAME")
    private String CompanyName;
    @ColumnInfo(name = "COMPANY_NO")
    private String CompanyNo;
    @ColumnInfo(name = "EMPLOY_NAME")
    private String EmployName;
    @ColumnInfo(name = "EMPLOY_NO")
    private String EmployNo;

    public Setting() {
    }

    public Setting(String IP_NO, String companyName, String companyNo, String employName, String employNo) {
        this.IP_NO = IP_NO;
        CompanyName = companyName;
        CompanyNo = companyNo;
        EmployName = employName;
        EmployNo = employNo;
    }

    public String getIP_NO() {
        return IP_NO;
    }

    public void setIP_NO(String IP_NO) {
        this.IP_NO = IP_NO;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

    public String getCompanyNo() {
        return CompanyNo;
    }

    public void setCompanyNo(String companyNo) {
        CompanyNo = companyNo;
    }

    public String getEmployName() {
        return EmployName;
    }

    public void setEmployName(String employName) {
        EmployName = employName;
    }

    public String getEmployNo() {
        return EmployNo;
    }

    public void setEmployNo(String employNo) {
        EmployNo = employNo;
    }
}
