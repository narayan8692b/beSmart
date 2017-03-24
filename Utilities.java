package com.example.pt.kioskbynarayan;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.os.Environment;
import android.text.Html;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Narayan on 24-03-2017.
 */

public class Utilities {
    private Context mContext;
    Utilities(Context context){
        mContext=context;
    }

    public static final String policyFilePath= Environment.getExternalStorageDirectory() +
            File.separator + "Android" + File.separator + "data" + File.separator + "com.example.pt.kioskbynarayan" + File.separator + "kioskByNarayan.txt";

    public static final String policyFolderPath= Environment.getExternalStorageDirectory() +
            File.separator + "Android" + File.separator + "data" + File.separator + "com.example.pt.kioskbynarayan";



    public boolean isBlocked(String input){

        String filedata = null;
        try {
            filedata = readFile(policyFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (filedata.contains(input)){
            return true;
        }else {
            return false;
        }

    }

    private String readFile(String pathname) throws IOException {

        File file = new File(pathname);
        StringBuilder fileContents = new StringBuilder((int)file.length());
        Scanner scanner = new Scanner(file);
        String lineSeparator = System.getProperty("line.separator");

        try {
            while(scanner.hasNextLine()) {
                fileContents.append(scanner.nextLine() + lineSeparator);
            }
            return fileContents.toString();
        } finally {
            scanner.close();
        }
    }

    public boolean isMyLauncherDefault() {
        PackageManager localPackageManager = mContext.getPackageManager();
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.HOME");
        String str = localPackageManager.resolveActivity(intent,
                PackageManager.MATCH_DEFAULT_ONLY).activityInfo.packageName;
        return str.equals(mContext.getPackageName());
    }

    public void makeFolder(){
        Boolean isTextFileMade=false;
        File folder = new File(policyFolderPath);
        boolean success = true;
        if (!folder.exists()) {
            success = folder.mkdirs();

            copyAssetFolder(mContext.getAssets(), "files",
                    "/data/com.example.pt.kioskbynarayan/");
        }
        SharedPreferences.Editor editor = mContext.getSharedPreferences("APP", MODE_PRIVATE).edit();
        if (success) {
            File file = new File(policyFilePath);
           /* try {
                if (file.exists()){
                   // file.delete();
                }
               // isTextFileMade=file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }*/
            editor.putBoolean("Folder", true);
            editor.apply();
        }




    }



//    public void makeHtmlFile(){
//        File file = new File(policyFolderPath + File.separator + "infoLogin.html");
//        try {
//            if(file.createNewFile()){
//                FileWriter writer = new FileWriter(file);
//                writer.append(String.format());
//                writer.flush();
//                writer.close();
//            }
//
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }

    private static boolean copyAssetFolder(AssetManager assetManager,
                                           String fromAssetPath, String toPath) {
        try {
            String[] files = assetManager.list(fromAssetPath);
            new File(toPath).mkdirs();
            boolean res = true;
            for (String file : files)
                if (file.contains("."))
                    res &= copyAsset(assetManager,
                            fromAssetPath + "/" + file,
                            toPath + "/" + file);
                else
                    res &= copyAssetFolder(assetManager,
                            fromAssetPath + "/" + file,
                            toPath + "/" + file);
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static boolean copyAsset(AssetManager assetManager,
                                     String fromAssetPath, String toPath) {
        InputStream in = null;
        OutputStream out = null;
        try {
            in = assetManager.open(fromAssetPath);
            new File(toPath).createNewFile();
            out = new FileOutputStream(toPath);
            copyFile(in, out);
            in.close();
            in = null;
            out.flush();
            out.close();
            out = null;
            return true;
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while((read = in.read(buffer)) != -1){
            out.write(buffer, 0, read);
        }
    }
}
