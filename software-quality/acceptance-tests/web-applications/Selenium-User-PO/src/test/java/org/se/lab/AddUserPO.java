package org.se.lab;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AddUserPO 
{
    private WebDriver driver;

    public AddUserPO(WebDriver driver)
    {
        this.driver = driver;
        driver.get("http://localhost:8080");
    }

    // Input fields
    int id;
    String username;
    String password;

    public ResultPO addUser()
    {
        driver.findElement(By.id("id")).sendKeys(String.valueOf(id));
        driver.findElement(By.id("username")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.cssSelector("button")).click();
        driver.findElement(By.cssSelector("p:nth-child(1)")).click();
        return new ResultPO(driver);
    }
    
}
