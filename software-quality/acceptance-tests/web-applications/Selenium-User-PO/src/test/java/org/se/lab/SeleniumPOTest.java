package org.se.lab;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;


public class SeleniumPOTest
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
    public void testAddUserHomer()
    {
        AddUserPO addUser = new AddUserPO(driver);
        addUser.id = 7;
        addUser.username = "homer";
        addUser.password = "duffbeer";
        ResultPO result = addUser.addUser();

        Assert.assertEquals("7: homer / duffbeer", result.message);
    }

    @Test
    public void testAddUserMarge()
    {
        AddUserPO addUser = new AddUserPO(driver);
        addUser.id = 8;
        addUser.username = "marge";
        addUser.password = "bluehair";
        ResultPO result = addUser.addUser();

        Assert.assertEquals("8: marge / bluehair", result.message);
    }
}
