package com.sitescrape.model;

/**
 * Defines the configuration of a site so we know how to parse it correctly.
 */
public class SiteDefinition {
    private String url;
    private String articleCssSelector;
    private String titleAttribute;
    private String linkAttribute;
    private String businessAttribute;
    private String businessWebsiteAttribute;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getArticleCssSelector() {
        return articleCssSelector;
    }

    public void setArticleCssSelector(String articleCssSelector) {
        this.articleCssSelector = articleCssSelector;
    }

    public String getTitleAttribute() {
        return titleAttribute;
    }

    public void setTitleAttribute(String titleAttribute) {
        this.titleAttribute = titleAttribute;
    }

    public String getLinkAttribute() {
        return linkAttribute;
    }

    public void setLinkAttribute(String linkAttribute) {
        this.linkAttribute = linkAttribute;
    }

    public String getBusinessAttribute() { return businessAttribute; }

    public void setBusinessAttribute(String businessAttribute) { this.businessAttribute = businessAttribute; }

    public String getBusinessWebsiteAttribute() { return businessWebsiteAttribute; }

    public void setBusinessWebsiteAttribute(String businessWebsiteAttribute) { this.businessWebsiteAttribute = businessWebsiteAttribute; }
}
