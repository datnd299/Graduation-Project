package com.example.signboard2.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;

import com.example.signboard2.R;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.FileOutputStream;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class Utils{
    public static boolean isMockSettingsON(Context context) {
        // returns true if mock location enabled, false if not enabled.
        if (Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ALLOW_MOCK_LOCATION).equals("0"))
            return false;
        else
            return true;
    }
    public static String toDateTimeString(String dateStr) {
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(dateStr);
            Format formatter = new SimpleDateFormat("dd/MM/yy HH:mm");
            String s = formatter.format(date);
            return s;
        }catch (Exception e){
            return  "";
        }

    }
    public static File saveImage(Bitmap finalBitmap) {

        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/saved_images");
        if (!myDir.exists()) {
            boolean success= myDir.mkdirs();
            if (success) {
                Log.d("xxxx", "SaveImage: ");
            } else {
                Log.d("xxxx", "SaveImage: sadf sd ");
            }
        }
        Random generator = new Random();
        int n = 100000;
        n = generator.nextInt(n);
        String fname = "img" +n+".png";
        File file = new File (myDir, fname);
        if (file.exists ())
            file.delete ();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }
    public static String toTimeUnitString(String code){
        if(code.equals("year")){
            return "Năm";
        }
        if(code.equals("moth")){
            return "Tháng";
        }
        if(code.equals("week")){
            return "Tuần";
        }if(code.equals("day")){
            return "Ngày";
        }

        return "";

    }
    public static String getStringTimeOfTask(JsonObject task){
        return toDateTimeString(task.get("start").getAsString())+"->"+toDateTimeString(task.get("end").getAsString());
    }
    public static String getStatusStringOfTask(JsonObject task){
        int status = task.get("status").getAsInt();
        if(status==-3){
            return "Hủy";
        }
        if(status==-2){
            return "Ẩn";
        }
        if(status==-1){
            return "Gốc";
        }
        if(status==1){
            return "Mới";
        }
        if(status==2){
            return "Đã báo cáo";
        }
        if(status==3){
            return "Hoàn thành";
        }
        if(status==0){
            return "Không duyệt";
        }
        return "---";
    }
    public static String getPlacesOfTask(JsonObject task){
        if(task.get("type").getAsString().equals("setup")){
//            imgIcon.setImageResource(R.drawable.ic_cogs_solid);
            return task.getAsJsonObject("setup_task").getAsJsonObject("place_rental").get("name").getAsString();
        }
        if(task.get("type").getAsString().equals("check")){
            String str = "";
            JsonArray jArPl = task.getAsJsonObject("check_task").getAsJsonArray("place_rental");
            for (JsonElement jEPl:jArPl) {
                JsonObject jOPl = jEPl.getAsJsonObject();
                str+=jOPl.get("name").getAsString()+", ";
            }
            if(str.length()>0){
                return str.substring(0,str.length()-2);
            }
            return str;
        }
        if(task.get("type").getAsString().equals("fee")){
            String str = "";
            JsonArray jArPl = task.getAsJsonObject("fee_task").getAsJsonArray("fee_detail");
            for (JsonElement jEPl:jArPl) {
                JsonObject jOPl = jEPl.getAsJsonObject();
                str+=jOPl.getAsJsonObject("place_rental").get("name").getAsString()+", ";
            }
            if(str.length()>0){
                return str.substring(0,str.length()-2);
            }
            return str;
        }
        if(task.get("type").getAsString().equals("report")){
           return task.getAsJsonObject("report_task").getAsJsonObject("place_rental").get("name").getAsString();
        }
        return "";
    }
    public static boolean areThereMockPermissionApps(Context context) {
        int count = 0;

        PackageManager pm = context.getPackageManager();
        List<ApplicationInfo> packages =
                pm.getInstalledApplications(PackageManager.GET_META_DATA);

        for (ApplicationInfo applicationInfo : packages) {
            try {
                PackageInfo packageInfo = pm.getPackageInfo(applicationInfo.packageName,
                        PackageManager.GET_PERMISSIONS);

                // Get Permissions
                String[] requestedPermissions = packageInfo.requestedPermissions;

                if (requestedPermissions != null) {
                    for (int i = 0; i < requestedPermissions.length; i++) {
                        if (requestedPermissions[i]
                                .equals("android.permission.ACCESS_MOCK_LOCATION")
                                && !applicationInfo.packageName.equals(context.getPackageName())) {
                            count++;
                        }
                    }
                }
            } catch (PackageManager.NameNotFoundException e) {
                Log.e("Got exception " , e.getMessage());
            }
        }

        if (count > 0)
            return true;
        return false;
    }
}