package com.myproject.vinsky.backend;

import android.util.Log;
import android.util.Xml;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.StringReader;

public class XmlParser {

    /**
     * RSS1.0に準拠するXMLファイルを解析する
     * @param xml
     */
    public void parseRss1(String xml) {

        try {
            XmlPullParser xmlPullParser = Xml.newPullParser();
            xmlPullParser.setInput(new StringReader(xml));

            int eventType;
            while ((eventType = xmlPullParser.next()) != XmlPullParser.END_DOCUMENT) {

                if (eventType == XmlPullParser.START_TAG &&
                        XmlParserEnum.TITLE.getLabel().equals(xmlPullParser.getName())) {
                    // イベントタイプがSTART_TAGでタイトルが"title"である時の処理

                } else if (eventType == XmlPullParser.END_DOCUMENT) {
                    // テキストファイル終了時の処理
                } else if (eventType == XmlPullParser.START_TAG) {
                    // 何かの開始タグ
                } else if (eventType == XmlPullParser.END_TAG) {
                    // 何かの終了タグ
                } else if (eventType == XmlPullParser.TEXT) {
                }
            }
        } catch (XmlPullParserException e) {
            Log.d("parserRss1", "Error");

        } catch (IOException e){
            Log.d("parserRss1", "Error");
        }
    }

    /**
     * RSS2.0に準拠するXMLファイルを解析する
     * @param xml
     */
    public void parseRss2(String xml) {
    }

    /**
     * ATOMに準拠するXMLファイルを解析する
     * @param xml
     */
    public void parseAtom(String xml) {
    }
}
