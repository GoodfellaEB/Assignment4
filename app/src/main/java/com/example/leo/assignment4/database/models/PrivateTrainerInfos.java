package com.example.leo.assignment4.database.models;

public class PrivateTrainerInfos {
    public static final String TABLE_NAME = "privateTrainerInfos";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_INFOS = "infos";
    public static final String COLUMN_TIMESTAMP = "timestamp";

    private int id;
    private String infos;
    private String timestamp;


    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_INFOS + " TEXT,"
                    + COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                    + ")";

    public PrivateTrainerInfos() {

    }

    public PrivateTrainerInfos(int id, String infos, String timestamp) {
        this.id = id;
        this.infos = infos;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }


    public String getInfos() {
        return infos;
    }

    public void setInfos(String infos) {
        this.infos = infos;
    }
}
