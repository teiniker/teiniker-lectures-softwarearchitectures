package org.se.lab;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class TranslatorSeleniumTest
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
        driver.get("http://localhost:8080/index.html");
        driver.findElement(By.name("word")).click();
        driver.findElement(By.name("word")).sendKeys("cat");
        driver.findElement(By.name("language")).click();
        driver.findElement(By.cssSelector("option:nth-child(2)")).click();
        driver.findElement(By.cssSelector("th:nth-child(3) > input")).click();
        driver.findElement(By.cssSelector("p:nth-child(1)")).click();
        assertThat(driver.findElement(By.cssSelector("p:nth-child(1)")).getText(), is("Translate: cat into Chatte"));
        driver.findElement(By.linkText("back")).click();
    }

    @Test
    public void testCatGerman()
    {
        driver.get("http://localhost:8080/index.html");
        driver.findElement(By.name("word")).click();
        driver.findElement(By.name("word")).sendKeys("cat");
        driver.findElement(By.name("language")).click();
        driver.findElement(By.cssSelector("option:nth-child(2)")).click();
        driver.findElement(By.cssSelector("th:nth-child(3) > input")).click();
        driver.findElement(By.cssSelector("p:nth-child(1)")).click();
        assertThat(driver.findElement(By.cssSelector("p:nth-child(1)")).getText(), is("Translate: cat into Chatte"));
        driver.findElement(By.linkText("back")).click();
    }
}
