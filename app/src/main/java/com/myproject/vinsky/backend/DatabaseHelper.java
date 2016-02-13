package com.myproject.vinsky.backend;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    /** DB Name */
    private static final String VINSKY_DB = "vinsky.db";
    /** DB Version */
    private static final int DB_VERSION = 1;

    /** Constructor */
    public DatabaseHelper(Context context) {
        super(context, VINSKY_DB, null, DB_VERSION);
    }

    /**
     * ローカルにDBファイルがない場合のみ実行されるメソッド
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        // CREATE
        String sql = sqlCreate();
        // TODO フラグで成功/失敗判定を入れるべき?
        db.execSQL(sql);
    }

    /**
     * CREATE文を作成して返す
     * @return
     */
    private String sqlCreate() {

        StringBuffer sb = new StringBuffer();

        // 記事内容保存テーブル作成
        sb.append("CREATE TABLE t01_article_table ( ");
        sb.append("  id INTEGER PRIMARY KEY AUTOINCREMENT ");
        sb.append("  ,title TEXT ");
        sb.append("  ,description TEXT ");
        sb.append("  ,contents TEXT ");
        sb.append("  ,watched NTEGER ) ");

        return sb.toString();
    }

    /**
     * アップグレードが行われた時に実行されるメソッド
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
