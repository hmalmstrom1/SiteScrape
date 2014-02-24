package com.sitescrape.writer;

import au.com.bytecode.opencsv.CSVReader;
import com.sitescrape.model.Article;
import org.junit.Test;

import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


public class CSVPageWriterTest {

    @Test
    public void testWriteArticlesWithNullArticles() throws Exception {
        CSVPageWriter pageWriter = new CSVPageWriter();
        String file = "test-1.csv";
        pageWriter.writeArticles(null, file);

        File f = new File(file);
        if (f.exists()) { f.deleteOnExit(); }
        assertTrue(f.exists() && f.isFile() && f.length() == 0);
    }

    @Test
    public void testWriteArticlesWithEmptyArticles() throws Exception {
        CSVPageWriter pageWriter = new CSVPageWriter();
        List<Article> articles = new ArrayList<Article>();
        String file = "test-file.csv";

        pageWriter.writeArticles(articles, file);
        File f = new File(file);
        if (f.exists()) { f.deleteOnExit(); }
        assertTrue(f.exists() && f.isFile() && f.length() == 0);
    }

    @Test
    public void testWriteArticlesWithNullFile() throws Exception {
        CSVPageWriter pageWriter = new CSVPageWriter();
        List<Article> articles = new ArrayList<Article>();
        Article a2 = new Article("title2","url2","company2","site2");
        articles.add(a2);

        int preCount = countCSVFiles();
        pageWriter.writeArticles(articles, null);
        assertTrue(preCount < countCSVFiles());
    }

    private int countCSVFiles() {
        File dir = new File(".");
        File[] files = dir.listFiles(new FilenameFilter() {
           @Override
           public boolean accept(File dir, String name) {
               return name.matches(".*\\.csv");
           }
        });
        return files.length;
    }

    @Test
    public void testWriteArticlesGoodCase() throws Exception {
        CSVPageWriter pageWriter = new CSVPageWriter();
        String file = "test-articles.csv";
        List<Article> articles = new ArrayList<Article>();
        Article a1 = new Article();
        a1.setArticleTitle("title1");
        a1.setArticleURL("url1");
        a1.setCompanyName("company1");
        a1.setCompanyWebSite("site1");
        Article a2 = new Article("title2","url2","company2","site2");
        Article a3 = new Article("title3","url3","company3","site3");
        Article a4 = new Article("title4","url4","company4","site4");
        articles.add(a1);
        articles.add(a2);
        articles.add(a3);
        articles.add(a4);


        pageWriter.writeArticles(articles, file);
        File f = new File(file);
        assertTrue(f.exists() && f.isFile() && f.length() > 0);
        f.deleteOnExit();
        CSVReader csvReader = new CSVReader(new FileReader(file));
        String[] row = csvReader.readNext();
        assertNotNull(row);
        assertArrayEquals(row, new String[] {"company1","site1","title1","url1"});
        row = csvReader.readNext();
        assertNotNull(row);
        assertArrayEquals(row, new String[] {"company2","site2","title2","url2"});
        row = csvReader.readNext();
        assertNotNull(row);
        assertArrayEquals(row, new String[] {"company3","site3","title3","url3"});
        row = csvReader.readNext();
        assertNotNull(row);
        assertArrayEquals(row, new String[] {"company4","site4","title4","url4"});
        row = csvReader.readNext();
        assertNull(row);
        csvReader.close();
    }

}
