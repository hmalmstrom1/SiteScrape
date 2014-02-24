package com.sitescrape.cli;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.sitescrape.model.SiteDefinition;
import com.sitescrape.modules.SiteScrapingModule;
import com.sitescrape.service.ArticleFetcher;
import org.apache.commons.cli.*;

import java.io.IOException;


/**
 * Command Line interface for fetching articles and determining the company if any.
 *
 * Outputs a csv file in the form of:
 *      company name,company website,article title,article url
 */
public class Main {

    public static void main(String[] args) throws ParseException, IOException {
        new Main().realMain(args);
    }

    private void realMain(String[] args) throws ParseException, IOException {
        Options options = getOptions();
        CommandLineParser parser = new PosixParser();
        CommandLine cmd = parser.parse(options, args);
        if (cmd.hasOption("help")) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("scraper", options);
            System.exit(1);
        }

        Injector injector = Guice.createInjector(new SiteScrapingModule());
        ArticleFetcher articleFetcher = injector.getInstance(ArticleFetcher.class);

        scrape(cmd, articleFetcher);
    }


    @SuppressWarnings("static-access")
    public static Options getOptions() {
        Options opts = new Options();

        Option help = new Option("help", "print usage");
        Option url = OptionBuilder.withArgName("url").hasArg().withDescription("The URL to scrape for articles").create("url");
        Option file = OptionBuilder.withArgName("file").hasArg().withDescription("The file to write the articles to.").create("file");
        Option articleSelector = OptionBuilder.withArgName("articleSelector").hasArg().withDescription("Overrides the default article css selector for matching the river of articles").create("aselect");
        Option articleLinkSelector = OptionBuilder.withArgName("articleLinkSelector").hasArg().withDescription("Overrides the default article link selector for matching links to articles").create("lselect");
        Option titleSelector = OptionBuilder.withArgName("titleSelector").hasArg().withDescription("Overrides the default article title selector for matching the article titles").create("tselect");

        opts.addOption(help);
        opts.addOption(url);
        opts.addOption(file);
        opts.addOption(articleLinkSelector);
        opts.addOption(articleSelector);
        opts.addOption(titleSelector);

        return opts;
    }


    /**
     * Do the post-injection / command-line processing work.
     *
     * @param cmd     The command-line arguments used to execute this program
     * @param articleFetcher   Utility Service for top level reading / writing of articles.
     * @throws IOException
     */
    public void scrape(CommandLine cmd, ArticleFetcher articleFetcher) throws IOException {
        SiteDefinition siteDef = getSiteDefinition(articleFetcher.getDefaultSiteDefinition(), cmd);
        articleFetcher.fetchAndWriteArticles(siteDef, cmd.getOptionValue("file"));
    }


    /**
     * Apply command-line argument options against a default site definition
     * in order to build our real site definition for this invocation.
     *
     * @param sd    The default site definition to be used when commands are not specified
     * @param cmd   The command-line arguments used to execute this program
     * @return  The resolved SiteDefinition for this invocation of the program
     */
    public SiteDefinition getSiteDefinition(SiteDefinition sd, CommandLine cmd) {
        // If an injected provider returns a null site definition this is invalid.
        if (sd == null) {
            System.err.println("No default site definition was found");
            System.exit(1);
        }

        SiteDefinition siteDefinition = new SiteDefinition();
        siteDefinition.setUrl((cmd.hasOption("url")) ? cmd.getOptionValue("url") : sd.getUrl());
        siteDefinition.setArticleCssSelector(cmd.hasOption("aselect") ? cmd.getOptionValue("aselect") : sd.getArticleCssSelector());
        siteDefinition.setLinkAttribute(cmd.hasOption("lselect") ? cmd.getOptionValue("lselect") : sd.getLinkAttribute());
        siteDefinition.setTitleAttribute(cmd.hasOption("tselect") ? cmd.getOptionValue("tselect") : sd.getTitleAttribute());
        // TODO: make these additional CLI options later.
        siteDefinition.setBusinessAttribute(sd.getBusinessAttribute());
        siteDefinition.setBusinessWebsiteAttribute(sd.getBusinessWebsiteAttribute());

        return siteDefinition;
    }

}
