package com.nml.database;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.nml.database.db.Constant;
import com.nml.database.db.DBManager;
import com.nml.database.db.MySqliteHelper;

/**
 * @author android
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private MySqliteHelper helper;

    private Button btn_createdb;
    private Button btn_insert,btn_update,btn_delete;
    private Button btn_insertapi,btn_updateapi,btn_deleteapi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        finViews();
        init();

    }

    private void init() {
        helper = DBManager.getInstance(this);
    }

    private void finViews() {
        btn_createdb = findViewById(R.id.btn_createdb);

        btn_insert = findViewById(R.id.btn_insert);
        btn_update = findViewById(R.id.btn_update);
        btn_delete = findViewById(R.id.btn_delete);

        btn_insertapi = findViewById(R.id.btn_insertapi);
        btn_updateapi = findViewById(R.id.btn_updateapi);
        btn_deleteapi = findViewById(R.id.btn_deleteapi);

        btn_createdb.setOnClickListener(this);
        btn_insert.setOnClickListener(this);
        btn_update.setOnClickListener(this);
        btn_delete.setOnClickListener(this);

        btn_insertapi.setOnClickListener(this);
        btn_updateapi.setOnClickListener(this);
        btn_deleteapi.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        SQLiteDatabase db = helper.getWritableDatabase();
        switch (view.getId()){
            case R.id.btn_createdb://点击按钮，创建数据库
                /**
                 * getWritableDatabase and getReadableDatabase 创建或打开数据库
                 * 如果数据库不存在则创建数据库，如果数据库存在直接打开数据库
                 * 默认情况下两个函数都表示打开或创建可读可写的数据库对象。
                 * 如果磁盘已满或数据库本身权限等情况下getReadableDatabase()打开的是只读数据库
                 */

                break;

            case R.id.btn_insert:

                String insertSql1 = "insert into user values(1,'zhangshan',11)";
                DBManager.execSQL(db,insertSql1);

                String insertSql2 = "insert into "+ Constant.TABLE_NAME+" values(2,'李四',22)";
                DBManager.execSQL(db,insertSql2);

                break;

            case R.id.btn_update:
//                String updateSql = "update user set name='android' where _id =1";
                String updateSql = "update "+Constant.TABLE_NAME+" set "+Constant.NAME+"='android' " +
                        "where "+Constant._ID+" =1";
                DBManager.execSQL(db,updateSql);
                break;

            case R.id.btn_delete:
//                String deleteSql = "delete from user where _id=2";
                String deleteSql = "delete from "+ Constant.TABLE_NAME+" where "+Constant._ID+"=2";
                DBManager.execSQL(db,deleteSql);
                break;

            case R.id.btn_query:
//                db = helper.getWritableDatabase();
////                String querySql = "select * from user";
//                String querySql = "select * from "+Constant.TABLE_NAME;
//                DBManager.execSQL(db,querySql);
//                db.close();
                Toast.makeText(MainActivity.this,"查询",Toast.LENGTH_LONG).show();
                break;


            case R.id.btn_insertapi://插入
                ContentValues values = new ContentValues();
                values.put(Constant._ID,3);//put(表示插入数据库的字段名称,表示插入该字段的具体值);
                values.put(Constant.NAME,"王五");
                values.put(Constant.AGE,25);
                /**
                 * public long insert(String table, String nullColumnHack, ContentValues values)
                 * String table  表示插入数据表的名称
                 * String nullColumnHack  SQLite数据库中不允许全部为null
                 * ContentValues values 键为String类型的hashmap集合
                 * 返回值 long 表示插入数据的列数
                 */
                long result = db.insert(Constant.TABLE_NAME,null,values);
                if (result>0){
                    Toast.makeText(MainActivity.this,"插入数据成功！",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(MainActivity.this,"插入数据失败！",Toast.LENGTH_LONG).show();
                }
                break;

            case R.id.btn_updateapi://更新
                /**
                 * public int update(String table, ContentValues values, String whereClause, String[] whereArgs)
                 * String table 表示修改的数据表的名称
                 * ContentValues values 表示键为String类型的hashmap
                 * String whereClause 表示修改条件
                 * String[] whereArgs 表示修改条件的占位符
                 * 返回值 int 表示成功修改的条数
                 */
                ContentValues cv = new ContentValues();
                cv.put(Constant.NAME,"王五666");//put(需要修改的字段名称，修改后的字段值)

//                int count = db.update("user",cv,"_id=3",null);
//                int count = db.update(Constant.TABLE_NAME,cv,Constant._ID+"=3",null);
                int count = db.update(Constant.TABLE_NAME,cv,Constant._ID+"=?",new String[]{"3"});
                if (count>0){
                    Toast.makeText(MainActivity.this,"修改数据成功！",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(MainActivity.this,"修改数据失败！",Toast.LENGTH_LONG).show();
                }
                break;

            case R.id.btn_deleteapi://删除
                /**
                 * public int delete(String table, String whereClause, String[] whereArgs)
                 * String table 表示需要删除的数据表名称
                 * String whereClause 表示删除条件
                 * String[] whereArgs 表示删除条件的占位符
                 */
                int deleteCount = db.delete(Constant.TABLE_NAME, Constant._ID+"=?", new String[]{"1"});
                if (deleteCount>0){
                    Toast.makeText(MainActivity.this,"删除数据成功！",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(MainActivity.this,"删除数据失败！",Toast.LENGTH_LONG).show();
                }
                break;

            case R.id.btn_queryapi:
                break;

            default:
                db.close();
                break;
        }
    }


}
