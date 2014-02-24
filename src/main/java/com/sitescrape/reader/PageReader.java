package com.sitescrape.reader;

import com.sitescrape.model.Article;
import com.sitescrape.model.SiteDefinition;

import java.io.IOException;
import java.util.List;


/**
 * Contract for external website readers
 */
public interface PageReader {
    public List<Article> fetchArticles(SiteDefinition siteDefinition) throws IOException;
    public SiteDefinition getDefaultSiteDefinition();
}
