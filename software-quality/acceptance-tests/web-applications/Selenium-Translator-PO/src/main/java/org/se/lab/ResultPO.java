package org.se.lab;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ResultPO
{
    private WebDriver driver;

    // Input and output fields
    String message;

    public ResultPO(WebDriver driver)
    {
        this.driver = driver;
        message = driver.findElement(By.cssSelector("p:nth-child(1)")).getText();
    }

    public TranslatorPO back()
    {
        driver.findElement(By.linkText("back")).click();
        return new TranslatorPO(driver);
    }
}
