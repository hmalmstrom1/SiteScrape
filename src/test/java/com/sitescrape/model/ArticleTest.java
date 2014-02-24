package com.sitescrape.model;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

public class ArticleTest {
    @Test
    public void testToArray() throws Exception {
        // toArray should return in the following order:
        //  companyName, companyWebSite, articleTitle, articleURL
        Article a = new Article();
        a.setArticleTitle("articleTitle");
        a.setArticleURL("articleURL");
        a.setCompanyName("companyName");
        a.setCompanyWebSite("companyURL");

        assertArrayEquals(a.toArray(), new String[]{"companyName", "companyURL", "articleTitle", "articleURL"});
    }

}
