package com.myproject.vinsky.app.UI;

/**
 * Created by vesp on 16/02/20.
 * 取得したRSSデータのなかの個々の記事データの構造を表す
 */
// Item.java
public class Item {
    // 記事のタイトル
    private CharSequence mTitle;
    // 記事の要約
    private CharSequence mSummary;
    // 記事の本文
    private CharSequence mDescription;
    // 記事へのリンク
    private CharSequence mLink;

    public Item() {
        mTitle = "";
        mSummary = "";
        mDescription = "";
        mLink = "";
    }

    // アクセッサ
    public CharSequence getDescription() {
        return mDescription;
    }

    public void setDescription(CharSequence description) {
        mDescription = description;
    }

    public CharSequence getSummary() {
        return mSummary;
    }

    public void setSummary(CharSequence summary) {
        mSummary = summary;
    }

    public CharSequence getTitle() {
        return mTitle;
    }

    public void setTitle(CharSequence title) {
        mTitle = title;
    }

    public CharSequence getLink(){ return mLink;}

    public void setLink(CharSequence link){mLink = link; }
}