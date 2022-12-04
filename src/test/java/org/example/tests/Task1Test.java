package org.example.tests;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.concurrent.TimeUnit;

public class Task1Test {

    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void beforeTest(){
        System.setProperty("webdriver.chrome.driver", "src/test/resources/webdrivers/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver,10,1000);
        driver.get("http://training.appline.ru/user/login");
    }

    @Test
    public void exampleTest() {

        // Залогиниться
        String fieldPath = "//input[@id='%s']";
        fillInputField(driver.findElement(By.xpath(String.format(fieldPath, "prependedInput"))), "Sekretar Kompanii");
        fillInputField(driver.findElement(By.xpath(String.format(fieldPath, "prependedInput2"))), "testing");
        String loginButtonXPath = "//button[@id='_submit']";
        WebElement loginButton = driver.findElement(By.xpath(loginButtonXPath));
        waitUntilClickable(loginButton);
        loginButton.click();

        // Проверить заголовок "Панель быстрого запуска"
        String firstTitleXPath = "//h1[@class='oro-subtitle']";
        WebElement firstTitle = driver.findElement(By.xpath(firstTitleXPath));
        waitUntilVisible(firstTitle);
        Assert.assertEquals("Заголовок отсутствует или не соответствует требуемому",
                "Панель быстрого запуска", firstTitle.getText());

        // Раскрыть меню "Расходы"
        String costsMenuXPath = "//span[text()='Расходы']";
        Actions action = new Actions(driver);
        WebElement costsMenu = driver.findElement(By.xpath(costsMenuXPath));
        waitUntilClickable(costsMenu);
        action.moveToElement(costsMenu).perform();

        // Нажать на пункт меню "Командировки"
        String businessTripXPath = "//span[text()='Командировки']";
        WebElement businessTripButton = driver.findElement(By.xpath(businessTripXPath));
        waitUntilClickable(businessTripButton);
        businessTripButton.click();

        // Нажать на кнопку "Создать командировку"
        String createBTripXPath = "//a[@title='Создать командировку' and text() = 'Создать командировку']";
        WebElement createBTripButton = driver.findElement(By.xpath(createBTripXPath));
        waitUntilClickable(createBTripButton);
        createBTripButton.click();

        // Проверить заголовок "Создать командировку"
        String createBTripTitleXPath = "//h1[@class='user-name']";
        WebElement createBTripTitle = driver.findElement(By.xpath(createBTripTitleXPath));
        waitUntilVisible(createBTripTitle);
        Assert.assertEquals("Заголовок отсутствует или не соответствует требуемому",
                "Создать командировку", createBTripTitle.getText());

        // Выбрать подразделение
        String drbBusinessUnitXPath = "//select[@data-name='field__business-unit']";
        Select drpBusinessUnit = new Select(driver.findElement(By.xpath(drbBusinessUnitXPath)));
        drpBusinessUnit.selectByVisibleText("Центр разработки и сопровождения");

        //Выбрать принимающую организацию
        String companySelectXPath = "//a[@id='company-selector-show']";
        WebElement companySelectButton = driver.findElement(By.xpath(companySelectXPath));
        waitUntilClickable(companySelectButton);
        companySelectButton.click();
        String companySelect2XPath = "//span[@class='select2-chosen' and text()='Укажите организацию']";
        WebElement companySelect2Button = driver.findElement(By.xpath(companySelect2XPath));
        waitUntilClickable(companySelect2Button);
        companySelect2Button.click();
        String neededComp = "Applana";
        String companyInputXPath = "//div[@id='select2-drop']//input[@class='select2-input select2-focused']";
        WebElement companyInput = driver.findElement(By.xpath(companyInputXPath));
        waitUntilClickable(companyInput);
        companyInput.sendKeys(neededComp);
        String matchingCompXPath = "//span[@class='select2-match' and text()='%s']";
        WebElement neededCompany = driver.findElement(By.xpath(String.format(matchingCompXPath,neededComp)));
        waitUntilClickable(neededCompany);
        neededCompany.click();

        // Поставить чек-бокс "Заказ билетов"
        String checkboxXPath = "//input[@data-ftid='crm_business_trip_tasks_1']";
        WebElement checkbox = driver.findElement(By.xpath(checkboxXPath));
        waitUntilClickable(checkbox);
        checkbox.click();

        // Заполнить города прибытия и убытия
        String citiesXPath = "//input[@data-ftid='%s']";
        WebElement depCity = driver.findElement(By.xpath(String.format(citiesXPath,"crm_business_trip_departureCity")));
        WebElement arrCity = driver.findElement(By.xpath(String.format(citiesXPath,"crm_business_trip_arrivalCity")));
        fillInputField(depCity,
                "Новосибирск, Россия");
        fillInputField(arrCity,
                "Шанхай, Китай");

        // Заполнить даты
        String depDateXPath = "//input[contains(@id,'selector') and contains(@id,'departureDatePlan')]";
        String retDateXPath = "//input[contains(@id,'selector') and contains(@id,'returnDatePlan')]";
        WebElement depDate = driver.findElement(By.xpath(depDateXPath));
        WebElement retDate = driver.findElement(By.xpath(retDateXPath));
        scrollToElementJs(depDate);
        depDate.sendKeys("11.11.2022");
        retDate.sendKeys("31.12.2023");
        retDate.sendKeys(Keys.ESCAPE);

        // Проверить заполнение полей
        String checkBusinessUnitXPath = "//div[@class='selector input-widget-select' and contains(@id,'businessUnit')]/span";
        Assert.assertEquals("Поле Подразделение заполнено неверно",
                "Центр разработки и сопровождения",
                driver.findElement(By.xpath(checkBusinessUnitXPath)).getText());
        String checkOrgXPath = "//a[@class='select2-choice']/span";
        Assert.assertEquals("Организация выбрана некорректно",
                "Applana", driver.findElement(By.xpath(checkOrgXPath)).getText());
        Assert.assertTrue("Чекбокс Заказ билетов не отмечен", checkbox.isSelected());
        Assert.assertEquals("Город выбытия заполнен неверно", "Новосибирск, Россия",
                depCity.getAttribute("value"));
        Assert.assertEquals("Город прибытия заполнен неверно","Шанхай, Китай",
                arrCity.getAttribute("value"));

        depDate.click();
        String date1XPath = "//td[contains(@class,'ui-datepicker-current-day') and @data-event='click']";
        String day1XPath = date1XPath + "/a";
        Assert.assertEquals("Год выезда выбран неправильно","2022",
                depDate.findElement(By.xpath(date1XPath)).getAttribute("data-year"));
        Assert.assertEquals("Месяц выезда выбран неправильно","10",
                depDate.findElement(By.xpath(date1XPath)).getAttribute("data-month"));
        Assert.assertEquals("День выезда выбран неправильно","11",
                depDate.findElement(By.xpath(day1XPath)).getText());
        depDate.sendKeys(Keys.ESCAPE);

        retDate.click();
        Assert.assertEquals("Год возвращения выбран неправильно","2023",
                depDate.findElement(By.xpath(date1XPath)).getAttribute("data-year"));
        Assert.assertEquals("Месяц возвращения выбран неправильно","11",
                depDate.findElement(By.xpath(date1XPath)).getAttribute("data-month"));
        Assert.assertEquals("День возвращения выбран неправильно","31",
                depDate.findElement(By.xpath(day1XPath)).getText());
        depDate.sendKeys(Keys.ESCAPE);

        // Нажать Сохранить и закрыть
        String saveAndCloseXPath = "//button[contains(text(), 'Сохранить и закрыть')]";
        WebElement saveAndClose = driver.findElement(By.xpath(saveAndCloseXPath));
        waitUntilClickable(saveAndClose);
        saveAndClose.click();

        // Проверить сообщение об ошибке
        String tripUsersErrorXPath = "//div[@data-ftid='crm_business_trip_users']/parent::*/following-sibling::*";
        WebElement tripUsersError = driver.findElement(By.xpath(tripUsersErrorXPath));
        waitUntilVisible(tripUsersError);
        Assert.assertEquals("Сообщение об ошибке не появилось/некорректно",
                "Список командируемых сотрудников не может быть пустым",
                tripUsersError.getText());

        String foreignUsersErrorXPath = "//div[@data-ftid='crm_business_trip_foreignUsers']/parent::*/following-sibling::*";
        WebElement foreignUsersError = driver.findElement(By.xpath(foreignUsersErrorXPath));
        waitUntilVisible(foreignUsersError);
        Assert.assertEquals("Сообщение об ошибке не появилось/некорректно",
                "Список командируемых сотрудников не может быть пустым",
                foreignUsersError.getText());
    }

    @After
    public void afterTest(){
        driver.quit();
    }

    private void fillInputField(WebElement element, String value) {
        waitUntilClickable(element);
        element.click();
        element.clear();
        element.sendKeys(value);
        boolean checkFlag = wait.until(ExpectedConditions.attributeContains(element, "value", value));
        Assert.assertTrue("Поле было заполнено некорректно", checkFlag);
    }

    private void waitUntilClickable(WebElement element){
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    private void waitUntilVisible(WebElement element){
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    private void scrollToElementJs(WebElement element) {
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
        javascriptExecutor.executeScript("arguments[0].scrollIntoView(true);", element);
    }





}
