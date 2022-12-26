package com.example.employeetime;

import static com.example.employeetime.CoreActivity.dates;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.employeetime.Model.DataStatus;
import com.example.employeetime.Model.LocationData;
import com.example.employeetime.Model.Setting;
import com.example.employeetime.Model.TransactionTable;
import com.example.employeetime.Model.inOutTrans;
import com.example.employeetime.RoomInterface.AppDatabase;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ControllClass {
    public AppDatabase db;
    Context context;
    double v1,v2;
   public List<LocationData> locationData;

    public ControllClass(Context context) {
        this.context=context;
        db = AppDatabase.getInstanceDatabase(context);
        locationData=new ArrayList<>();
    }


    public List<Setting> getSettingTable() {

        // UserDaoCard userDao = db.itemCard();
        return db.SettingTable().getAll();
    }

    public String saveTransactionTable(List<TransactionTable>list) {

        // UserDaoCard userDao = db.itemCard();
        if(list.size()!=0) {
//            db.daoTransactionTable().deleteAll();
            db.daoTransactionTable().insertAll(list);
        }else {
            Toast.makeText(context, "NoData", Toast.LENGTH_SHORT).show();
        }
        return "Save Successful";
    }

    public List<TransactionTable> getTransaction() {

        // UserDaoCard userDao = db.itemCard();
        return db.daoTransactionTable().getAll();
    }

    public String SaveStatusTable(DataStatus setting) {

        // UserDaoCard userDao = db.itemCard();
        db.dataStatusTable().deleteAll();
        db.dataStatusTable().insertUsers(setting);
        db.daoTransactionTable().UpdateShow(setting.getMotionNo(),"1");
        return "Save Successful";
    }

    public String getDateStatusTable() {

        // UserDaoCard userDao = db.itemCard();

        List<DataStatus>list=db.dataStatusTable().getAll();
        String date="";
        if (list.size()!=0){
            date=list.get(0).getDate();
        }else{
            date="00/00/0000";
        }
        return date;
    }

    public String deleteStatusTable() {

        db.dataStatusTable().deleteAll();
        return "delete Successful";
    }

    public String UpdateTransactionTable() {

        db.daoTransactionTable().UpdateAllShow("0");
        return "Save Successful";
    }

    String getDate(){

        Calendar calendar = Calendar.getInstance();
//date format is:  "Date-Month-Year Hour:Minutes am/pm"
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); //Date and time
        String currentDate = sdf.format(calendar.getTime());
        SimpleDateFormat sdf_ = new SimpleDateFormat("EEEE");
        Date date = new Date();
        String dayName = sdf_.format(date);
        dates=currentDate;
        return dayName.toUpperCase()+"    "+currentDate;
    }



    public void getLoc() {
       LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        String locationProvider = LocationManager.NETWORK_PROVIDER;
        // I suppressed the missing-permission warning because this wouldn't be executed in my
        // case without location services being enabled
        //  @SuppressLint("MissingPermission")
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

           // sweetMassage("Please Open Location Permission");

            if (ContextCompat.checkSelfPermission(context,
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context,
                        Manifest.permission.ACCESS_FINE_LOCATION)) {
                    ActivityCompat.requestPermissions((Activity) context,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                } else {
                    ActivityCompat.requestPermissions((Activity) context,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                }
            }

            return;
        }
        android.location.Location lastKnownLocation = locationManager.getLastKnownLocation(locationProvider);

        try {
            v1 = lastKnownLocation.getLatitude();
            v2 = lastKnownLocation.getLongitude();
        } catch (Exception e) {
        }

//        LatLng latLng = new LatLng(v1, v2);
        Log.e("LocationLanLag", "  loo");
        Log.e("LocationLanLag", "  n  " + v1 + "   " + v2);
        LocationData locationDatas=new LocationData(v1,v2);
        locationData.add(locationDatas);

    }

    public boolean isShow(String transeKind) {

       List<TransactionTable>list= db.daoTransactionTable().GetIfShow(transeKind);
       Log.e("ren",""+list.size());
        if(list.size()!=0) {
            Log.e("ren",""+list.get(0).getShow());

            if(list.get(0).getShowInDay().equals("1")){
              return true;
          }else {
              return false;
          }
        }else {
            return false;
        }
    }


    public int isInOutNo(String tranceKind,String inOut) {

        int resultTrans=0;//if not
        List<inOutTrans>list= db.daoTransactionTable().getInOutTra();
     try {


         if(list.size()!=0) {

             if ((inOut.equals("0") && list.get(0).getInOut().equals("1"))||(inOut.equals("1") && list.get(0).getInOut().equals("0"))){
                 int newTrans = Integer.parseInt(list.get(0).getTranceKind()) + 1;
                 int TransE = Integer.parseInt(tranceKind) ;


                     if (!list.get(0).getTranceKind().equals("1")) {
                         if (!list.get(0).getInOut().equals("1")){
                         if (newTrans == TransE) {
                             return 1;//trance is in out and same Event
                         } else {
                             return 2; //trance is in out but not same Event
                         }

                     }else {
                             return 1;//trance is in out and same Event becuse is checkin
                         }
                     } else {
                         return 1;//trance is in out and same Event becuse is checkin
                     }


             }else {
                 return 3;//trance is not in out
             }
         }
     }catch (Exception e){

     }
return 0;//trance is not in out and not same Event
    }


    public void sweetMassage(String titleText){
        new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                .setTitleText(titleText)
                .setContentText("")
//                            .setCancelButton("cancel", new SweetAlertDialog.OnSweetClickListener() {
//                                @Override
//                                public void onClick(SweetAlertDialog sweetAlertDialog) {
//                                    sweetAlertDialog.dismissWithAnimation();
//                                }
//                            })
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {

                        sweetAlertDialog.dismissWithAnimation();


                    }
                })
                .show();
    }

    public void sweetMassageAlert(String titleText){
        new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                .setTitleText(titleText)
                .setContentText("")
//                            .setCancelButton("cancel", new SweetAlertDialog.OnSweetClickListener() {
//                                @Override
//                                public void onClick(SweetAlertDialog sweetAlertDialog) {
//                                    sweetAlertDialog.dismissWithAnimation();
//                                }
//                            })
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {

                        sweetAlertDialog.dismissWithAnimation();


                    }
                })
                .show();
    }
    public void sweetMassageSuccess(String titleText){
        new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText(titleText)
                .setContentText("")
//                            .setCancelButton("cancel", new SweetAlertDialog.OnSweetClickListener() {
//                                @Override
//                                public void onClick(SweetAlertDialog sweetAlertDialog) {
//                                    sweetAlertDialog.dismissWithAnimation();
//                                }
//                            })
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {

                        sweetAlertDialog.dismissWithAnimation();


                    }
                })
                .show();
    }

    public static long getDateDiff(SimpleDateFormat format, String oldDate, String newDate) {
        try {
            return TimeUnit.DAYS.convert(format.parse(newDate).getTime() - format.parse(oldDate).getTime(), TimeUnit.DAYS);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }





}
