SiteScrape
==========

A command-line tool to scrape TechCrunch.com for article title,article url,company name,company website.
When the company name or url cannot be determined "n/a" will be specified.
A csv file will be generated listing each articles information in the order listed above.

building
--------
To build a jar with dependencies issue the following:
> mvn install assembly:assembly

Command-line Usage
------------------

Issue the following java command:
> java -jar target/SiteScrape-1.0-SNAPSHOT-jar-with-dependencies.jar com.sitescraper.cli.Main -file test1.csv


