package com.example.employeetime;

import static com.example.employeetime.CoreActivity.dates;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.employeetime.DataServer.importJson;
import com.example.employeetime.Model.Setting;
import com.example.employeetime.Model.TransactionTable;
import com.example.employeetime.RoomInterface.AppDatabase;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class SingInActivity extends AppCompatActivity {
    TextView LogInButton;
    TextView SingUpButton,setting;
    AppDatabase db;
    importJson importJsonObj;
    ControllClass controllClass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sing_in_activity);
        initialization();

        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {
                // This method will be executed once the timer is over

                controllClass=new ControllClass(SingInActivity.this);
                importJsonObj=new importJson(SingInActivity.this);

                controllClass.getSettingTable();
                String oldDate=controllClass.getDateStatusTable();
                int dateDifference = (int) controllClass.getDateDiff(new SimpleDateFormat("dd/MM/yyyy"), oldDate, dates);

                if(dateDifference>0){
                    controllClass.UpdateTransactionTable();
                    controllClass.deleteStatusTable();
                    Toast.makeText(SingInActivity.this, "def Day", Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(SingInActivity.this, "Same Day", Toast.LENGTH_SHORT).show();

                }
                //importJsonObj.getAction();

                Intent i = new Intent(SingInActivity.this, CoreActivity.class);
                startActivity(i);
                finish();
            }
        }, 2000);
    }

    private void initialization() {
        db=AppDatabase.getInstanceDatabase(SingInActivity.this);
        LogInButton=findViewById(R.id.LogInButton);
        LogInButton.setOnClickListener(clicked);
        SingUpButton=findViewById(R.id.SingUpButton);
        SingUpButton.setOnClickListener(clicked);
        setting=findViewById(R.id.setting);
        setting.setOnClickListener(clicked);

    }

    View.OnClickListener clicked=new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            switch (view.getId()){

                case R.id.LogInButton:
                    Intent intLogIn = new Intent(SingInActivity.this, CoreActivity.class);
                    startActivity(intLogIn);
                    break;
                case R.id.SingUpButton:
                    Intent intSingUp = new Intent(SingInActivity.this, SingOutActivity.class);
                    startActivity(intSingUp);
                    break;
                case R.id.setting:
                    Intent  settingInt=new Intent(SingInActivity.this,SettingActivity.class);
                    startActivity(settingInt);
                    break;

            }

        }
    };


    public List<Setting> getSettingTable(){

        // UserDaoCard userDao = db.itemCard();
        return  db.SettingTable().getAll();
    }


}