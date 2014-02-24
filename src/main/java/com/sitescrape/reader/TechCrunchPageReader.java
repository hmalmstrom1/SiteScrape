package com.sitescrape.reader;

import com.sitescrape.model.Article;
import com.sitescrape.model.SiteDefinition;
import com.sitescrape.validators.SiteDefinitionValidator;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Fetches and Analyzes articles from TechCrunch for company and article information.
 * Provides key methods for population of Article objects.
 */
public class TechCrunchPageReader implements PageReader {
    private static final String URI = "http://techcrunch.com/";
    private static final String ARTICLE_RIVER_CLASS = ".river-block";
    private static final String ARTICLE_LINK_ATTR = "a[data-omni-sm=gbl_river_headline]";
    private static final String ARTICLE_TITLE_ATTR = "a[data-omni-sm=gbl_river_headline]";
    private static final String BUSINESS_CARD_TITLE_ATTR = ".card-title";
    private static final String BUSINESS_CARD_INFO_ATTR = ".card-info";
    private static final String NO_COMPANY = "n/a";
    private static final String NO_COMPANY_WEBSITE = "n/a";
    private SiteDefinition defaultSD;


    public TechCrunchPageReader() {
        this.defaultSD = getSiteDefinitionConstants();
    }

    public static SiteDefinition getSiteDefinitionConstants() {
        SiteDefinition defaultSD = new SiteDefinition();
        defaultSD.setUrl(URI);
        defaultSD.setTitleAttribute(ARTICLE_TITLE_ATTR);
        defaultSD.setLinkAttribute(ARTICLE_LINK_ATTR);
        defaultSD.setArticleCssSelector(ARTICLE_RIVER_CLASS);
        defaultSD.setBusinessAttribute(BUSINESS_CARD_TITLE_ATTR);
        defaultSD.setBusinessWebsiteAttribute(BUSINESS_CARD_INFO_ATTR);
        return defaultSD;
    }


    /**
     * Default site definition for a TechCrunch specific reader.
     * @return  our fallback options for this invocation.
     */
    @Override
    public SiteDefinition getDefaultSiteDefinition() {
        if (defaultSD == null) {
            defaultSD = getSiteDefinitionConstants();
        }
        return defaultSD;
    }

    /**
     * Fetch the articles from techcrunch website.
     *
     * @param siteDefinition  the settings to use for this site.
     * @return a list of articles from TechCrunch's article river.
     * @throws IOException  if we can't fetch the site
     * @throws IllegalArgumentException  if the site definition is invalid
     */
    @Override
    public List<Article> fetchArticles(SiteDefinition siteDefinition) throws IOException, IllegalArgumentException {
        siteDefinition = this.defaultEmptySettings(siteDefinition, this.defaultSD);
        if (!SiteDefinitionValidator.isValid(siteDefinition)) {
            throw new IllegalArgumentException("Invalid site definition!  Please check the URL and attribute settings for the site.");
        }

        Document doc = getSiteDocument(siteDefinition.getUrl());
        Elements articleLinks = doc.select(siteDefinition.getArticleCssSelector());
        List<Article> articles = new ArrayList<Article>(articleLinks.size());

        for (Element elem : articleLinks) {
            articles.add(processArticleLink(elem, siteDefinition));
        }

        return articles;
    }


    /**
     * Used to merge default values with specified values where the specified ones are not set.
     *
     * @param siteDefinition
     * @param defaults
     * @return
     * @throws IllegalArgumentException
     */
    public SiteDefinition defaultEmptySettings(SiteDefinition siteDefinition, SiteDefinition defaults) throws IllegalArgumentException {
        if (siteDefinition == null && defaults != null) {
            return defaults;
        }
        if (defaults == null) {
            throw new IllegalArgumentException("You must provide a non-null default object for setting defaults!");
        }

        if (StringUtils.isEmpty(siteDefinition.getUrl())) {
            siteDefinition.setUrl(defaults.getUrl());
        }

        if (StringUtils.isEmpty(siteDefinition.getArticleCssSelector())) {
            siteDefinition.setArticleCssSelector(defaults.getArticleCssSelector());
        }

        if (StringUtils.isEmpty(siteDefinition.getLinkAttribute())) {
            siteDefinition.setLinkAttribute(defaults.getLinkAttribute());
        }

        if (StringUtils.isEmpty(siteDefinition.getTitleAttribute())) {
            siteDefinition.setTitleAttribute(defaults.getTitleAttribute());
        }

        return siteDefinition;
    }


    /**
     * Get a Jsoup document for a URL.
     * This is mostly broken out for ease of testing.
     *
     * @param url
     * @return
     * @throws IOException
     */
    public Document getSiteDocument(String url) throws IOException {
        return Jsoup.connect(url).get();
    }


    /**
     * Create the article entry for the given article element.
     * This includes following the link to the article and grabbing
     * the business the article is about and the company website.
     *
     * @param element  An article in the river of site articles.
     * @param siteDefinition  The configuration for the site being scraped.
     *
     * @return An Article object representing the entry.
     */
    public Article processArticleLink(Element element, SiteDefinition siteDefinition) throws IOException {
        Article article = new Article();
        Element linkElem = element.select(siteDefinition.getLinkAttribute()).first();
        article.setArticleURL(linkElem.attr("href"));

        Element titleElem = element.select(siteDefinition.getTitleAttribute()).first();
        article.setArticleTitle(titleElem.text());

        populateArticleCompanyInformation(article, siteDefinition);
        return article;
    }

    /**
     * Grabs the company business card on the article page of TechCrunch
     * and gets the company name and website if available.
     *
     * @param article  The Article to populate.
     * @throws IOException when the site is unavailable
     */
    private void populateArticleCompanyInformation(Article article, SiteDefinition siteDefinition) throws IOException {
        article.setCompanyName(NO_COMPANY);
        article.setCompanyWebSite(NO_COMPANY_WEBSITE);

        if (StringUtils.isEmpty(article.getArticleURL())) {
            return;
        }

        Document doc = getSiteDocument(article.getArticleURL());
        Element bizElement = doc.select(siteDefinition.getBusinessAttribute()).first();
        if (bizElement == null) {
            return;
        }
        article.setCompanyName(bizElement.text());

        Element cardElement = doc.select(siteDefinition.getBusinessWebsiteAttribute()).first();
        Elements cardCells = cardElement.select("li");
        for (Element elem : cardCells) {
            if (elem.hasText() && elem.text().contains("Website")) {
                article.setCompanyWebSite(elem.text().substring(8)); // account for "Website " at the beginning
                break;
            }
        }
    }

}
