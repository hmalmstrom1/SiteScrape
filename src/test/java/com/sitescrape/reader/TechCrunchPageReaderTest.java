package com.sitescrape.reader;

import com.sitescrape.model.Article;
import com.sitescrape.model.SiteDefinition;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class TechCrunchPageReaderTest {
    private static final String testSiteHTML = "./src/test/resources/TechCrunchStatic.html";
    private static final String testArticleHTML = "./src/test/resources/TechCrunchArticleStatic.html";
    private static final String testArticleCompanyHTML = "./src/test/resources/TechCrunchArticleStaticWithCompany.html";


    @Test
    public void testFetchArticles() throws Exception {
        TechCrunchPageReader reader = new TechCrunchPageReader();
        SiteDefinition sd = reader.getDefaultSiteDefinition();

        List<Article> articles = reader.fetchArticles(sd);
        assertNotNull(articles);
        assertTrue(articles.size() > 0);
    }


    @Test(expected=IllegalArgumentException.class)
    public void testFetchArticlesWithIllegalArgumentException() throws Exception {
        TechCrunchPageReader reader = new TechCrunchPageReader();
        SiteDefinition sd = reader.getDefaultSiteDefinition();
        sd.setUrl("blahblah"); // invalid url
        reader.fetchArticles(sd);
    }


    // live functional test so uncomment below and run as it can break due to networking.
    //  @Test
    public void testFetchArticlesWithNullSD() throws Exception {
        TechCrunchPageReader reader = new TechCrunchPageReader();
        List<Article> articles = reader.fetchArticles(null);
        assertNotNull(articles);
        assertTrue(articles.size() > 0);
        int companyFoundCount = 0;
        int companyWebsitesFound = 0;
        for (Article article : articles) {
           assertTrue(StringUtils.isNotEmpty(article.getArticleTitle()));
           assertTrue(StringUtils.isNotEmpty(article.getArticleURL()));
           if (!"n/a".equalsIgnoreCase(article.getCompanyName())) {
               companyFoundCount++;
           }
           if (!"n/a".equalsIgnoreCase(article.getCompanyWebSite())) {
               companyWebsitesFound++;
           }
        }
        // NOTE: possible to fail the following two assertions and still be right,
        // but it's statistically unlikely enough to check when it does as it aids
        // in seeing if we can find companies.
        assertTrue(companyFoundCount > 1);
        assertTrue(companyWebsitesFound > 1);
    }

    @Test
    public void testProcessArticleLinkNoBusiness() throws Exception {
        TechCrunchPageReader reader = mock(TechCrunchPageReader.class);
        when(reader.getSiteDocument(anyString())).thenReturn(getDummyArticleDocument());
        when(reader.processArticleLink(any(Element.class), any(SiteDefinition.class))).thenCallRealMethod();
        SiteDefinition sd = TechCrunchPageReader.getSiteDefinitionConstants();
        Document doc = getDummySiteDocument();

        Element riverArticle = doc.select(sd.getArticleCssSelector()).first();
        Article testArticle = reader.processArticleLink(riverArticle, sd);

        assertNotNull(testArticle);
        assertTrue(testArticle.getArticleTitle().contains("How To Cope with Your Insane Jealousy Of The WhatsApp Deal"));
        assertTrue("http://techcrunch.com/2014/02/22/how-to-cope-with-your-insane-jealousy-of-the-whatsapp-deal/".equals(testArticle.getArticleURL()));
        assertTrue("n/a".equalsIgnoreCase(testArticle.getCompanyName()));
        assertTrue("n/a".equalsIgnoreCase(testArticle.getCompanyWebSite()));
    }


    @Test
    public void testProcessArticleLinkBusiness() throws Exception {
        TechCrunchPageReader reader1 = mock(TechCrunchPageReader.class);
        when(reader1.getSiteDocument(anyString())).thenReturn(getDummyArticleWithCompanyDocument());
        when(reader1.processArticleLink(any(Element.class), any(SiteDefinition.class))).thenCallRealMethod();
        SiteDefinition sd = TechCrunchPageReader.getSiteDefinitionConstants();
        Document doc = getDummySiteDocument();

        Element riverArticle = doc.select(sd.getArticleCssSelector()).first();
        Article testArticle = reader1.processArticleLink(riverArticle, sd);

        assertNotNull(testArticle);
        assertTrue(testArticle.getArticleTitle().contains("How To Cope with Your Insane Jealousy Of The WhatsApp Deal"));
        assertTrue("http://techcrunch.com/2014/02/22/how-to-cope-with-your-insane-jealousy-of-the-whatsapp-deal/".equals(testArticle.getArticleURL()));
        assertTrue("Android".equals(testArticle.getCompanyName()));
        assertTrue("http://www.android.com".equals(testArticle.getCompanyWebSite()));
    }

    private Document getDummyArticleDocument() throws IOException {
        return Jsoup.parse(new File(testArticleHTML), "UTF-8");
    }

    private Document getDummyArticleWithCompanyDocument() throws IOException {
        return Jsoup.parse(new File(testArticleCompanyHTML), "UTF-8");
    }

    private Document getDummySiteDocument() throws IOException {
        return Jsoup.parse(new File(testSiteHTML), "UTF-8");
    }

}
