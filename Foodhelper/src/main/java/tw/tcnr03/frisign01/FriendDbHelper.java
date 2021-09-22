package tw.tcnr03.frisign01;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class FriendDbHelper extends SQLiteOpenHelper {
    String TAG = "JIA=>";
    public String sCreateTableCommand;
    // 資料庫名稱
    private static final String DB_FILE = "friends.db";
    // 資料庫版本，資料結構改變的時候要更改這個數字，通常是加一
    public static final int VERSION = 1;    // 資料表名稱
    private static final String DB_TABLE = "member";    // 資料庫物件，固定的欄位變數
    private static final String crTBsql=" CREATE TABLE "+DB_TABLE+"("
            +"id INTEGER PRIMARY KEY,"+"name TEXT NOT NULL,"+"date TEXT);";
    private static SQLiteDatabase database;
    ////----------------------------------------------------------
//建構式參數說明：
//context 可以操作資料庫的內容本文，一般可直接傳入Activity物件。
//name 要操作資料庫名稱，如果資料庫不存在，會自動被建立出來並呼叫onCreate()方法。
//factory 用來做深入查詢用，入門時用不到。
//version 版本號碼。
    // 需要資料庫的元件呼叫這個方法，這個方法在一般的應用都不需要修改
    public static SQLiteDatabase getDatabase(Context context){
        if (database == null || !database.isOpen())  {
            database = new FriendDbHelper(context, DB_FILE, null, VERSION)
                    .getWritableDatabase();
        }
        return database;
    }
    public FriendDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context,DB_FILE,null,VERSION);
        sCreateTableCommand="";
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(crTBsql);
    }
    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP     TABLE     IF    EXISTS    " + DB_TABLE);
        onCreate(db);
    }
    public long insertRec(String b_name,String b_date) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues rec = new ContentValues();
        rec.put("name", b_name);
        rec.put("date", b_date);
        //rec.put("address", b_address);
        long rowID = db.insert(DB_TABLE, null, rec); //SQLite 新增
        db.close();
        return rowID;
    }
    public int RecCount() {  //有幾筆  計算筆數
        SQLiteDatabase db = getWritableDatabase();
        String sql = "SELECT * FROM " + DB_TABLE;
        Cursor recSet = db.rawQuery(sql, null);
        return recSet.getCount();
    }
    // 修改用的 抓資料
    public ArrayList<String> getRecSet() {
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM " + DB_TABLE;
        Cursor recSet = db.rawQuery(sql, null);
        ArrayList<String> recAry = new ArrayList<String>();
        //----------------------------
//        Log.d(TAG, "recSet=" + recSet);
        int columnCount = recSet.getColumnCount();
        recSet.moveToFirst();
        String fldSet = "";

        if (recSet.getCount() != 0) { // 判斷資料如果 不是0比 才執行抓資料
            for (int i = 0; i < columnCount; i++)
                fldSet += recSet.getString(i) + "#"; // 欄位跟欄位 用 # 做區隔
            recAry.add(fldSet);
        }
        while (recSet.moveToNext()) {
            fldSet = "";
            for (int i = 0; i < columnCount; i++) {
                fldSet += recSet.getString(i) + "#"; // 欄位跟欄位 用 # 做區隔
            }
            recAry.add(fldSet);
        }
        //------------------------
        recSet.close();
        db.close();
//        Log.d(TAG, "recAry=" + recAry);
        return recAry;
    }
    public int clearRec() {
        SQLiteDatabase db = getWritableDatabase();
        String sql = "SELECT * FROM " + DB_TABLE;
        Cursor recSet = db.rawQuery(sql, null);
        if (recSet.getCount() != 0) {
            int rowsAffected = db.delete(DB_TABLE, "1", null);
            db.close();
            return rowsAffected;
        } else {
            db.close();
            return -1;
        }
    }

    //--------------END------------------
}
