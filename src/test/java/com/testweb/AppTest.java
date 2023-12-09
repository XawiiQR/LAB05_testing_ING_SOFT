package com.testweb;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import junit.framework.TestCase;

public class AppTest extends TestCase {
    private WebDriver driver;

    @Override
    protected void setUp() {
        System.setProperty("webdriver.chrome.driver",
                "src/test/java/com/testweb/chromedriver_win32/chromedriver.exe");
        driver = new ChromeDriver();
    }

    public void tests() {
        // Casos de equivalencia y valores límite
        testPercentageCalculation("15.5", "7.2", "1.116"); // Caso normal
        testPercentageCalculation("-15.5", "7.2", "-1.116"); // Números negativos
        testPercentageCalculation("0", "7.2", "0"); // División por cero
        testPercentageCalculation("0.01", "7.2", "0.00072"); // Números decimales
        testPercentageCalculation("100", "7.2", "7.2"); // Porcentaje máximo

    }

    @Override
    protected void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    public static String calculatePercentage(WebDriver driver, String firstValue, String secondValue) {
        navigateToCalculatorPage(driver);

        WebElement firstNumber = driver.findElement(By.id("cpar1"));
        firstNumber.sendKeys(firstValue);

        WebElement secondNumber = driver.findElement(By.id("cpar2"));
        secondNumber.sendKeys(secondValue);

        WebElement calculateButton = driver.findElement(By.xpath("//input[@value='Calculate']"));
        calculateButton.click();

        return getResult(driver);
    }

    private static void navigateToCalculatorPage(WebDriver driver) {
        driver.get("https://www.calculator.net/percent-calculator.html");
    }

    private static String getResult(WebDriver driver) {
        WebElement resultElement = driver.findElement(By.cssSelector("font[color='green']"));
        return resultElement.getText();
    }

    private void testPercentageCalculation(String firstValue, String secondValue, String expected) {
        String result = calculatePercentage(driver, firstValue, secondValue);
        System.out.println("Para " + firstValue + " y " + secondValue + " el resultado es: " + result);
        assertEquals(expected, result.trim());
    }
}
