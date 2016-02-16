package com.myproject.vinsky.backend;

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
    private static int eventType;

    /**
     * RSS1.0に準拠するXMLファイルを解析する.
     * 解析結果をBean変数内に整理してBeanごと返す.
     * @param xml RSS1.0準拠のXMLファイル
     */
    public XmlParserBean parseRss1(String xml) throws XmlPullParserException, IOException {

    	// ブログ情報全体の格納先となるビーン.
    	XmlParserBean xmlParserBean = new XmlParserBean();
    	// ブログ情報(channelタグ内情報)を保持するハッシュマップ.
    	HashMap<String, String> channel = null;
    	// 記事情報(itemタグ内情報)を保持するリスト.
    	List<HashMap<String, String>> articles = new ArrayList<HashMap<String, String>>();

        XmlPullParser xmlPullParser = Xml.newPullParser();
        xmlPullParser.setInput(new StringReader(xml));

        while ((eventType = xmlPullParser.next()) != XmlPullParser.END_DOCUMENT) {

            if (eventType == XmlPullParser.START_TAG &&
                    xmlPullParser.getName().equals(XmlParserEnum.CHANNEL.getName())) {
                // channelタグを処理
                channel = parseChannelTag(xmlPullParser);

            } else if (eventType == XmlPullParser.START_TAG &&
                    xmlPullParser.getName().equals(XmlParserEnum.ITEM.getName())) {
                // itemタグを処理
                articles.add(parseItemTag(xmlPullParser));

            } else {
            	// その他タグは無視
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
        // TODO 他に属性がある場合、仕様では順序まで保障されていない可能性あり
        channel.put(XmlParserEnum.RDF_ABOUT.getName(), xmlPullParser.getAttributeValue(0));

        // (2)ブログの基本情報を取得.
        while ((eventType = xmlPullParser.next()) != XmlPullParser.END_TAG &&
                xmlPullParser.getName().equals(XmlParserEnum.CHANNEL.getName())) {

            if (eventType == XmlPullParser.START_TAG &&
                    xmlPullParser.getName().equals(XmlParserEnum.TITLE.getName())) {
                // title
                channel.put(XmlParserEnum.TITLE.getName(),
                        StringUtils.defaultString(xmlPullParser.getText()));

            } else if (eventType == XmlPullParser.START_TAG &&
                    xmlPullParser.getName().equals(XmlParserEnum.LINK.getName())) {
                // link
                channel.put(XmlParserEnum.LINK.getName(),
                        StringUtils.defaultString(xmlPullParser.getText()));

            } else if (eventType == XmlPullParser.START_TAG &&
                    xmlPullParser.getName().equals(XmlParserEnum.DESCRIPTION.getName())) {
                // description
                channel.put(XmlParserEnum.DESCRIPTION.getName(),
                        StringUtils.defaultString(xmlPullParser.getText()));

            } else if (eventType == XmlPullParser.START_TAG &&
                    xmlPullParser.getName().equals(XmlParserEnum.ITEM.getName())) {
                // item
                //TODO itemはメソッドを使いまわせるよう記述する
            }
        }
        return channel;
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
        // TODO RSSの仕様では属性の順序まで保障されていない可能性があるので要確認
        article.put(XmlParserEnum.RDF_ABOUT.getName(),
                StringUtils.defaultString(xmlPullParser.getAttributeValue(0)));

        // (2)記事情報を取得.
        // TODO この条件じゃ駄目な気がする
        while ((eventType = xmlPullParser.next()) != XmlPullParser.END_TAG &&
                xmlPullParser.getName().equals(XmlParserEnum.ITEM.getName())) {

            if (eventType == XmlPullParser.START_TAG &&
                    xmlPullParser.getName().equals(XmlParserEnum.TITLE.getName())) {
                // title
                article.put(XmlParserEnum.TITLE.getName(),
                        StringUtils.defaultString(xmlPullParser.getText()));

            } else if (eventType == XmlPullParser.START_TAG &&
                    xmlPullParser.getName().equals(XmlParserEnum.DESCRIPTION.getName())) {
                // description
                article.put(XmlParserEnum.DESCRIPTION.getName(),
                        StringUtils.defaultString(xmlPullParser.getText()));

            } else if (eventType == XmlPullParser.START_TAG &&
                    xmlPullParser.getName().equals(XmlParserEnum.LINK.getName())) {
                // link
                article.put(XmlParserEnum.LINK.getName(),
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
    public void parseRss2(String xml) {
    }

    /**
     * ATOMに準拠するXMLファイルを解析する
     * @param xml xml文字列
     */
    public void parseAtom(String xml) {
    }
}