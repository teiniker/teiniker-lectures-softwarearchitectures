package org.se.lab;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.Assert;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class SeleniumTest
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
    public void addUserTest() 
    {
        driver.get("http://localhost:8080/");
        driver.findElement(By.id("id")).sendKeys("7");
        driver.findElement(By.id("username")).sendKeys("homer");
        driver.findElement(By.id("password")).sendKeys("duffbeer");
        driver.findElement(By.cssSelector("button")).click();
        driver.findElement(By.cssSelector("p:nth-child(1)")).click();
        Assert.assertEquals("7: homer / duffbeer", driver.findElement(By.cssSelector("p:nth-child(1)")).getText());
        driver.findElement(By.linkText("back")).click();
    }

}
