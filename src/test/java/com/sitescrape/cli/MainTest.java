package com.sitescrape.cli;

import com.sitescrape.model.SiteDefinition;
import org.apache.commons.cli.*;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Testing we are getting the values for cli
 */
public class MainTest {
    private static final String DEFAULT_URL = "http://blah.com/";
    private static final String DEFAULT_ARTICLE = ".what";
    private static final String DEFAULT_LINK = ".huh";
    public static final String DEFAULT_TITLE = ".who";

    @Test
    public void testGetSiteDefinition() throws Exception {
        Main scraper = new Main();
        Options opts = Main.getOptions();

        final String fileStr = "testfile";
        final String urlStr = "http://url.com/";
        final String aselectorStr = ".aselect";
        final String lselectorStr = ".lselect";
        final String tselectorStr = ".tselect";
        // first check all options can override
        CommandLine cmd = getCommandLineFor(opts, new String[] { "-file", fileStr, "-url", urlStr, "-aselect", aselectorStr, "-lselect", lselectorStr, "-tselect", tselectorStr});
        assertTrue(fileStr.equals(cmd.getOptionValue("file")));

        SiteDefinition sd = scraper.getSiteDefinition(getDefaultSiteDef(), cmd);
        assertTrue(aselectorStr.equals(sd.getArticleCssSelector()));
        assertTrue(lselectorStr.equals(sd.getLinkAttribute()));
        assertTrue(tselectorStr.equals(sd.getTitleAttribute()));
        assertTrue(urlStr.equals(sd.getUrl()));

        // now test that defaults work with no arguments
        CommandLine cmd2 = getCommandLineFor(opts, new String[] {});

        SiteDefinition sd2 = scraper.getSiteDefinition(getDefaultSiteDef(), cmd2);
        assertTrue(DEFAULT_URL.equals(sd2.getUrl()));
        assertTrue(DEFAULT_ARTICLE.equals(sd2.getArticleCssSelector()));
        assertTrue(DEFAULT_LINK.equals(sd2.getLinkAttribute()));
        assertTrue(DEFAULT_TITLE.equals(sd2.getTitleAttribute()));
    }


    private SiteDefinition getDefaultSiteDef() {
        SiteDefinition defaultSiteDef = new SiteDefinition();
        defaultSiteDef.setUrl(DEFAULT_URL);
        defaultSiteDef.setArticleCssSelector(DEFAULT_ARTICLE);
        defaultSiteDef.setLinkAttribute(DEFAULT_LINK);
        defaultSiteDef.setTitleAttribute(DEFAULT_TITLE);

        return defaultSiteDef;
    }

    private CommandLine getCommandLineFor(Options opts, String[] args) throws ParseException {
        CommandLineParser parser = new PosixParser();
        return parser.parse(opts, args);
    }
}
