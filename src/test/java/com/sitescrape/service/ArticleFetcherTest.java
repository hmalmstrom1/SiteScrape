package com.sitescrape.service;

import com.sitescrape.model.Article;
import com.sitescrape.model.SiteDefinition;
import com.sitescrape.reader.TechCrunchPageReader;
import com.sitescrape.writer.CSVPageWriter;
import com.sitescrape.writer.PageWriter;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.verify;

public class ArticleFetcherTest {
    private TechCrunchPageReader pageReader;
    private PageWriter pageWriter;
    private ArticleFetcher fetcherToTest;

    @Test
    public void testGetArticles() throws Exception {
        pageReader = Mockito.mock(TechCrunchPageReader.class);
        pageWriter = Mockito.mock(CSVPageWriter.class);
        fetcherToTest = new ArticleFetcher(pageReader, pageWriter);
        fetcherToTest.getArticles(fetcherToTest.getDefaultSiteDefinition());
        verify(pageReader).fetchArticles(any(SiteDefinition.class));
    }

    @Test
    public void testWriteCsvArticles() throws IOException {
        pageReader = Mockito.mock(TechCrunchPageReader.class);
        pageWriter = Mockito.mock(CSVPageWriter.class);
        fetcherToTest = new ArticleFetcher(pageReader, pageWriter);
        fetcherToTest.writeCsvArticles(anyListOf(Article.class), anyString());
        verify(pageWriter).writeArticles(anyListOf(Article.class), anyString());
    }


    @Test
    public void testGetDefaultSiteDefinition() {
        // just insure we get something to default to...
        pageReader = new TechCrunchPageReader();
        pageWriter = Mockito.mock(CSVPageWriter.class);
        fetcherToTest = new ArticleFetcher(pageReader, pageWriter);

        SiteDefinition sd = fetcherToTest.getDefaultSiteDefinition();
        assertNotNull(sd);
        assertNotNull(sd.getArticleCssSelector());
        assertNotNull(sd.getBusinessAttribute());
        assertNotNull(sd.getBusinessWebsiteAttribute());
        assertNotNull(sd.getLinkAttribute());
        assertNotNull(sd.getTitleAttribute());
        assertNotNull(sd.getUrl());
    }

}
