package com.myproject.vinsky.app.backend;

import android.util.Log;
import android.util.Xml;
import org.apache.commons.lang3.StringUtils;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class XmlParser {

    /** 解析状態を保持するための変数 */
    private int eventType;

    /** コンストラクタ */
    public XmlParser() {
    }

    /**
     * XML文字列を受け取り、そのバージョンで処理を振り分ける
     * @param xml
     * @return
     * @throws XmlPullParserException
     * @throws IOException
     */
    public XmlParserBean parseXml(String xml) throws XmlPullParserException, IOException {

        // ブログ情報全体の格納先となるビーン.
        XmlParserBean xmlParserBean = null;
        XmlPullParser xmlPullParser = Xml.newPullParser();
        xmlPullParser.setInput(new StringReader(xml));

        // xmlの最初のタグ(=バージョン)を取得
        eventType = xmlPullParser.next();
        if (eventType == XmlPullParser.START_TAG &&
                xmlPullParser.getName().equals(XmlParserEnum.XML.getName())) {

            if (xmlPullParser.getAttributeValue(0).equals(XmlParserEnum.VERSION_1.getName())) {
                // xml version 1.0
                xmlParserBean = parseRss1(xmlPullParser);
            }
        }

        return xmlParserBean;
    }

    /**
     * RSS1.0に準拠するXMLファイルを解析する.
     * 解析結果をBean変数内に整理してBeanごと返す.
     * channel情報はHashMap、記事情報は1記事1HashMapのリストとしてBeanに格納される。
     */
    private XmlParserBean parseRss1(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {

        // ブログ情報全体の格納先となるビーン.
        XmlParserBean xmlParserBean = new XmlParserBean();
        // ブログ情報(channelタグ内情報)を保持するハッシュマップ.
        HashMap<String, String> channel = null;
        // 記事情報(itemタグ内情報)を保持するリスト.
        List<HashMap<String, String>> articles = new ArrayList<HashMap<String, String>>();

        while ((eventType = xmlPullParser.next()) != XmlPullParser.END_DOCUMENT) {

            if (eventType == XmlPullParser.START_TAG &&
                    xmlPullParser.getName().equals(XmlParserEnum.RDF_RDF.getName())) {
                continue;

            } else if (eventType == XmlPullParser.START_TAG &&
                    xmlPullParser.getName().equals(XmlParserEnum.CHANNEL.getName())) {
                // channel
                channel = parseChannelTag(xmlPullParser);

            } else if (eventType == XmlPullParser.START_TAG &&
                    xmlPullParser.getName().equals(XmlParserEnum.ITEM.getName())) {
                // item
                articles.add(parseItemTag(xmlPullParser));

            } else {
                continue;
            }
        }

        xmlParserBean.setChannels(channel);
        xmlParserBean.setArticles(articles);

        return xmlParserBean;
    }

    /**
     * CHANNELタグ解析用メソッド.
     * @param xmlPullParser XmlPullParserオブジェクト
     * @return channel Channelタグ内の情報をまとめたハッシュ
     * @throws XmlPullParserException 例外
     * @throws IOException 例外
     */
    private HashMap<String, String> parseChannelTag(XmlPullParser xmlPullParser)
            throws XmlPullParserException, IOException {

        HashMap<String, String> channel = new HashMap<String, String>();

        // (1)ブログへのURLを取得.
        // TODO rssの仕様では属性の付与は必須だが、存在しない場合の対策をいれること.
        if (xmlPullParser.getAttributeValue(0) != null) {
            channel.put(XmlParserEnum.RDF_ABOUT.getName(), xmlPullParser.getAttributeValue(0));
        }

        // (2)ブログの基本情報を取得.
        while ((eventType = xmlPullParser.next()) != XmlPullParser.END_TAG &&
                xmlPullParser.getName().equals(XmlParserEnum.CHANNEL.getName())) {

            if (eventType == XmlPullParser.START_TAG &&
                    xmlPullParser.getName().equals(XmlParserEnum.TITLE.getName())) {
                // title
                Log.d(XmlParserEnum.TITLE.getName(), xmlPullParser.getText());
                channel.put(XmlParserEnum.TITLE.getName(),
                        StringUtils.defaultString(xmlPullParser.getText()));

            } else if (eventType == XmlPullParser.START_TAG &&
                    xmlPullParser.getName().equals(XmlParserEnum.LINK.getName())) {
                // link
                Log.d(XmlParserEnum.LINK.getName(), xmlPullParser.getText());
                channel.put(XmlParserEnum.LINK.getName(),
                        StringUtils.defaultString(xmlPullParser.getText()));

            } else if (eventType == XmlPullParser.START_TAG &&
                    xmlPullParser.getName().equals(XmlParserEnum.DESCRIPTION.getName())) {
                // description
                Log.d(XmlParserEnum.DESCRIPTION.getName(), xmlPullParser.getText());
                channel.put(XmlParserEnum.DESCRIPTION.getName(),
                        StringUtils.defaultString(xmlPullParser.getText()));

            } else if (eventType == XmlPullParser.START_TAG &&
                    xmlPullParser.getName().equals(XmlParserEnum.ITEMS.getName())) {
                // items
                //TODO channelの中にはchannel用のitemsタグがあるので、それに合わせて処理を作る

            } else {
                // 終了タグまで処理を続行する
                // TODO 終了タグが記述されていない場合の考慮を入れるかどうか
                continue;
            }
        }
        return channel;
    }
	
	private void parseChannelItemsTag() {
	}

    /**
     * ITEMタグ解析用メソッド.
     * @param xmlPullParser XmlPullParserオブジェクト
     * @return article 記事情報をまとめたハッシュ
     * @throws XmlPullParserException 例外
     * @throws IOException 例外
     */
    private HashMap<String, String> parseItemTag(XmlPullParser xmlPullParser)
            throws XmlPullParserException, IOException {

        HashMap<String, String> article = new HashMap<String, String>();

        // (1)記事へのURLを取得.
        // TODO 属性が存在していないケースがある。その場合の対処はどうするか
        article.put(XmlParserEnum.RDF_ABOUT.getName(),
                StringUtils.defaultString(xmlPullParser.getAttributeValue(0)));

        // (2)記事情報を取得.
        while ((eventType = xmlPullParser.next()) != XmlPullParser.END_TAG &&
                xmlPullParser.getName().equals(XmlParserEnum.ITEM.getName())) {

            if (eventType == XmlPullParser.START_TAG &&
                    xmlPullParser.getName().equals(XmlParserEnum.TITLE.getName())) {
                // title
                Log.d(XmlParserEnum.TITLE.getName(), xmlPullParser.getText());
                article.put(XmlParserEnum.TITLE.getName(),
                        StringUtils.defaultString(xmlPullParser.getText()));

            } else if (eventType == XmlPullParser.START_TAG &&
                    xmlPullParser.getName().equals(XmlParserEnum.LINK.getName())) {
                // link
                Log.d(XmlParserEnum.LINK.getName(), xmlPullParser.getText());
                article.put(XmlParserEnum.LINK.getName(),
                        StringUtils.defaultString(xmlPullParser.getText()));

            } else if (eventType == XmlPullParser.START_TAG &&
                    xmlPullParser.getName().equals(XmlParserEnum.DESCRIPTION.getName())) {
                // description
                Log.d(XmlParserEnum.DESCRIPTION.getName(), xmlPullParser.getText());
                article.put(XmlParserEnum.DESCRIPTION.getName(),
                        StringUtils.defaultString(xmlPullParser.getText()));

            } else if (eventType == XmlPullParser.START_TAG &&
                	xmlPullParser.getName().equals(XmlParserEnum.DC_DATE.getName())) {
                // dc:date
                Log.d(XmlParserEnum.DC_DATE.getName(), xmlPullParser.getText());
                article.put(XmlParserEnum.DC_DATE.getName(),
                        StringUtils.defaultString(xmlPullParser.getText()));
 
            } else {
                // その他タグは無視
                continue;
            }
        }
        return article;
    }

    /**
     * RSS2.0に準拠するXMLファイルを解析する
     * @param xml xml文字列
     */
    private void parseRss2(String xml) {
    }

    /**
     * ATOMに準拠するXMLファイルを解析する
     * @param xml xml文字列
     */
    private void parseAtom(String xml) {
    }

    /**
     * TODO 後で削除すること.
     * JUNIT動作確認用メソッド
     * @param id
     * @return
     */
    public String testMeth(int id) {
        String res = null;

        if (id == 1) {
            res = "test1";
        } else if (id == 2) {
            res = "test2";
        } else {
            res = "test3";
        }

        return res;
    }
}