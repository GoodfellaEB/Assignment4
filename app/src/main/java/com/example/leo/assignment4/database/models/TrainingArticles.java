package com.example.leo.assignment4.database.models;

public class TrainingArticles {
    public static final String TABLE_NAME = "trainingArticles";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_ARTICLES = "articles";
    public static final String COLUMN_TIMESTAMP = "timestamp";

    private int id;
    private String articles;
    private String timestamp;


    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_ARTICLES + " TEXT,"
                    + COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                    + ")";

    public TrainingArticles() {

    }

    public TrainingArticles(int id, String articles, String timestamp) {
        this.id = id;
        this.articles = articles;
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

    public String getArticles() {
        return articles;
    }

    public void setArticles(String articles) {
        this.articles = articles;
    }
}
