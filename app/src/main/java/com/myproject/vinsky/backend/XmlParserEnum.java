package com.myproject.vinsky.backend;

public enum XmlParserEnum {

    // title
    TITLE("title"),
    // description
    DESCRIPTION("description");

    private String label;

    /** コンストラクタ */
    XmlParserEnum(String label) {
        this.label = label;
    }

    public String getLabel() {
        return this.label;
    }
}
