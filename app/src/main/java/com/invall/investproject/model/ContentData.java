package com.invall.investproject.model;

public class ContentData {
    private String contentKey;
    private String cardimage;
    private String cardtitle;
    private String cardtext;
    private String fulltext;
    private String fullpic;

    public ContentData(String contentKey, String cardimage, String cardtitle, String cardtext,
                       String fulltext, String fullpic) {
        this.contentKey = contentKey;
        this.cardimage = cardimage;
        this.cardtitle = cardtitle;
        this.cardtext = cardtext;
        this.fulltext = fulltext;
        this.fullpic = fullpic;
    }

    public String getCardimage() {
        return cardimage;
    }

    public void setCardimage(String cardimage) {
        this.cardimage = cardimage;
    }

    public String getCardtitle() {
        return cardtitle;
    }

    public void setCardtitle(String cardtitle) {
        this.cardtitle = cardtitle;
    }

    public String getCardtext() {
        return cardtext;
    }

    public void setCardtext(String cardtext) {
        this.cardtext = cardtext;
    }

    public String getFulltext() {
        return fulltext;
    }

    public void setFulltext(String fulltext) {
        this.fulltext = fulltext;
    }

    public String getFullpic() {
        return fullpic;
    }

    public void setFullpic(String fullpic) {
        this.fullpic = fullpic;
    }

    public String getContentKey() {
        return contentKey;
    }

    public void setContentKey(String contentKey) {
        this.contentKey = contentKey;
    }

}
