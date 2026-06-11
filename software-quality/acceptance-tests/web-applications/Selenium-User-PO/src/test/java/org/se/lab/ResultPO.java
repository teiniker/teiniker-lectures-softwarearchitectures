package org.se.lab;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ResultPO
{
    private WebDriver driver;

    // Output field
    String message;

    public ResultPO(WebDriver driver)
    {
        this.driver = driver;
        message = driver.findElement(By.cssSelector("p:nth-child(1)")).getText();
    }

    public AddUserPO back()
    {
        driver.findElement(By.linkText("back")).click();
        return new AddUserPO(driver);
    }
}
