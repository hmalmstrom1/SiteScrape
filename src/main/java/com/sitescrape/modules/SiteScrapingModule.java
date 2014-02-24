/*
 * Created by IntelliJ IDEA.
 * User: heathm
 * Date: 2/20/14
 * Time: 9:56 PM
 */
package com.sitescrape.modules;

import com.google.inject.AbstractModule;
import com.sitescrape.reader.PageReader;
import com.sitescrape.reader.TechCrunchPageReader;
import com.sitescrape.writer.CSVPageWriter;
import com.sitescrape.writer.PageWriter;

public class SiteScrapingModule extends AbstractModule {
    protected void configure() {
//add configuration logic here
        bind(PageReader.class).to(TechCrunchPageReader.class);
        bind(PageWriter.class).to(CSVPageWriter.class);
    }
}
