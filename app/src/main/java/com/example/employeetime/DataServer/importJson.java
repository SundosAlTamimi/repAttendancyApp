package com.example.employeetime.DataServer;


import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.employeetime.ControllClass;
import com.example.employeetime.Model.Setting;
import com.example.employeetime.Model.TransactionTable;
import com.example.employeetime.SettingActivity;
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

public class importJson {

    private Context context;
    private ProgressDialog progressDialog;
    private ProgressDialog progressDialogSave;
    private JSONObject obj;
    String itemCode;
    String JsonResponseSave;
    ControllClass controllClass;

    String ip="",CoNo="";

    List<TransactionTable> transactionTableList;


    public importJson(Context context) {//, JSONObject obj
        this.context = context;
        transactionTableList = new ArrayList<>();
        controllClass = new ControllClass(context);
        final List<Setting> mainSettings = controllClass.getSettingTable();

        if (mainSettings.size() != 0) {
            ip = mainSettings.get(0).getIP_NO();
            CoNo=mainSettings.get(0).getCompanyNo();
        }

    }

    public void getAction(){
        new GetTransaction().execute();
    }


    private class GetTransaction extends AsyncTask<String, String, String> {
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
//            pd = ProgressDialog.show(context, "title", "loading", true);
//            pd.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
//            pd.setTitleText(context.getResources().getString("Import Data"));


            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... params) {

            try {

                String link = "http://" + ip + "/Get_TA_Transk?".trim();

                String data = "CONO=" + URLEncoder.encode(CoNo, "UTF-8");

                URL url = new URL(link+data);
                Log.e("importEmploy1 = ", "" + url.toString());

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                httpURLConnection.setRequestMethod("GET");

                Log.e("importEmploy2 = ", "in" );
//
//                DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
//                wr.writeBytes(data);
//                wr.flush();
//                wr.close();

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
                try {
                    Log.e("importEmploy3 = ", "" + stringBuffer.toString());
                }catch (Exception e){
                    Log.e("importEmploy4 = ", "Error String");

                }
                return stringBuffer.toString();

            } catch (IOException e) {
                e.printStackTrace();
                Log.e("importEmploy5 = ", "" + e.toString());

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
                Log.e("importEmploy6 = ", "Error" );
            }
            return null;
        }

