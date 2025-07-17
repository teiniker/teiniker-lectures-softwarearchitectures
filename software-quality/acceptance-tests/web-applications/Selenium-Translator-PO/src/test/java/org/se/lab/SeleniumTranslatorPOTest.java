package org.se.lab;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class SeleniumTranslatorPOTest
{
    private WebDriver driver;

    @Before
    public void setUp()
    {
        driver = new FirefoxDriver();
    }

    @After
    public void tearDown()
    {
        driver.quit();
    }

    @Test
    public void testCatFrench()
    {
        TranslatorPO translator = new TranslatorPO(driver);
        translator.word = "cat";
        translator.language = Language.FRENCH;
        ResultPO result = translator.translate();

        Assert.assertEquals("Translate: cat into Chatte", result.message);
    }

    @Test
    public void testCatGerman()
    {
        TranslatorPO translator = new TranslatorPO(driver);
        translator.word = "cat";
        translator.language = Language.GERMAN;
        ResultPO result = translator.translate();

        Assert.assertEquals("Translate: cat into Katze", result.message);
    }
}
