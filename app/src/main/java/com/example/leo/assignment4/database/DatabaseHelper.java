package com.example.leo.assignment4.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.leo.assignment4.database.models.PrivateTrainerInfos;
import com.example.leo.assignment4.database.models.TrainingArticles;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "assignment4_db";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(PrivateTrainerInfos.CREATE_TABLE);
        db.execSQL(TrainingArticles.CREATE_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + PrivateTrainerInfos.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TrainingArticles.TABLE_NAME);

        // Create tables again
        onCreate(db);
    }

    /**
    INFOS TABLE METHOD
    **/

    public long insertInfo(String info) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(PrivateTrainerInfos.COLUMN_INFOS, info);

        // insert row
        long id = db.insert(PrivateTrainerInfos.TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    public PrivateTrainerInfos getInfo(long id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(PrivateTrainerInfos.TABLE_NAME,
                new String[]{PrivateTrainerInfos.COLUMN_ID, PrivateTrainerInfos.COLUMN_INFOS, PrivateTrainerInfos.COLUMN_TIMESTAMP},
                PrivateTrainerInfos.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare note object
        PrivateTrainerInfos infos = new PrivateTrainerInfos(
                cursor.getInt(cursor.getColumnIndex(PrivateTrainerInfos.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(PrivateTrainerInfos.COLUMN_INFOS)),
                cursor.getString(cursor.getColumnIndex(PrivateTrainerInfos.COLUMN_TIMESTAMP)));

        // close the db connection
        cursor.close();

        return infos;
    }

    public List<PrivateTrainerInfos> getAllInfos() {
        List<PrivateTrainerInfos> infos = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + PrivateTrainerInfos.TABLE_NAME + " ORDER BY " +
                PrivateTrainerInfos.COLUMN_TIMESTAMP + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                PrivateTrainerInfos info = new PrivateTrainerInfos();
                info.setId(cursor.getInt(cursor.getColumnIndex(PrivateTrainerInfos.COLUMN_ID)));
                info.setInfos(cursor.getString(cursor.getColumnIndex(PrivateTrainerInfos.COLUMN_INFOS)));
                info.setTimestamp(cursor.getString(cursor.getColumnIndex(PrivateTrainerInfos.COLUMN_TIMESTAMP)));

                infos.add(info);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return infos;
    }

    public int getInfosCount() {
        String countQuery = "SELECT  * FROM " + PrivateTrainerInfos.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();


        // return count
        return count;
    }

    public int updateInfo(PrivateTrainerInfos info) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PrivateTrainerInfos.COLUMN_INFOS, info.getInfos());

        // updating row
        return db.update(PrivateTrainerInfos.TABLE_NAME, values, PrivateTrainerInfos.COLUMN_ID + " = ?",
                new String[]{String.valueOf(info.getId())});
    }

    public void deleteInfo(PrivateTrainerInfos info) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(PrivateTrainerInfos.TABLE_NAME, PrivateTrainerInfos.COLUMN_ID + " = ?",
                new String[]{String.valueOf(info.getId())});
        db.close();
    }

    /**
     TRAINING ARTICLES TABLE METHODS
     **/

    public long insertArticle(String article) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(TrainingArticles.COLUMN_ARTICLES, article);

        // insert row
        long id = db.insert(TrainingArticles.TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    public TrainingArticles getArticle(long id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TrainingArticles.TABLE_NAME,
                new String[]{TrainingArticles.COLUMN_ID, TrainingArticles.COLUMN_ARTICLES, TrainingArticles.COLUMN_TIMESTAMP},
                TrainingArticles.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare note object
        TrainingArticles articles = new TrainingArticles(
                cursor.getInt(cursor.getColumnIndex(TrainingArticles.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(TrainingArticles.COLUMN_ARTICLES)),
                cursor.getString(cursor.getColumnIndex(TrainingArticles.COLUMN_TIMESTAMP)));

        // close the db connection
        cursor.close();

        return articles;
    }

    public List<TrainingArticles> getAllArticles() {
        List<TrainingArticles> articles = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TrainingArticles.TABLE_NAME + " ORDER BY " +
                TrainingArticles.COLUMN_TIMESTAMP + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                TrainingArticles article = new TrainingArticles();
                article.setId(cursor.getInt(cursor.getColumnIndex(TrainingArticles.COLUMN_ID)));
                article.setArticles(cursor.getString(cursor.getColumnIndex(TrainingArticles.COLUMN_ARTICLES)));
                article.setTimestamp(cursor.getString(cursor.getColumnIndex(TrainingArticles.COLUMN_TIMESTAMP)));

                articles.add(article);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return articles;
    }

    public int getArticlesCount() {
        String countQuery = "SELECT  * FROM " + TrainingArticles.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();


        // return count
        return count;
    }

    public int updateArticle(TrainingArticles article) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TrainingArticles.COLUMN_ARTICLES, article.getArticles());

        // updating row
        return db.update(TrainingArticles.TABLE_NAME, values, TrainingArticles.COLUMN_ID + " = ?",
                new String[]{String.valueOf(article.getId())});
    }

    public void deleteArticle(TrainingArticles article) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TrainingArticles.TABLE_NAME, TrainingArticles.COLUMN_ID + " = ?",
                new String[]{String.valueOf(article.getId())});
        db.close();
    }
}
