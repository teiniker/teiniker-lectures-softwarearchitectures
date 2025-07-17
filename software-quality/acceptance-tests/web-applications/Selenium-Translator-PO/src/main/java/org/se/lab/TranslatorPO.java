package org.se.lab;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class TranslatorPO
{
    private WebDriver driver;

    public TranslatorPO(WebDriver driver)
    {
        this.driver = driver;
        driver.get("http://localhost:8080/index.html");

    }

    // Input and output fields
    String word;
    Language language;

    public ResultPO translate()
    {
        driver.findElement(By.name("word")).click();
        driver.findElement(By.name("word")).sendKeys("cat");
        driver.findElement(By.name("language")).click();
        switch(language)
        {
            case FRENCH:
                driver.findElement(By.cssSelector("option:nth-child(2)")).click();
                break;
            case GERMAN:
                driver.findElement(By.cssSelector("option:nth-child(1)")).click();
                break;
            default:
                throw new IllegalArgumentException("Unsupported language: " + language);
        }
        driver.findElement(By.cssSelector("th:nth-child(3) > input")).click();
        driver.findElement(By.cssSelector("p:nth-child(1)")).click();

        return new ResultPO(driver);
    }
}
