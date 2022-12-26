package com.example.employeetime.DataServer;


import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;

import com.example.employeetime.ControllClass;
import com.example.employeetime.CoreActivity;
import com.example.employeetime.Model.DataStatus;
import com.example.employeetime.Model.Setting;
import com.example.employeetime.Model.TransactionTable;
import com.example.employeetime.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ExportJson {

    private Context context;
    private ProgressDialog progressDialog;
    private ProgressDialog progressDialogSave;
    private JSONObject obj;
    String itemCode;
    String JsonResponseSave;
    ControllClass controllClass;

    String ip;
    DataStatus dataStatus;

    List<TransactionTable> transactionTableList;
    SweetAlertDialog pd;

    public ExportJson(Context context) {//, JSONObject obj
        this.context = context;
        transactionTableList = new ArrayList<>();
        controllClass = new ControllClass(context);
        dataStatus=new DataStatus();
        final List<Setting> mainSettings = controllClass.getSettingTable();
pd=new SweetAlertDialog(context,SweetAlertDialog.PROGRESS_TYPE);
        if (mainSettings.size() != 0) {
            ip = mainSettings.get(0).getIP_NO();
        }

    }

    public void SaveAction(JSONObject jsonObject,DataStatus dataStatus){
        obj=jsonObject;
        this.dataStatus=dataStatus;
        new SaveTransaction().execute();
    }


    private class SaveTransaction extends AsyncTask<String, String, String> {
        private String JsonResponse = null;
        private HttpURLConnection urlConnection = null;
        private BufferedReader reader = null;

        @Override
        protected void onPreExecute() {

//            progressDialog = new ProgressDialog(context,R.style.MyTheme);
//            progressDialog.setCancelable(false);
//            progressDialog.setMessage("Loading...");
//            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//            progressDialog.setProgress(0);
//            progressDialog.show();
//           ProgressDialog pd = ProgressDialog.show(context, "title", "loading", true);
            pd.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
            pd.setTitleText(context.getString(R.string.SaveInServer));
            pd.setCancelable(false);
            pd.show();

            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... params) {

            try {

                String link = "http://" + ip + "/SaveTempFingerTrans";

//                String data = "CONO=" + URLEncoder.encode("ATT", "UTF-8");

                String data = "JSONSTR=" + URLEncoder.encode(obj.toString(), "UTF-8")  + "&" +
                        "CONO="+URLEncoder.encode("ATT", "UTF-8");

                URL url = new URL(link);
                Log.e("urlStringCard = ", "" + url.toString() + "   " + data);
                Log.e("exportEmploy4", ""+obj.toString());

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                httpURLConnection.setRequestMethod("POST");


                DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
                wr.writeBytes(data);
                wr.flush();
                wr.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                StringBuffer stringBuffer = new StringBuffer();

                while ((JsonResponse = bufferedReader.readLine()) != null) {
                    stringBuffer.append(JsonResponse + "\n");
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                // Log.e("tag", "ItemOCode -->" + stringBuffer.toString());
                Log.e("exportEmploy5", ""+stringBuffer.toString());

                return stringBuffer.toString();

            } catch (IOException e) {

                e.printStackTrace();
                Log.e("exportEmploy6", ""+e.toString());

            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("tag", "Error closing stream", e);
                    }
                }
                Log.e("exportEmploy66", "Error");

            }
            return null;
        }

        @Override
        protected void onPostExecute(String JsonResponse) {
            super.onPostExecute(JsonResponse);

           // JsonResponse = "successful";
            if (JsonResponse != null && JsonResponse.contains("Successfully")) {
                Log.e("tag_ItemOCode", "****Success");
                Log.e("exportEmploy7", "****saveSuccess.");


                JsonResponseSave = JsonResponse;
                controllClass.SaveStatusTable(dataStatus);
                // Toast.makeText(context, "Save Successful", Toast.LENGTH_SHORT).show();
                controllClass.sweetMassageSuccess(context.getResources().getString(R.string.save));
                Log.e("exportEmploy8", "save");

                try {
                    CoreActivity coreActivity = (CoreActivity) context;
                    coreActivity.refreshLayout();
                } catch (Exception e) {

                }

                if (pd!=null){
                    pd.dismiss();

                }

            } else if (JsonResponse != null && JsonResponse.contains("No Data Found.")) {
                Log.e("exportEmploy9", "****No Data Found.");
                if (pd!=null){
                    pd.dismiss();

                }

                // new SyncItemSwitch().execute();
            } else {
                Log.e("tag_itemCard", "****Failed to export data");
                Log.e("exportEmploy10", "****Failed to export data");
//                Toast.makeText(context, "Failed to Get data", Toast.LENGTH_SHORT).show();
                if(pd!=null) {
                    pd.dismiss();
                    new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText(context.getResources().getString(R.string.ops))
                            .setContentText(context.getResources().getString(R.string.fildToSaveTrans))
                            .show();
                }


            }

        }
    }


}