        @Override
        protected void onPostExecute(String JsonResponse) {
            super.onPostExecute(JsonResponse);

          // JsonResponse = "[{\"SERIALNO\":\"1\",\"TRANSKIND\":\"1\",\"TRANSNAMEA\":\"دخول مقر العمل\\/بداية الدوام\",\"TRANSNAMEE\":\"CLOCK WORK IN\",\"TLNO\":\"43574\",\"INOUT\":\"1\",\"SUMTOWRK\":\"0\",\"Show\":\"\"},{\"SERIALNO\":\"2\",\"TRANSKIND\":\"2\",\"TRANSNAMEA\":\"خروج مقر عمل\\/نهاية دوام\",\"TRANSNAMEE\":\"CLOCK WORK OUT\",\"TLNO\":\"37645\",\"INOUT\":\"0\",\"SUMTOWRK\":\"0\",\"Show\":\"\"},{\"SERIALNO\":\"3\",\"TRANSKIND\":\"3\",\"TRANSNAMEA\":\"خروج إستراحة غداء\",\"TRANSNAMEE\":\"CLOCK LUNCH OUT\",\"TLNO\":\"1\",\"INOUT\":\"0\",\"SUMTOWRK\":\"0\",\"Show\":\"\"},{\"SERIALNO\":\"4\",\"TRANSKIND\":\"4\",\"TRANSNAMEA\":\"دخول من إستراحة الغداء\",\"TRANSNAMEE\":\"CLOCK LUNCH IN\",\"TLNO\":\"1\",\"INOUT\":\"1\",\"SUMTOWRK\":\"0\",\"Show\":\"\"},{\"SERIALNO\":\"5\",\"TRANSKIND\":\"5\",\"TRANSNAMEA\":\"خروج حالة طارئة\",\"TRANSNAMEE\":\"CLOCK UNEXCEPECTED OUT\",\"TLNO\":\"1\",\"INOUT\":\"0\",\"SUMTOWRK\":\"0\",\"Show\":\"\"},{\"SERIALNO\":\"6\",\"TRANSKIND\":\"6\",\"TRANSNAMEA\":\"دخول من حالة طارئة\",\"TRANSNAMEE\":\"CLOCK UNEXCEPECTED IN\",\"TLNO\":\"1\",\"INOUT\":\"1\",\"SUMTOWRK\":\"0\",\"Show\":\"\"},{\"SERIALNO\":\"7\",\"TRANSKIND\":\"7\",\"TRANSNAMEA\":\"خروج مغادرة شخصية\",\"TRANSNAMEE\":\"CLOCK PERSONAL OUT\",\"TLNO\":\"1\",\"INOUT\":\"0\",\"SUMTOWRK\":\"0\",\"Show\":\"\"},{\"SERIALNO\":\"8\",\"TRANSKIND\":\"8\",\"TRANSNAMEA\":\"دخول من مغادرة شخصية\",\"TRANSNAMEE\":\"CLOCK PERSONAL IN\",\"TLNO\":\"4\",\"INOUT\":\"1\",\"SUMTOWRK\":\"0\",\"Show\":\"\"},{\"SERIALNO\":\"9\",\"TRANSKIND\":\"9\",\"TRANSNAMEA\":\"خروج أثناء العمل\",\"TRANSNAMEE\":\"CLOCK WHILE WORK OUT\",\"TLNO\":\"1\",\"INOUT\":\"0\",\"SUMTOWRK\":\"0\",\"Show\":\"\"},{\"SERIALNO\":\"10\",\"TRANSKIND\":\"10\",\"TRANSNAMEA\":\"دخول أثناء العمل\",\"TRANSNAMEE\":\"CLOCK WHILE WORK IN\",\"TLNO\":\"1\",\"INOUT\":\"1\",\"SUMTOWRK\":\"0\",\"Show\":\"\"},{\"SERIALNO\":\"11\",\"TRANSKIND\":\"11\",\"TRANSNAMEA\":\"خروج إلى أي مكان معين\",\"TRANSNAMEE\":\"CLOCK SPECIAL PLACE OUT\",\"TLNO\":\"1\",\"INOUT\":\"0\",\"SUMTOWRK\":\"0\",\"Show\":\"\"},{\"SERIALNO\":\"12\",\"TRANSKIND\":\"12\",\"TRANSNAMEA\":\"دخول من أي مكان معين\",\"TRANSNAMEE\":\"CLOCK SPECIAL PLACE IN\",\"TLNO\":\"1\",\"INOUT\":\"1\",\"SUMTOWRK\":\"0\",\"Show\":\"\"},{\"SERIALNO\":\"13\",\"TRANSKIND\":\"13\",\"TRANSNAMEA\":\"خروج لإجازة رسمية\",\"TRANSNAMEE\":\"CLOCK OFFICIAL VICATION OUT\",\"TLNO\":\"1\",\"INOUT\":\"0\",\"SUMTOWRK\":\"0\",\"Show\":\"\"},{\"SERIALNO\":\"14\",\"TRANSKIND\":\"14\",\"TRANSNAMEA\":\"دخول من إجازة رسمية\",\"TRANSNAMEE\":\"CLOCK OFFICIAL VICATION IN\",\"TLNO\":\"1\",\"INOUT\":\"1\",\"SUMTOWRK\":\"0\",\"Show\":\"\"},{\"SERIALNO\":\"15\",\"TRANSKIND\":\"15\",\"TRANSNAMEA\":\"خروج إجازة مرضية\",\"TRANSNAMEE\":\"CLOCK SICK OUT\",\"TLNO\":\"1\",\"INOUT\":\"0\",\"SUMTOWRK\":\"0\",\"Show\":\"\"},{\"SERIALNO\":\"16\",\"TRANSKIND\":\"16\",\"TRANSNAMEA\":\"دخول من إجازة مرضية\",\"TRANSNAMEE\":\"CLOCK SICK IN\",\"TLNO\":\"1\",\"INOUT\":\"1\",\"SUMTOWRK\":\"0\",\"Show\":\"\"},{\"SERIALNO\":\"17\",\"TRANSKIND\":\"17\",\"TRANSNAMEA\":\"دخول\",\"TRANSNAMEE\":\"CLOCK IN\",\"TLNO\":\"1\",\"INOUT\":\"1\",\"SUMTOWRK\":\"0\",\"Show\":\"\"},{\"SERIALNO\":\"18\",\"TRANSKIND\":\"18\",\"TRANSNAMEA\":\"خروج\",\"TRANSNAMEE\":\"CLOCK OUT\",\"TLNO\":\"1\",\"INOUT\":\"0\",\"SUMTOWRK\":\"0\",\"Show\":\"\"},{\"SERIALNO\":\"19\",\"TRANSKIND\":\"50\",\"TRANSNAMEA\":\"إجازات\",\"TRANSNAMEE\":\"Vacation\",\"TLNO\":\"1\",\"INOUT\":\"10\",\"SUMTOWRK\":\"0\",\"Show\":\"\"}]";

            if (JsonResponse != null && JsonResponse.contains("SERIALNO")) {
                Log.e("importEmploy7 = ", "Successful" );

                JsonResponseSave = JsonResponse;

                Gson gson = new Gson();

                Type collectionType = new TypeToken<Collection<TransactionTable>>() {
                }.getType();
                Collection<TransactionTable> enums = gson.fromJson(JsonResponse, collectionType);
                transactionTableList.clear();
                transactionTableList = (List<TransactionTable>) enums;

                controllClass.saveTransactionTable(transactionTableList);
                controllClass.UpdateTransactionTable();
                Toast.makeText(context, "save", Toast.LENGTH_LONG).show();


                try {

                    SettingActivity settingActivity=(SettingActivity) context;
                    settingActivity.finish();

                }catch (Exception e){

                }

            } else if (JsonResponse != null && JsonResponse.contains("No Data Found.")) {
                // new SyncItemSwitch().execute();
               // Toast.makeText(context, "faild", Toast.LENGTH_LONG).show();

                try {

                    SettingActivity settingActivity=(SettingActivity) context;
                    settingActivity.finish();

                }catch (Exception e){

                }
                Log.e("importEmploy8 = ", "no data" );

            } else {
                try {

                    SettingActivity settingActivity=(SettingActivity) context;
                    settingActivity.finish();

                }catch (Exception e){

                }
                Log.e("tag_itemCard", "****Failed to export data");
                Log.e("importEmploy9 = ", "Failed to export data" );
//                Log.e("tag_itemCard", "****"+JsonResponse.toString());
             //   Toast.makeText(context, "faild export"+ JsonResponse.toString(), Toast.LENGTH_LONG).show();

//                Toast.makeText(context, "Failed to Get data", Toast.LENGTH_SHORT).show();
//                if(pd!=null) {
//                    pd.dismiss();
//                    new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
//                            .setTitleText(context.getResources().getString(R.string.ops))
//                            .setContentText(context.getResources().getString(R.string.fildtoimportitemswitch))
//                            .show();
//                }


            }

        }
    }


}








