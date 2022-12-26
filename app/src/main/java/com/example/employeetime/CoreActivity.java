package com.example.employeetime;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.employeetime.Adapter.ListAdapterOrder;
import com.example.employeetime.Model.DataStatus;
import com.example.employeetime.Model.Setting;
import com.example.employeetime.Model.TransactionTable;
import com.example.employeetime.RoomInterface.AppDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class CoreActivity extends AppCompatActivity {
TextView date,setting,lastStatus;
    AppDatabase db;
    List<DataStatus> dataStatuses;
    LinearLayout checkInLinear,checkOutLinear,breakInLinear,breakOutLinear;
    GridView itemListView;
    List<TransactionTable> transactionTableList;
    ControllClass controllClass;
    ListAdapterOrder listAdapterOrder;
    EditText search;
    public static boolean isFull=false;
    List<TransactionTable>tempList=new ArrayList<>();
    List<Setting>settingData=new ArrayList<>();
    public static boolean openSetting=false;

    public static String lastMotion="no";

    public static String dates="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_core);
        initialization();

    }

    @SuppressLint("SetTextI18n")
    private void initialization() {
        db = AppDatabase.getInstanceDatabase(CoreActivity.this);
        date=findViewById(R.id.date);
        itemListView=findViewById(R.id.itemListView);
        lastStatus=findViewById(R.id.lastStatus);
        setting=findViewById(R.id.setting);
        checkInLinear=findViewById(R.id.checkInLinear);
        checkOutLinear=findViewById(R.id.checkOutLinear);
        breakInLinear=findViewById(R.id.breakInLinear);
        breakOutLinear=findViewById(R.id.breakOutLinear);
        search=findViewById(R.id.search);
        controllClass=new ControllClass(CoreActivity.this);
        date.setText(""+controllClass.getDate());
       // dates=controllClass.getDate();
        transactionTableList=new ArrayList<>();
        transactionTableList=controllClass.getTransaction();

        listAdapterOrder = new ListAdapterOrder(CoreActivity.this, transactionTableList);
        itemListView.setAdapter(listAdapterOrder);

        dataStatuses=new ArrayList<>();
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
                Intent  settingInt=new Intent(CoreActivity.this,SettingActivity.class);
                startActivity(settingInt);

            }
        });

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(!search.getText().toString().equals("")){
                    tempList.clear();
                    for(int q=0;q<transactionTableList.size();q++){

                        if(transactionTableList.get(q).getTRANS_NAMEE().toUpperCase().contains(search.getText().toString().toUpperCase())||transactionTableList.get(q).getTRANS_NAMEA().toUpperCase().contains(search.getText().toString().toUpperCase())) {
                            tempList.add(transactionTableList.get(q));
                        }
                    }

                    listAdapterOrder = new ListAdapterOrder(CoreActivity.this, tempList);
                    itemListView.setAdapter(listAdapterOrder);

                }else {
                    listAdapterOrder = new ListAdapterOrder(CoreActivity.this, transactionTableList);
                    itemListView.setAdapter(listAdapterOrder);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        refreshLayout();

        controllClass.getLoc();

        checkInLinear.setOnClickListener(cliked);
        checkOutLinear.setOnClickListener(cliked);
        breakInLinear.setOnClickListener(cliked);
        breakOutLinear.setOnClickListener(cliked);


        settingData=controllClass.getSettingTable();

        if(settingData.size()==0){

            SweetAlertDialog sweetAlertDialog=  new SweetAlertDialog(CoreActivity.this, SweetAlertDialog.WARNING_TYPE);
            sweetAlertDialog  .setTitleText(getResources().getString(R.string.mainSetting) + "!");
            sweetAlertDialog  .setContentText(getResources().getString(R.string.nomainSetting));
            sweetAlertDialog   .setConfirmText(getResources().getString(R.string.cancel));
            sweetAlertDialog   .showCancelButton(false);
            sweetAlertDialog     .setCancelClickListener(null);
            sweetAlertDialog.setCancelable(false);
            sweetAlertDialog     .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {

                            Intent settingIntent=new Intent(CoreActivity.this,SettingActivity.class);
                            startActivity(settingIntent);
                            finish();

                            sweetAlertDialog.dismissWithAnimation();
                        }
                    });
            sweetAlertDialog.show();

        }else {
            Toast.makeText(this, "Setting Found", Toast.LENGTH_SHORT).show();
        }

    }

    public  void refreshLayout(){
        dataStatuses=getStatusTable();
        if(dataStatuses.size()!=0){
            isFull=true;
            lastMotion=dataStatuses.get(0).getMotionNo();
            lastStatus.setText(""+dataStatuses.get(0).getLastMotion());


        }else{
            lastStatus.setText(CoreActivity.this.getResources().getString(R.string.noevent));
            isFull=false;
            lastMotion="-1";
        }


    }


    View.OnClickListener cliked=new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            switch (view.getId()){

                case R.id.breakInLinear:
                    if(dataStatuses.size()!=0) {
                        DataStatus status1 = new DataStatus();
                        status1.setDate(date + "");
                        status1.setLastMotion("break In");
                        status1.setMotionNo("3");
                        SaveStatusTable(status1);
                    }else{
                        Toast.makeText(CoreActivity.this, CoreActivity.this.getResources().getString(R.string.checkIn), Toast.LENGTH_SHORT).show();
                    }

                    break;
                case R.id.breakOutLinear:
                    if(dataStatuses.size()!=0) {
                        DataStatus status2 = new DataStatus();
                        status2.setDate(date + "");
                        status2.setLastMotion("break out");
                        status2.setMotionNo("4");
                        SaveStatusTable(status2);
                    }else{
                        Toast.makeText(CoreActivity.this, CoreActivity.this.getResources().getString(R.string.checkIn), Toast.LENGTH_SHORT).show();
            }
                    break;
                case R.id.checkInLinear:
                    if(dataStatuses.size()==0) {
                        DataStatus status3 = new DataStatus();
                        status3.setDate(date + "");
                        status3.setLastMotion("Check In");
                        status3.setMotionNo("1");
                        SaveStatusTable(status3);
                    }else{
                        Toast.makeText(CoreActivity.this, CoreActivity.this.getResources().getString(R.string.multiClick), Toast.LENGTH_SHORT).show();

                    }
                    break;
                case R.id.checkOutLinear:
                    if(dataStatuses.size()!=0) {
                        DataStatus status = new DataStatus();
                        status.setDate(date + "");
                        status.setLastMotion("Check out");
                        status.setMotionNo("2");
                        SaveStatusTable(status);
                    }else{
                        Toast.makeText(CoreActivity.this, CoreActivity.this.getResources().getString(R.string.checkIn), Toast.LENGTH_SHORT).show();

                    }
                    break;


            }

        }
    };

    public List<DataStatus> getStatusTable() {

        // UserDaoCard userDao = db.itemCard();
        return db.dataStatusTable().getAll();
    }

    public String SaveStatusTable(DataStatus setting) {

        // UserDaoCard userDao = db.itemCard();
        db.dataStatusTable().deleteAll();
        db.dataStatusTable().insertUsers(setting);
        return "Save Successful";
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(CoreActivity.this,
                            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

}