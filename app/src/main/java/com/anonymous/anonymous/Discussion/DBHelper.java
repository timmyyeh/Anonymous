package com.anonymous.anonymous.Discussion;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    /**
     * Constructor for DBHelper
     * @param context application context.
     * @param name Database name.
     * @param factory factory information.
     * @param version Version information.
     */
    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    /**
     * Database on create function.
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // 새로운 테이블 생성
        /* 이름은 MONEYBOOK이고, 자동으로 값이 증가하는 _id 정수형 기본키 컬럼과
        item 문자열 컬럼, message 정수형 컬럼, create_at 문자열 컬럼으로 구성된 테이블을 생성. */
        db.execSQL("CREATE TABLE MONEYBOOK (_id INTEGER PRIMARY KEY AUTOINCREMENT, item TEXT, message TEXT, create_at TEXT);");
    }

    /**
     * If we need to use onupgrade function, define here.
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * Insert function.
     * @param create_at Data time.
     * @param title title for the discussion.
     * @param message Message for the discussion.
     */
    public void insert(String create_at, String title, String message) {

        //Opening writiable database.
        SQLiteDatabase db = getWritableDatabase();

        // Insert data into the database.
        db.execSQL("INSERT INTO MONEYBOOK VALUES(null, '" + title + "', '" + message + "', '" + create_at + "');");
        db.close();
    }

    public void update(String item, String message) {
        SQLiteDatabase db = getWritableDatabase();
        // 입력한 항목과 일치하는 행의 가격 정보 수정
        db.execSQL("UPDATE MONEYBOOK SET message=" + message + " WHERE item='" + item + "';");
        db.close();
    }

    public void delete(String item) {
        SQLiteDatabase db = getWritableDatabase();
        // 입력한 항목과 일치하는 행 삭제
        db.execSQL("DELETE FROM MONEYBOOK WHERE item='" + item + "';");
        db.close();
    }

    /**
     * Temporary function returning the result in string format.
     * @return every data result.
     */
    public String getResult() {
        // Opening readable database.
        SQLiteDatabase db = getReadableDatabase();
        String result = "";

        //Accessing db with cursor, and save the data into result string.
        Cursor cursor = db.rawQuery("SELECT * FROM MONEYBOOK", null);
        while (cursor.moveToNext()) {
            result += cursor.getString(0)
                    + " : "
                    + cursor.getString(1)
                    + " | "
                    + cursor.getString(2)
                    + " | "
                    + cursor.getString(3)
                    + "\n";
        }

        return result;
    }

    /**
     * Getting title in String array format.
     * @return titles
     */
    public String[] getTitle() {

        // Opening readable database
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<String> result = new ArrayList<>();

        //Get the title and save it into the String Array.
        Cursor cursor = db.rawQuery("SELECT * FROM MONEYBOOK", null);
        for (int i=0; cursor.moveToNext(); i++) {
            result.add(cursor.getString(1));
        }

        return result.toArray(new String[0]);
    }

//    public String getResult() {
//        String[] titles = getTitle();
//        //String[] titles = {"HI", "Testing"};
//        String result = "";
//
//        for(String str : titles) {
//
//            result += str + "\n";
//        }
//
//        return result;
//    }
}


