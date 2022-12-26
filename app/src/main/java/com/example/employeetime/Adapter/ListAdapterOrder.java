package com.example.employeetime.Adapter;


import static com.example.employeetime.CoreActivity.dates;
import static com.example.employeetime.CoreActivity.isFull;
import static com.example.employeetime.CoreActivity.lastMotion;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.employeetime.ControllClass;
import com.example.employeetime.CoreActivity;
import com.example.employeetime.DataServer.ExportJson;
import com.example.employeetime.Model.DataExported;
import com.example.employeetime.Model.DataStatus;
import com.example.employeetime.Model.Setting;
import com.example.employeetime.Model.TransactionTable;
import com.example.employeetime.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;


public class ListAdapterOrder extends BaseAdapter {
    private Context context;
    List<TransactionTable> itemsList;
    ControllClass controllClass;

    public ListAdapterOrder(Context context, List<TransactionTable> itemsList) {
        this.context = context;
        this.itemsList = itemsList;
        controllClass = new ControllClass(context);
    }

    public ListAdapterOrder() {

    }

    public void setItemsList(List<TransactionTable> itemsList) {
        this.itemsList = itemsList;
    }

    @Override
    public int getCount() {
        return itemsList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private class ViewHolder {
        TextView transactionName;//,transactionNameA;
        LinearLayout transactionLinear;
        CircleImageView circulID;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        final ViewHolder holder = new ViewHolder();
        view = View.inflate(context, R.layout.transaction_row, null);

        holder.transactionName = view.findViewById(R.id.transactionName);
//        holder.transactionNameA=view.findViewById(R.id.transactionNameA);
        holder.transactionLinear = view.findViewById(R.id.transactionLinear);
        holder.circulID = view.findViewById(R.id.circulID);

        if (i == 0) {
            holder.transactionLinear.setBackground(context.getResources().getDrawable(R.drawable.edit_bac_all));
            holder.transactionName.setTextColor(context.getResources().getColor(R.color.blue));
            holder.circulID.setImageDrawable(context.getResources().getDrawable(R.drawable.bac_6));
//            holder.transactionNameA.setTextColor(context.getResources().getColor(R.color.blue));

        }


        holder.transactionLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFull) {
                    if (!controllClass.isShow(itemsList.get(i).getTRANS_KIND())) {

                        if (!itemsList.get(i).getTRANS_KIND().equals("1")) {

                            if(itemsList.get(i).getTRANS_KIND().equals("2")) {

                                saveIn(i);


                            }else {
                                int result=controllClass.isInOutNo(itemsList.get(i).getTRANS_KIND(),itemsList.get(i).getIN_OUT());
                                if(result==1){//save
                                    saveIn(i);
                                }else if(result==2){//not same event
                                   controllClass.sweetMassageAlert("Please Check out event This Not Correct");
                                }else if (result==3){
                                    controllClass.sweetMassageAlert("can Not Click In After In Or Out After Out ");

                                }else {
                                    controllClass.sweetMassageAlert("can Not Save  ");

                                }

                            }

                        } else {
                            controllClass.sweetMassage(context.getResources().getString(R.string.multiClick));
                        }

                    } else {
//                       Toast.makeText(context, "Can Not  Check in MultiTime", Toast.LENGTH_SHORT).show();
                        controllClass.sweetMassage(context.getResources().getString(R.string.multiClick));
                    }

                } else {
                    if (!itemsList.get(i).getTRANS_KIND().equals("1")) {
//                        Toast.makeText(context, "Please Check in first", Toast.LENGTH_SHORT).show();
                        controllClass.sweetMassage(context.getResources().getString(R.string.checkIn));
                    } else {


                        new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("Do you Want to Click to "+itemsList.get(i).getTRANS_NAMEE())
                                .setContentText("")
                                .setCancelButton("cancel", new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        sweetAlertDialog.dismissWithAnimation();
                                    }
                                })
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {

                                        DataStatus status1 = new DataStatus();
                                        status1.setDate(dates + "");
                                        status1.setLastMotion(itemsList.get(i).getTRANS_NAMEE() + "");
                                        status1.setMotionNo(itemsList.get(i).getTRANS_KIND());
//                        controllClass.SaveStatusTable(status1);
////                        Toast.makeText(context, "Save Successful", Toast.LENGTH_SHORT).show();
//                        controllClass.sweetMassageSuccess("Save Successful");
//
//                        try {
//                            CoreActivity coreActivity = (CoreActivity) context;
//                            coreActivity.refreshLayout();
//                        } catch (Exception e) {
//
//                        }

                                        saveInServer(status1);

                                        sweetAlertDialog.dismissWithAnimation();


                                    }
                                })
                                .show();



                    }

                }
            }
        });

        if(context.getResources().getString(R.string.Lang).equals("Eng")){
            holder.transactionName.setText("" + itemsList.get(i).getTRANS_NAMEE());
        }else {
            holder.transactionName.setText("" + itemsList.get(i).getTRANS_NAMEA());
        }
//        holder.transactionName.setText("" + itemsList.get(i).getTRANS_NAMEE());
//        holder.transactionNameA.setText(""+itemsList.get(i).getTRANS_NAMEA());
        return view;
    }

    void saveInServer(DataStatus status) {

        controllClass.getLoc();
        List<Setting> listSetting = controllClass.getSettingTable();
        String empcode = "";
        if (listSetting.size() != 0) {
            empcode = listSetting.get(0).getEmployNo();
        }
        DataExported dataExported = new DataExported();

//        try {
//            dataExported = new DataExported(
//                    empcode,
//                    status.getMotionNo(),
//                    controllClass.locationData.get(0).getLa() + "",
//                    controllClass.locationData.get(0).getLo() + ""
//
//            );
//        } catch (Exception e) {
//
//            Toast.makeText(context, "Exception of location", Toast.LENGTH_SHORT).show();
//
//        }


        try {
            dataExported = new DataExported(
                    empcode,
                    status.getMotionNo(),
                    "555555.0",
                    "25874.2"

            );
        } catch (Exception e) {

            Toast.makeText(context, "Exception of location", Toast.LENGTH_SHORT).show();

        }

        try {
            JSONArray obj2 = new JSONArray();
            obj2.put(dataExported.getJSONObject2());

            JSONObject obj = new JSONObject();
            obj.put("JSN", obj2);
            ExportJson exportJson = new ExportJson(context);
            exportJson.SaveAction(obj, status);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

   public void saveIn(int i){
       new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
               .setTitleText("Do you Want to Click to " + itemsList.get(i).getTRANS_NAMEE())
               .setContentText("")
               .setCancelButton("cancel", new SweetAlertDialog.OnSweetClickListener() {
                   @Override
                   public void onClick(SweetAlertDialog sweetAlertDialog) {
                       sweetAlertDialog.dismissWithAnimation();
                   }
               })
               .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                   @Override
                   public void onClick(SweetAlertDialog sweetAlertDialog) {

                       DataStatus status1 = new DataStatus();
                       status1.setDate(dates + "");
                       status1.setLastMotion(itemsList.get(i).getTRANS_NAMEE() + "");
                       status1.setMotionNo(itemsList.get(i).getTRANS_KIND());

                       saveInServer(status1);
                       sweetAlertDialog.dismissWithAnimation();


                   }
               })
               .show();
    }
}

