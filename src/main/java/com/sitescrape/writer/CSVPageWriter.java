package com.sitescrape.writer;

import au.com.bytecode.opencsv.CSVWriter;
import com.sitescrape.model.Article;
import org.apache.commons.lang.StringUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Used to write articles from the website to a csv file format.
 */
public class CSVPageWriter implements PageWriter {
    private static final String fallbackFilename = "sitescraper_%s.csv";

    @Override
    public void writeArticles(List<Article> articles, String file) throws IOException {
        if (articles == null) {
            articles = new ArrayList<Article>();
        }
        file = (StringUtils.isEmpty(file)) ? getFallbackFilename() : file;
        CSVWriter writer = new CSVWriter(new FileWriter(file));

        for (Article article : articles) {
            writer.writeNext(article.toArray());
        }

        writer.close();
    }

    private String getFallbackFilename() {
         return String.format(fallbackFilename,
                 new SimpleDateFormat("MM-dd-yyyy-HH:mm:ss").format(Calendar.getInstance().getTime()));
    }
}
