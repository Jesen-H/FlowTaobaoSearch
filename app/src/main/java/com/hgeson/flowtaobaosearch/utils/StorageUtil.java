package com.hgeson.flowtaobaosearch.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.content.SharedPreferencesCompat;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * @Describe：存储搜索对象
 * @Date：2018/9/21
 * @Author：hgeson
 */

public class StorageUtil {
    private static final String FILE_NAME = "FILE_NAME";

    public static List<String> get(Context context){
        List<String> list = new ArrayList<>();
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,MODE_PRIVATE);
        int datasize = sp.getInt("datasize", 0);
        for (int i = 0; i < datasize; i++) {
            list.add(sp.getString(i + "",""));
        }
        return list;
    }

    public static void share(Context context,List<String> data){
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        for (int i = 0; i < data.size(); i++) {
            edit.putString(i + "",data.get(i));
        }
        edit.putInt("datasize",data.size());
        edit.apply();
    }

    public static void remove(Context context,String key,boolean isClear){
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        if (isClear){
            edit.clear();
        }else{
            edit.remove(key);
        }
        SharedPreferencesCompat.EditorCompat.getInstance().apply(edit);
    }
}
