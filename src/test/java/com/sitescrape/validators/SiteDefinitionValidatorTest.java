package com.sitescrape.validators;

import com.sitescrape.model.SiteDefinition;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class SiteDefinitionValidatorTest {

    @Test
    public void testIsValidPossitive() throws Exception {
        SiteDefinition sd = getValidSiteDefinition();
        assertTrue(SiteDefinitionValidator.isValid(sd));
    }

    @Test
    public void testIsValidBadURL() throws Exception {
        SiteDefinition sd = getValidSiteDefinition();
        sd.setUrl(null);
        assertFalse(SiteDefinitionValidator.isValid(sd));

        sd.setUrl("");
        assertFalse(SiteDefinitionValidator.isValid(sd));

        sd.setUrl("fdjkdlajf");
        assertFalse(SiteDefinitionValidator.isValid(sd));
    }

    @Test
    public void testIsValidBadLinkAttribute() throws Exception {
        SiteDefinition sd = getValidSiteDefinition();
        sd.setLinkAttribute(null);
        assertFalse(SiteDefinitionValidator.isValid(sd));

        sd.setLinkAttribute("");
        assertFalse(SiteDefinitionValidator.isValid(sd));
    }

    @Test
    public void testIsValidBadTitleAttribute() throws Exception {
        SiteDefinition sd = getValidSiteDefinition();
        sd.setTitleAttribute(null);
        assertFalse(SiteDefinitionValidator.isValid(sd));

        sd.setTitleAttribute("");
        assertFalse(SiteDefinitionValidator.isValid(sd));
    }

    @Test
    public void testIsValidBadArticleCSSSelector() throws Exception {
        SiteDefinition sd = getValidSiteDefinition();
        sd.setArticleCssSelector(null);
        assertFalse(SiteDefinitionValidator.isValid(sd));

        sd.setArticleCssSelector("");
        assertFalse(SiteDefinitionValidator.isValid(sd));
    }

    @Test
    public void testIsValidBadBusinessAttribute() throws Exception {
        SiteDefinition sd = getValidSiteDefinition();
        sd.setBusinessAttribute(null);
        assertFalse(SiteDefinitionValidator.isValid(sd));

        sd.setBusinessAttribute("");
        assertFalse(SiteDefinitionValidator.isValid(sd));
    }

    @Test
    public void testIsValidBadBusinessWebsiteAttribute() throws Exception {
        SiteDefinition sd = getValidSiteDefinition();
        sd.setBusinessWebsiteAttribute(null);
        assertFalse(SiteDefinitionValidator.isValid(sd));

        sd.setBusinessWebsiteAttribute("");
        assertFalse(SiteDefinitionValidator.isValid(sd));
    }


    private SiteDefinition getValidSiteDefinition() {
        SiteDefinition sd = new SiteDefinition();
        sd.setUrl("http://techcrunch.com");
        sd.setTitleAttribute("a[data-omni-sm=gbl_river_headline]");
        sd.setLinkAttribute("a[data-omni-sm=gbl_river_headline]");
        sd.setBusinessAttribute(".card-title");
        sd.setBusinessWebsiteAttribute(".card-info");
        sd.setArticleCssSelector(".river-block");
        return sd;
    }



}
