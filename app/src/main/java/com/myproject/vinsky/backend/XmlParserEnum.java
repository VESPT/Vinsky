package com.myproject.vinsky.backend;

public enum XmlParserEnum {

    /** ?xml */
    XML("?xml"),
    /** version */
    VERSION("version"),
    /** encoding */
    ENCODING("encoding"),
    /** channel */
    CHANNEL("channel"),
    /** rdf:about(サイトのRSSのURL or 記事xのURL) */
    RDF_ABOUT("df:about"),
    /** rdf:li */
    RDF_LI("rdf:li"),
    /** dc:date(作成日時) */
    DC_DATE("dc:date"),
    /** dc:language */
    DC_LANGUAGE("dc:language"),
    /** item */
    ITEM("item"),
    /** title */
    TITLE("title"),
    /** link */
    LINK("link"),
    /** description */
    DESCRIPTION("description");

	/** タグ名 */
    private String name;

    /** Constructor */
    XmlParserEnum(String name) {
        this.name = name;
    }
	
    public String getName() {
        return this.name;
    }
}
