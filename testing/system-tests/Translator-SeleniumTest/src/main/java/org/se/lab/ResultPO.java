package org.se.lab;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ResultPO
{
    private WebDriver driver;

    public ResultPO(WebDriver driver)
    {
        this.driver = driver;
        driver.findElement(By.cssSelector("p:nth-child(1)")).click();
        text = driver.findElement(By.cssSelector("p:nth-child(1)")).getText();
    }

    private String text;
    public String getText()
    {
        return text;
    }

    public TranslatorPO back()
    {
        driver.findElement(By.linkText("back")).click();
        return new TranslatorPO(driver);
    }
}
