package com.example.karthiksingh.bookworld;

/**
 * Created by karthiksingh on 2017-10-04.
 */

public class Book {
    private String mtitle;
    private String msubTitle;
    private String mauthors;
    private String mpublishedDate;
    private String mDescription;
    private int mpageCount;
    private String msmallImage;
    private String mimage;
    private String mpreviewLink;
    private Boolean mpdfisAvailable;
    private String mdownloadLink;
    private String mwebReaderurl;

    public Book(String mtitle, String msubTitle, String mauthors, String mpublishedDate, String mDescription, int mpageCount, String msmallImage, String mimage, String mpreviewLink, Boolean mpdfisAvailable, String mdownloadLink, String mwebReaderurl) {
        this.mtitle = mtitle;
        this.msubTitle = msubTitle;
        this.mauthors = mauthors;
        this.mpublishedDate = mpublishedDate;
        this.mDescription = mDescription;
        this.mpageCount = mpageCount;
        this.msmallImage = msmallImage;
        this.mimage = mimage;
        this.mpreviewLink = mpreviewLink;
        this.mpdfisAvailable = mpdfisAvailable;
        this.mdownloadLink = mdownloadLink;
        this.mwebReaderurl = mwebReaderurl;
    }

    public String gettitle() {
        return mtitle;
    }

    public String getsubTitle() {
        return msubTitle;
    }

    public String getauthors() {
        return mauthors;
    }

    public String getpublishedDate() {
        return mpublishedDate;
    }

    public String getDescription() {
        return mDescription;
    }

    public int getpageCount() {
        return mpageCount;
    }

    public String getsmallImage() {
        return msmallImage;
    }

    public String getimage() {
        return mimage;
    }

    public String getpreviewLink() {
        return mpreviewLink;
    }

    public Boolean getpdfisAvailable() {
        return mpdfisAvailable;
    }

    public String getdownloadLink() {
        return mdownloadLink;
    }

    public String getwebReaderurl() {
        return mwebReaderurl;
    }

}
