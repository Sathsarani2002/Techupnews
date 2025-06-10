// NewsDatabaseHelper.java
package com.example.techupnews;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;

public class NewsDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "news.db";
    private static final int DATABASE_VERSION = 1;

    public NewsDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE news (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "category TEXT," +
                "description TEXT," +
                "imageResId INTEGER)";
        db.execSQL(CREATE_TABLE);
        insertInitialData(db);
    }

    private void insertInitialData(SQLiteDatabase db) {
        insertNews(db, "Sports", "Football tournament next week", R.drawable.image1);
        insertNews(db, "Sports", "Cricket team selection announced", R.drawable.image2);
        insertNews(db, "Academic", "New AI course launched", R.drawable.image3);
        insertNews(db, "Academic", "Exam schedule released", R.drawable.image4);
        insertNews(db, "Events", "TechFest on July 20", R.drawable.image5);
        insertNews(db, "Events", "Cultural night registration open", R.drawable.image6);
    }

    private void insertNews(SQLiteDatabase db, String category, String desc, int imgResId) {
        ContentValues values = new ContentValues();
        values.put("category", category);
        values.put("description", desc);
        values.put("imageResId", imgResId);
        db.insert("news", null, values);
    }

    public ArrayList<News> getNewsByCategory(String category) {
        ArrayList<News> newsList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query("news", null, "category=?", new String[]{category},
                null, null, "id DESC");

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String desc = cursor.getString(cursor.getColumnIndexOrThrow("description"));
            int imageResId = cursor.getInt(cursor.getColumnIndexOrThrow("imageResId"));
            newsList.add(new News(id, category, desc, imageResId));
        }
        cursor.close();
        return newsList;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS news");
        onCreate(db);
    }
}
