package com.sitescrape.model;

/**
 * Represents a news / blog article
 */
public class Article {
    private String articleTitle;
    private String articleURL;
    private String companyName;
    private String companyWebSite;

    public Article() { }

    public Article(String articleTitle, String articleURL, String companyName, String companyWebSite) {
        this.articleTitle = articleTitle;
        this.articleURL = articleURL;
        this.companyName = companyName;
        this.companyWebSite = companyWebSite;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public String getArticleURL() {
        return articleURL;
    }

    public void setArticleURL(String articleURL) {
        this.articleURL = articleURL;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyWebSite() {
        return companyWebSite;
    }

    public void setCompanyWebSite(String companyWebSite) {
        this.companyWebSite = companyWebSite;
    }

    public String[] toArray() {
        return new String[]{ companyName, companyWebSite, articleTitle, articleURL };
    }
}
