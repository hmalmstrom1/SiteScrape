package com.sitescrape.validators;

import com.sitescrape.model.SiteDefinition;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.routines.UrlValidator;

/**
 * Validator for site configuration defined in SiteDefinition instances.
 */
public class SiteDefinitionValidator {

    /**
     * Checks to insure a Site Definition is valid
     *
     * @param siteDefinition  Options for this invocation to configure site scraping
     * @return true if valid, otherwise false
     */
    public static boolean isValid(SiteDefinition siteDefinition) {
        if (siteDefinition == null) {
            return false;
        }
        if ( StringUtils.isEmpty(siteDefinition.getArticleCssSelector()) ||
                StringUtils.isEmpty(siteDefinition.getUrl()) ||
                StringUtils.isEmpty(siteDefinition.getLinkAttribute()) ||
                StringUtils.isEmpty(siteDefinition.getTitleAttribute()) ||
                StringUtils.isEmpty(siteDefinition.getBusinessAttribute()) ||
                StringUtils.isEmpty(siteDefinition.getBusinessWebsiteAttribute())) {
            return false;
        }

        // the site URL needs to be a valid http URL
        UrlValidator urlValidator = new UrlValidator(new String[]{"http"});
        return (urlValidator.isValid(siteDefinition.getUrl()));
    }


}
