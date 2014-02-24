package com.sitescrape.service;

import com.google.inject.Inject;
import com.sitescrape.model.Article;
import com.sitescrape.model.SiteDefinition;
import com.sitescrape.reader.PageReader;
import com.sitescrape.writer.PageWriter;

import java.io.IOException;
import java.util.List;

/**
 * Fetches articles
 */
public class ArticleFetcher {
    private final PageReader pageReader;
    private final PageWriter pageWriter;

    @Inject
    ArticleFetcher(PageReader pageReader, PageWriter pageWriter) {
        this.pageReader = pageReader;
        this.pageWriter = pageWriter;
    }

    /**
     * Fetch all articles from the site and write them out.
     *
     * @param siteDefinition  The settings to use for the site.
     * @param filename   The file to write the output to.
     * @throws IOException
     */
    public void fetchAndWriteArticles(SiteDefinition siteDefinition, String filename) throws IOException {
        List<Article> articles = getArticles(siteDefinition);
        writeCsvArticles(articles, filename);
    }

    /**
     * Get all articles for the site by it's siteDefinition.
     *
     * @param siteDefinition
     * @return
     * @throws IOException
     */
    public List<Article> getArticles(SiteDefinition siteDefinition) throws IOException {
        return pageReader.fetchArticles(siteDefinition);
    }


    public SiteDefinition getDefaultSiteDefinition() {
        return pageReader.getDefaultSiteDefinition();
    }

    /**
     * Write all the articles with the injected pageWriter
     *
     * @param articles
     * @throws IOException
     */
    public void writeCsvArticles(List<Article> articles, String file) throws IOException {
        pageWriter.writeArticles(articles, file);
    }
}
