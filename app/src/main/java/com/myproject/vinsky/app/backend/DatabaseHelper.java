package com.myproject.vinsky.app.backend;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.myproject.vinsky.app.UI.Item;
import java.util.List;
import java.util.ArrayList;

/**
 * vinsky.dbの作成、テーブルの作成を行うヘルパークラス
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    /** DB Name */
    private static final String DB_NAME = "vinsky.db";
    /** DB Version */
    private static final int DB_VERSION = 1;
    /** Columun */
    // テーブル名:articles
    public static final String TABLE_ARTICLES = "articles";
    // 記事ID:integer
    public static final String ARTICLE_ID = "_id";
    // 記事タイトル:text
    public static final String ARTICLE_TITLE = "title";
    // 記事要約:text
    public static final String ARTICLE_DESCRIPTION = "description";
    // 記事本文:text
    public static final String ARTICLE_CONTENTS = "contents";
    // 記事状態:integer 0:未読 / 1:既読
    public static final String ARTICLE_WATCHFLG = "watcheFlg";
    // 投稿日:text
    public static final String ARTICLE_POSTDATE = "postDate";
    // articleテーブル作成SQL
    private static final String CREATE_ARTICLES_SQL =
            "create table " + TABLE_ARTICLES + " " +
                    "(" + ARTICLE_ID + " integer primary key autoincrement," +
                    ARTICLE_TITLE + " text," +
                    ARTICLE_CONTENTS + " text," +
                    ARTICLE_DESCRIPTION + " text," +
                    ARTICLE_WATCHFLG + " integer," +
                    ARTICLE_POSTDATE + " text)";

    /** Constructor */
    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    /**
     * 端末にDBファイルがない場合のみ実行されるメソッド.
     * DBファイルを作成後、テーブルを作成する.
     * @param db データベースオブジェクト.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_ARTICLES_SQL);
        // TODO onCreateでテーブルが作られない問題がある。 一旦手動で登録するものとし、先の作業を続ける
        Log.d("INFO1","creating db : " + CREATE_ARTICLES_SQL);
        Log.d("INF02","insert dummy data : " + this.makeDummyData());
    }

    /**
     * DBアップグレードが行われた時に実行されるメソッド.
     * @param db データベースオブジェクト.
     * @param oldVersion 旧バージョン番号.
     * @param newVersion 新バージョン番号.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    /**
     * メインクラス用の最新記事一覧取得処理.
     * @return result トップ画面/記事一覧表示用(最大取得数を10件に設定)の記事情報を保持したリスト.
     */
//    public List<Item> getAllArticles(SQLiteDatabase db) {
//
//        List<Item> result = new ArrayList<Item>();
//        Cursor cursor = db.rawQuery(this.getAllArticleSql(), null);
//        cursor.moveToFirst();
//
//        try {
//            while (cursor.moveToNext()) {
//                Item item = new Item();
//                item.setTitle(cursor.getString(cursor.getColumnIndex("title")));
//                item.setSummary(cursor.getString(cursor.getColumnIndex("description")));
//                item.setDescription(cursor.getString(cursor.getColumnIndex("contents")));
//                result.add(item);
//            }
//        } finally {
//            cursor.close();
//        }
//
//        return result;
//    }


    /**
     * 開発用ダミーデータ生成クラス.
     * @return SQL ダミーデータ.
     */
    public String makeDummyData() {

        StringBuilder sb = new StringBuilder();

        sb.append("insert into articles values (0,'title01','test01','contents test01',0,'2016-01-00');");
        sb.append("insert into articles values (1,'title02','test02','contents test02',0,'2016-01-01');");
        sb.append("insert into articles values (2,'title03','test03','contents test03',0,'2016-01-02');");
        sb.append("insert into articles values (3,'title04','test04','contents test04',0,'2016-01-03');");
        sb.append("insert into articles values (4,'title05','test05','contents test05',0,'2016-01-04');");
        sb.append("insert into articles values (5,'title06','test06','contents test06',0,'2016-01-05');");
        sb.append("insert into articles values (6,'title07','test07','contents test07',0,'2016-01-06');");
        sb.append("insert into articles values (7,'title08','test08','contents test08',0,'2016-01-07');");
        sb.append("insert into articles values (8,'title09','test09','contents test09',0,'2016-01-08');");
        sb.append("insert into articles values (9,'title10','test10','contents test10',0,'2016-01-09');");

        return sb.toString();
    }
}