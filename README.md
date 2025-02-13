# Book House Web Automation Usiong Selenium(Java) with Allure Report
## Overview
This project automates the functionality of the BookHouse.com website using Java, Selenium WebDriver, and TestNG for test execution. The automation suite includes key operations such as login, searching for books, adding items to the cart, and completing checkout. All test results are reported using Allure, generating detailed and interactive reports for test analysis also created the report on surefire-report.

This project aims to ensure the website's smooth functionality by automating critical flows and validating the website’s behaviour under various scenarios based on only happy paths.

## Features
* **Automated Login** <br>
* **Search Functionalities** : Automated searching books based on the writer, title, genre, publishers etc. <br>
* **Shopping page Automated** : Simulates adding/removing books from the cart. <br>
* **Checkout Operation** : Automates the entire checkout process by following all steps like entering the number address delivery area delivery method etc. <br>
* **Automated Logout** <br>
* **Report Generate with Allure and Surefire** : Generates rich, visually appealing HTML reports of the test executions.

## Website
[Book House](https://bookhouse.com.bd/)

## Manual TestCase
[Test Case for Book House by Anik Chakraborty](https://docs.google.com/spreadsheets/d/1fprVjAqXRW_7NpZPudY8ja0rpoFY658I/edit?usp=sharing&ouid=102409645167363931924&rtpof=true&sd=true)  

## Pre-requisites
* Java (it's better to use the latest and stable version)
* Maven
* Manual Test Cases
* Testing Script(Manual Test cases converted into codes)
* Webdriver
* Allure Command Line
* Surefire command line for run in terminal
* IDE like IntelliJ IDEA, Eclipse, or Visual Studio Code

## Project Structure
````
**bookhouse-automation-pom/**  
|──**src/**  
│   ├── **main/**  
│   │   └── **java/**  
│   │       └── **com/**  
│   │           └── **bookhouse/**  
│   │             
│   └── **test/**  
│   |   └── **java/**  
│   |        └── **com/**  
│   |            └── **bookhouse/**  
│   │               ├── **pages/**                 **# Page Object Models (POM)**
|   |                    ├── **BasePage.java**     **# Base class with common setup** 
│   |                    ├── **HomePage.java**
|   |                    ├── **LoginPage.java**
|   |                    ├── **WriterPage.java**
|   |                    ├── **ShoppingCartPage.java**
|   |                    ├── **CheckOutPage.java**
│   |                    └── **LogoutPage.java**   
│   │               ├── **utils/**                 **# Helper/Utility classes**
|   |                    ├── **DataSet.java**
│   |                    └── **DriverSetup.java**  
|   |               └── **tests/**                 **# TestNG test cases**  
│                        ├── **TestHomePage.java**
|                        ├── **TestLoginPage.java**
|                        ├── **TestWriterPage.java**
|                        ├── **TestShoppingCartPage.java**
|                        ├── **TestCheckoutPage.java**
│                        └── **TestLogOutPage.java**   
├── **pom.xml**                                    **# Maven dependencies and build configuration**  
├── **allure-results/**                            **# Allure test execution results**
├── **Testng.xml/**                                **# Project Html Documentation to run all test**
└── **README.md**                                  **# Project documentation**  
````
## Installation
**1. Clone the Repository**
First, clone this repository to your local machine:
````
https://github.com/Anik16298/Book_house_web_automation_using_selenium_with_allure_report
````
**2. Set Up Java and Maven**
Ensure Java 8 or higher and Maven 3.x are installed on your machine.

Check your Java and Maven versions:
````
java -version
mvn -version
````
**3. WebDriver Setup**
Setup your webdriver properly.

**4. Add Dependecies in (pom.xml)**
- **selenium dependencies**
````

<!-- https://mvnrepository.com/artifact/org.seleniumhq.selenium/selenium-java -->
<dependency>
      <groupId>org.seleniumhq.selenium</groupId>
      <artifactId>selenium-java</artifactId>
      <version>4.25.0</version>
</dependency>
````
- **Testng dependencies**
````

<!-- https://mvnrepository.com/artifact/org.testng/testng -->
<dependency>
      <groupId>org.testng</groupId>
      <artifactId>testng</artifactId>
      <version>7.10.2</version>
      <scope>test</scope>
</dependency>
````
- **Allure dependencies**
````

<!-- https://mvnrepository.com/artifact/io.qameta.allure/allure-testng -->
<dependency>
      <groupId>io.qameta.allure</groupId>
      <artifactId>allure-testng</artifactId>
      <version>2.29.0</version>
</dependency>
````
**5. Maven Plugin**
````

<plugins>
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-surefire-plugin</artifactId>
        <version>3.5.2</version>
        <configuration>
          <suiteXmlFiles>
            <suiteXmlFile>testng.xml</suiteXmlFile>
          </suiteXmlFiles>
        </configuration>
      </plugin>
</plugins>
````
## Example Test Code
### Driver Setup
````

package Utility;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.time.Duration;

public class DriverSetup {

    private static String browserName = System.getProperty("browser", "firefox");
    private static final ThreadLocal<WebDriver> LOCAL_DRIVER = new ThreadLocal<>();

    public static void setDriver(WebDriver driver) {
        DriverSetup.LOCAL_DRIVER.set(driver);
    }

    public static WebDriver getDriver() {
        return LOCAL_DRIVER.get();
    }

    public WebDriver getBrowser(String browser_name) {
        if (browser_name.equalsIgnoreCase("chrome")) {
            return new ChromeDriver();
        } else if (browser_name.equalsIgnoreCase("firefox")) {
            return new FirefoxDriver();
        } else if (browser_name.equalsIgnoreCase("edge")) {
            return new EdgeDriver();
        }
        else {
            throw new RuntimeException("This Browser is not Available: " +browser_name);
        }
    }

    @BeforeMethod
    public void OpenABrowser(){
        WebDriver driver = getBrowser(browserName);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        setDriver(driver);
    }

    @AfterMethod
    public void CloseBrowser(){
        getDriver().quit();
    }
}
````
### BasePage Setup
````

package Pages;

import io.qameta.allure.Allure;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.ByteArrayInputStream;
import java.time.Duration;

import static Utility.DriverSetup.getDriver;

public class BasePage {

    public void loadWebPage(String url){
        getDriver().get(url);
    }

    public WebElement getElement(By locator){
        return getDriver().findElement(locator);
    }

    public void clickOnElement(By locator){
        getElement(locator).click();
    }

    public void writeOnElement(By locator, String text){
        getElement(locator).click();
        getElement(locator).clear();
        getElement(locator).sendKeys(text);
    }

    public String getPageUrl(){
        return getDriver().getCurrentUrl();
    }

    public String getPageTitle(){
        return getDriver().getTitle();
    }

    public Boolean is_element_visible(By locator){
        try {
            return getElement(locator).isDisplayed();
        }catch (Exception e){
            return false;
        }
    }

    public Boolean is_selected(By locator){
        try {
            return getElement(locator).isSelected();
        }catch (Exception e){
            return false;
        }
    }

    public Boolean is_enabled(By locator){
        try {
            return getElement(locator).isEnabled();
        }catch (Exception e){
            return false;
        }
    }

    public void GetText(By locator){
        getElement(locator).getText();
    }

    public void HoverElement(By locator){
        Actions actions = new Actions(getDriver());
        actions.moveToElement(getElement(locator)).build().perform();
        actions.click(getElement(locator)).build().perform();
    }

    public void ScrollElement(By locator){
        JavascriptExecutor scroll = (JavascriptExecutor) getDriver();
        WebElement next_page = getElement(locator);
        scroll.executeScript("arguments[0].scrollIntoView()", next_page);
    }

    public void HandleDropdown(By locator, String text) {
        WebElement dropdown = getElement(locator);
        dropdown.click();
        for (WebElement option : dropdown.findElements(By.xpath("//li"))) {
            if (option.getText().equals(text)) {
                option.click();
                break;
            }
        }
    }

    public void webDriverWait(By locator){
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(15));
        wait.until(ExpectedConditions.elementToBeClickable(locator));
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public String getAttributeName(By locator){
        return getElement(locator).getAttribute("Message");
    }

    public void BrowserNavigate(){
        getDriver().navigate().back();
    }

    public void addScreenshot() {
        Allure.addAttachment("After Test", new ByteArrayInputStream(((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BYTES)));
    }
}
````
### LoginTest 
````

package TestCases;

import Pages.HomePage;
import Pages.LoginPage;
import Utility.DataSet;
import Utility.DriverSetup;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class TestLoginPage extends DriverSetup {

    HomePage homePage = new HomePage();
    LoginPage loginPage = new LoginPage();

    @BeforeMethod
    public void TestLoginBtn(){
        loginPage.navigateToLoginPage();
    }

    @Test(dataProvider = "validCredentials", dataProviderClass = DataSet.class)
    @Description("User Login With ValidCredentials")
    @Severity(SeverityLevel.BLOCKER)
    public void loginWithValidData(String phone_number, String pass){
        loginPage.doLogin(phone_number, pass);
        homePage.HoverElement(homePage.sign_in_hover);
        Assert.assertTrue(homePage.is_element_visible(homePage.logout_hover_btn));
    }
}
````
## Reporting
### Allure Reporting
Make Sure allure dependencies are added to the POM.xml file. Then, Create a TestNG.xml File and set up a run method with a Thread count. Finally, run the test.
After Completing the run test go to the terminal and execute this code:- 
````

allure generate ./allure-results/ --clean

allure open ./allure-report
````
### Surefire Reporting
Go through the terminal or cmd and run this given code based on your requirements: 
````

mvn test
mvn test -Dbrowser=Chrome
mvn test -Dbrowser=Chrome -DsuitFileName="testng.xml"
````
## Report Screenshots
<img width="675" alt="Allure_Report" src="https://github.com/Anik16298/Book_house_web_automation_using_selenium_with_allure_report/blob/02c3e581ddcdccd7b048d9547af26368ebfbdac3/screenshots/Result%20SS-1.png">
<img width="675" alt="Allure_Report" src="https://github.com/Anik16298/Book_house_web_automation_using_selenium_with_allure_report/blob/02c3e581ddcdccd7b048d9547af26368ebfbdac3/screenshots/Result%20SS-2.png">
<img width="675" alt="Allure_Report" src="https://github.com/Anik16298/Book_house_web_automation_using_selenium_with_allure_report/blob/02c3e581ddcdccd7b048d9547af26368ebfbdac3/screenshots/Result%20SS-3.png">
<img width="675" alt="Allure_Report" src="https://github.com/Anik16298/Book_house_web_automation_using_selenium_with_allure_report/blob/02c3e581ddcdccd7b048d9547af26368ebfbdac3/screenshots/Result%20SS-4.png">
<img width="675" alt="Allure_Report" src="https://github.com/Anik16298/Book_house_web_automation_using_selenium_with_allure_report/blob/02c3e581ddcdccd7b048d9547af26368ebfbdac3/screenshots/Result%20SS-5.png">
<img width="675" alt="Allure_Report" src="https://github.com/Anik16298/Book_house_web_automation_using_selenium_with_allure_report/blob/02c3e581ddcdccd7b048d9547af26368ebfbdac3/screenshots/Result%20SS-6.png">
<img width="675" alt="Allure_Report" src="https://github.com/Anik16298/Book_house_web_automation_using_selenium_with_allure_report/blob/02c3e581ddcdccd7b048d9547af26368ebfbdac3/screenshots/Result%20SS-7.png">
