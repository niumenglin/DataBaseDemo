package com.nml.database.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

/**
 * @ProjectName: DataBaseDemo
 * @Package: com.nml.database.db
 * @ClassName: DBManager
 * @Description: 主要是对数据库操作的工具类
 * @Author: android
 */
public class DBManager {

    private static MySqliteHelper helper;

    /**
     * 单例模式获取 MySqliteHelper对象
     * @param context 上下文对象
     * @return
     */
    public static MySqliteHelper getInstance(Context context){
        if (helper ==null){
            helper = new MySqliteHelper(context);
        }
        return helper;
    }

    public static void execSQL(SQLiteDatabase db,String sql){
        if (db!=null){
            if (!TextUtils.isEmpty(sql)){
                db.execSQL(sql);
            }
        }
    }



}
