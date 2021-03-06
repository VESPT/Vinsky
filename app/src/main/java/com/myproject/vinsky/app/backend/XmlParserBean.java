package com.myproject.vinsky.app.backend;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class XmlParserBean {

    /** channelタグ以下格納用 */
    private HashMap<String, String> channels;

    /** itemタグ以下格納用 */
    private List<HashMap<String, String>> articles;

    public HashMap<String, String> getChannels() {
        return channels;
    }

    public void setChannels(HashMap<String, String> channels) {
        this.channels = channels;
    }

    public List<HashMap<String, String>> getArticles() {
        return articles;
    }

    public void setArticles(List<HashMap<String, String>> articles) {
        this.articles = articles;
    }
}