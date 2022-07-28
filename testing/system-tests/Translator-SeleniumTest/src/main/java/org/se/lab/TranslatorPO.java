package org.se.lab;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class TranslatorPO
{
    private WebDriver driver;

    public TranslatorPO(WebDriver driver)
    {
        this.driver = driver;
    }

    private String word;
    public String getWord()
    {
        return word;
    }
    public void setWord(String word)
    {
        this.word = word;
    }

    private Language lang;
    public Language getLang()
    {
        return lang;
    }
    public void setLang(Language lang)
    {
        this.lang = lang;
    }

    public ResultPO translate()
    {
        driver.get("http://localhost:8080/index.html");
        driver.findElement(By.name("word")).click();
        driver.findElement(By.name("word")).sendKeys("cat");
        driver.findElement(By.name("language")).click();

        if(lang == Language.DEUTSCH)
            driver.findElement(By.cssSelector("option:nth-child(1)")).click();
        else
            driver.findElement(By.cssSelector("option:nth-child(2)")).click();

        driver.findElement(By.cssSelector("th:nth-child(3) > input")).click();
        return new ResultPO(driver);
    }
}
