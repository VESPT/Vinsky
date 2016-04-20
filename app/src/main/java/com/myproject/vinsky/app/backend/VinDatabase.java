package com.myproject.vinsky.app.backend;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.myproject.vinsky.app.UI.Item;
import java.util.List;
import java.util.ArrayList;

public class VinDatabase extends SQLiteOpenHelper {

    /** DB Name */
    private static final String VINSKY_DB = "vinsky.db";
    /** DB Version */
    private static final int DB_VERSION = 1;

    /** Constructor */
    public VinDatabase(Context context) {
        super(context, VINSKY_DB, null, DB_VERSION);
    }

    /**
     * ローカルにDBファイルがない場合のみ実行されるメソッド.
     * @param db データベースオブジェクト.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        // CREATE
        db.execSQL(this.sqlCreate());
        // TODO 開発用 ダミーデータ生成 データ取得処理作成完了後に削除すること
        String makeDummySql = makeDummyData();
        db.execSQL(makeDummySql);
    }

    /**
     * CREATE文を作成して返す.
     * @return SQL / CREATE文.
     */
    private String sqlCreate() {

        StringBuilder sb = new StringBuilder();

        // 記事内容保存テーブル作成
        sb.append("CREATE TABLE t01_article_table ( ");
        sb.append(" id INTEGER PRIMARY KEY AUTOINCREMENT");
        sb.append(" ,title TEXT");
        sb.append(" ,description TEXT");
        sb.append(" ,contents TEXT");
        sb.append(" ,watched INTEGER");
        sb.append(" ,postDate TEXT )");

        return sb.toString();
    }

    /**
     * アップグレードが行われた時に実行されるメソッド.
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
    public List<Item> getAllArticles(SQLiteDatabase db) {

        List<Item> result = new ArrayList<Item>();
        Cursor cursor = db.rawQuery(this.getAllArticleSql(), null);
        cursor.moveToFirst();

        try {
            while (cursor.moveToNext()) {
                Item item = new Item();
                item.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                item.setSummary(cursor.getString(cursor.getColumnIndex("description")));
                item.setDescription(cursor.getString(cursor.getColumnIndex("contents")));
                result.add(item);
            }
        } finally {
            cursor.close();
        }

        return result;
    }

    /**
     * アプリ起動時のメインメニュー表示用/最新記事一覧取得用SQLを返す.
     * @return SQL トップ画面/記事一覧表示用SQL取得.
     */
    private String getAllArticleSql() {

        StringBuilder sb = new StringBuilder();

        sb.append("SELECT title, description, contents, postDate");
        sb.append("  FROM t01_article_table ");
        sb.append("  ORDER BY postDate ASC ");
        sb.append("  LIMIT 10;");

        return sb.toString();
    }

    /**
     * 開発用ダミーデータ生成クラス.
     * @return SQL ダミーデータ.
     */
    public String makeDummyData() {

        StringBuilder sb = new StringBuilder();

        sb.append("INSERT INTO t01_article_table VALUES (0,\"title01\",\"test01\",\"contents test01\",NULL);");
        sb.append("INSERT INTO t01_article_table VALUES (1,\"title02\",\"test02\",\"contents test02\",NULL);");
        sb.append("INSERT INTO t01_article_table VALUES (2,\"title03\",\"test03\",\"contents test03\",NULL);");
        sb.append("INSERT INTO t01_article_table VALUES (3,\"title04\",\"test04\",\"contents test04\",NULL);");
        sb.append("INSERT INTO t01_article_table VALUES (4,\"title05\",\"test05\",\"contents test05\",NULL);");
        sb.append("INSERT INTO t01_article_table VALUES (5,\"title06\",\"test06\",\"contents test06\",NULL);");
        sb.append("INSERT INTO t01_article_table VALUES (6,\"title07\",\"test07\",\"contents test07\",NULL);");
        sb.append("INSERT INTO t01_article_table VALUES (7,\"title08\",\"test08\",\"contents test08\",NULL);");
        sb.append("INSERT INTO t01_article_table VALUES (8,\"title09\",\"test09\",\"contents test09\",NULL);");
        sb.append("INSERT INTO t01_article_table VALUES (9,\"title10\",\"test10\",\"contents test10\",NULL);");

        return sb.toString();
    }
}