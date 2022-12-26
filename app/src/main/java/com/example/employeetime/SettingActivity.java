package com.example.employeetime;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.employeetime.DataServer.importJson;
import com.example.employeetime.Model.Setting;
import com.example.employeetime.RoomInterface.AppDatabase;

import java.util.ArrayList;
import java.util.List;

public class SettingActivity extends AppCompatActivity {
    AppDatabase db;
    EditText ipE, companyNoE, companyNameE, employNoE, employNameE;
    TextView saveButton;
    List<Setting>settingData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_activity);

        initialization();


    }

    private void initialization() {
        db = AppDatabase.getInstanceDatabase(SettingActivity.this);
        settingData=new ArrayList<>();

        ipE = findViewById(R.id.ip);
        saveButton=findViewById(R.id.saveButton);
        companyNoE = findViewById(R.id.coNO);
        companyNameE = findViewById(R.id.coName);
        employNoE = findViewById(R.id.empNo);
        employNameE = findViewById(R.id.empName);
        settingData=getSettingTable();
        fillSetting();
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveData();
            }
        });
    }

    void SaveData(){

        if(!ipE.getText().toString().equals("")){
            if(!companyNoE.getText().toString().equals("")){
                if(!companyNameE.getText().toString().equals("")){
                    if(!employNoE.getText().toString().equals("")){
                        if(!employNameE.getText().toString().equals("")){


                            Setting setting=new Setting();
                            setting.setIP_NO(ipE.getText().toString());
                            setting.setCompanyName(companyNameE.getText().toString());
                            setting.setCompanyNo(companyNoE.getText().toString());
                            setting.setEmployName(employNameE.getText().toString());
                            setting.setEmployNo(employNoE.getText().toString());
                            SaveSettingTable(setting);
                            Toast.makeText(this, "Save Successful", Toast.LENGTH_SHORT).show();
                            importJson imp=new importJson(SettingActivity.this);
                            imp.getAction();
//                            Intent settingIntent=new Intent(SettingActivity.this,CoreActivity.class);
//                            startActivity(settingIntent);
                        }else {
                            employNameE.setError("Required!");
                        }
                    }else {
                        employNoE.setError("Required!");
                    }
                }else {
                    companyNameE.setError("Required!");
                }
            }else {
                companyNoE.setError("Required!");
            }
        }else {
            ipE.setError("Required!");
        }

    }

    @SuppressLint("SetTextI18n")
    void fillSetting(){
        if(settingData.size()!=0){
            ipE.setText(""+settingData.get(0).getIP_NO());
            companyNameE.setText(""+settingData.get(0).getCompanyName());
            companyNoE.setText(""+settingData.get(0).getCompanyNo());
            employNameE.setText(""+settingData.get(0).getEmployName());
            employNoE.setText(""+settingData.get(0).getEmployNo());

        }
    }

    public List<Setting> getSettingTable() {

        // UserDaoCard userDao = db.itemCard();
        return db.SettingTable().getAll();
    }

    public String SaveSettingTable(Setting setting) {

        // UserDaoCard userDao = db.itemCard();
        db.SettingTable().deleteAll();
        db.SettingTable().insertUsers(setting);
        return "Save Successful";
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //finish();
        Intent settingIntent=new Intent(SettingActivity.this,CoreActivity.class);
        startActivity(settingIntent);
    }
}