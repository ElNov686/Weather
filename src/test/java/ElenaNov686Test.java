import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ElenaNov686Test {

    private static final String BASE_URL = "https://openweathermap.org/";

    /*TC_11_01
    1.  Открыть базовую ссылку
    2.  Нажать на пункт меню Guide
    3.  Подтвердить, что вы перешли на страницу со ссылкой https://openweathermap.org/guide и что
    title этой страницы OpenWeatherMap API guide - OpenWeatherMap
    */
    @Test
    public void testGuideVerifyTitle() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "/Users/elena/Driver/chromedriver");
        WebDriver driver = new ChromeDriver();

        driver.get(BASE_URL);
        Thread.sleep(5000);

        WebElement findLinkGuide = driver.findElement(By
                .xpath("//div[@id='desktop-menu']//a[@href='/guide']"));
        //body/nav//a[text()='Guide']
        findLinkGuide.click();
        Thread.sleep(5000);

        String actualResult1 = driver.getCurrentUrl();
        String actualResult2 = driver.getTitle();

        String expectedResult1 = "https://openweathermap.org/guide";
        String expectedResult2 = "OpenWeatherMap API guide - OpenWeatherMap";

        Assert.assertEquals(actualResult1, expectedResult1);
        Assert.assertEquals(actualResult2, expectedResult2);

        driver.quit();
    }

    /*TC_11_02
    1.  Открыть базовую ссылку
    2.  Нажать на единицы измерения Imperial: °F, mph
    3.  Подтвердить, что температура для города показана в Фарингейтах
     */
    @Test
    public void testTemperatureImperialFahrenheitVerify() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "/Users/elena/Driver/chromedriver");
        WebDriver driver = new ChromeDriver();

        driver.get(BASE_URL);
        Thread.sleep(5000);

        WebElement imperialF = driver.findElement(By
                .xpath("//div[@class='switch-container']//div[text()='Imperial: °F, mph']"));

        imperialF.click();
        Thread.sleep(5000);

        WebElement temperatureF = driver.findElement(By
                .xpath("//span[@class='heading']"));

        String actualResult = temperatureF.getText();
        actualResult = actualResult.substring(actualResult.length() - 1);

        String expectedResult = "F";

        Assert.assertEquals(actualResult, expectedResult);

        driver.quit();
    }

    /*TC_11_03
    1.  Открыть базовую ссылку
    2. Подтвердить, что внизу страницы есть панель с текстом “We use cookies which are essential
    for the site to work. We also use non-essential cookies to help us improve our services.
    Any data collected is anonymised. You can allow all cookies or manage them individually.”
    3. Подтвердить, что на панели внизу страницы есть 2 кнопки “Allow all” и “Manage cookies”*/
    @Test
    public void testVerifyCookiesTextAndTwoButtons() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "/Users/elena/Driver/chromedriver");
        WebDriver driver = new ChromeDriver();

        driver.get(BASE_URL);
        Thread.sleep(5000);

        WebElement cookiesText = driver.findElement(By
                .xpath("//p[@class='stick-footer-panel__description']"));
        String actualResult1 = cookiesText.getText();
        String expectedResult1 = "We use cookies which are essential for the site to work. We also use non-essential cookies to help us improve our services. Any data collected is anonymised. You can allow all cookies or manage them individually.";

        WebElement allowAll = driver.findElement(By.xpath("//button[text()='Allow all']"));
        Boolean actualResult2 = allowAll.isDisplayed();

        WebElement manageCookies = driver.findElement(By
                .xpath("//a[@class='stick-footer-panel__link']"));
        Boolean actualResult3 = manageCookies.isDisplayed();

        Assert.assertEquals(actualResult1, expectedResult1);
        Assert.assertTrue(actualResult2);
        Assert.assertTrue(actualResult3);

        driver.quit();
    }

    /*TC_11_04
    1.  Открыть базовую ссылку
    2.  Подтвердить, что в меню Support есть 3 подменю с названиями “FAQ”, “How to start”
    и “Ask a question”*/

    @Test
    public void testVerifyInMenuSupportSubmenu() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "/Users/elena/Driver/chromedriver");
        WebDriver driver = new ChromeDriver();

        driver.get(BASE_URL);

        driver.manage().window().maximize();
        Thread.sleep(5000);

        WebElement menuSupport = driver.findElement(By.xpath("//div[@id='support-dropdown']"));
        menuSupport.click();
        WebElement menuDropDownFAQ = driver.findElement(By
                .xpath("//ul[@id='support-dropdown-menu']//a[@href='/faq']"));
        WebElement menuDropDownHowToStart = driver.findElement(By
                .xpath("//ul[@id='support-dropdown-menu']//a[@href='/appid']"));
        WebElement menuDropDownAskQuestions = driver.findElement(By
                .xpath("//ul[@id='support-dropdown-menu']//a[@href='https://home.openweathermap.org/questions']"));

        Assert.assertTrue(menuDropDownFAQ.isDisplayed());
        Assert.assertTrue(menuDropDownHowToStart.isDisplayed());
        Assert.assertTrue(menuDropDownAskQuestions.isDisplayed());
        Assert.assertTrue(menuSupport.isDisplayed());

    }
    /* TC_11_05
    1. Открыть базовую ссылку
    2. Нажать пункт меню Support → Ask a question
    3. Заполнить поля Email, Subject, Message
    4. Не подтвердив CAPTCHA, нажать кнопку Submit
    5. Подтвердить, что пользователю будет показана ошибка “reCAPTCHA verification failed, please try again.”
    */

    @Test
    public void testErrorMessage_WhenSubmitSupportWithoutCapcha() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "/Users/elena/Driver/chromedriver");
        WebDriver driver = new ChromeDriver();

        String eMail = "tester@test.com";
        String message = "TTTTTTT";
        String expectedResult = "reCAPTCHA verification failed, please try again.";

        driver.get(BASE_URL);
        driver.manage().window().maximize();
        Thread.sleep(5000);

        WebElement menuSupport = driver.findElement(By.xpath("//div[@id='support-dropdown']"));
        menuSupport.click();
        Thread.sleep(500);

        WebElement dropDownAskQuestion = driver.findElement(By
                .xpath("//ul[@id='support-dropdown-menu']//a[@href='https://home.openweathermap.org/questions']"));
        dropDownAskQuestion.click();
        Thread.sleep(5000);

        String mainWindow = driver.getWindowHandle();
        for (String winHandle : driver.getWindowHandles()) {
            if (!winHandle.equals(mainWindow)) {
                driver.switchTo().window(winHandle);
            }
        }

        Thread.sleep(5000);
        WebElement emailField = driver.findElement(By.
                xpath("//input[@id='question_form_email']"));
        emailField.click();
        emailField.sendKeys(eMail);

        WebElement subjectSelectField = driver.findElement(By
                .xpath("//select[@id='question_form_subject']"));
        subjectSelectField.click();
        Thread.sleep(3000);

        WebElement subjectSelectOptions = driver.findElement(By
                .xpath("//select[@id='question_form_subject']//option[@value='Tech Issue']"));
        subjectSelectOptions.click();
        Thread.sleep(3000);

        WebElement messageTextArea = driver.findElement(By
                .xpath("//textarea[@id='question_form_message']"));
        messageTextArea.sendKeys(message);

        Thread.sleep(3000);
        WebElement submitButton = driver.findElement(By.xpath("//input[@value='Submit']"));
        submitButton.click();
        Thread.sleep(3000);

        WebElement actualResult = driver.findElement(By
                .xpath("//div[@class='help-block']"));

        Assert.assertEquals(actualResult.getText(), expectedResult);

        driver.quit();
    }

    /*    TC_11_06
    1.  Открыть базовую ссылку
    2.  Нажать пункт меню Support → Ask a question
    3.  Оставить значение по умолчанию в checkbox Are you an OpenWeather user?
    4. Оставить пустым поле Email
    5. Заполнить поля  Subject, Message
    6. Подтвердить CAPTCHA
    7. Нажать кнопку Submit
    8. Подтвердить, что в поле Email пользователю будет показана ошибка “can't be blank” */
    @Test
    public void testErrorMessage_WhenSubmitSupportWithoutEmail() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "/Users/elena/Driver/chromedriver");
        WebDriver driver = new ChromeDriver();

        String eMail = "";
        String message = "TTTTTTT";
        String expectedResult = "can't be blank";

        driver.get(BASE_URL);
        driver.manage().window().maximize();
        Thread.sleep(5000);

        WebElement menuSupport = driver.findElement(By.xpath("//div[@id='support-dropdown']"));
        menuSupport.click();
        Thread.sleep(500);

        WebElement dropDownAskQuestion = driver.findElement(By
                .xpath("//ul[@id='support-dropdown-menu']//a[@href='https://home.openweathermap.org/questions']"));
        dropDownAskQuestion.click();
        Thread.sleep(5000);

        String mainWindow = driver.getWindowHandle();
        for (String winHandle : driver.getWindowHandles()) {
            if (!winHandle.equals(mainWindow)) {
                driver.switchTo().window(winHandle);
            }
        }

        Thread.sleep(5000);
        WebElement emailField = driver.findElement(By.
                xpath("//input[@id='question_form_email']"));
        emailField.click();
        emailField.sendKeys(eMail);
        WebElement subjectSelectField = driver.findElement(By
                .xpath("//select[@id='question_form_subject']"));
        subjectSelectField.click();

        Thread.sleep(3000);
        WebElement subjectSelectOptions = driver.findElement(By
                .xpath("//select[@id='question_form_subject']//option[@value='Tech Issue']"));
        subjectSelectOptions.click();
        Thread.sleep(3000);

        WebElement messageTextArea = driver.findElement(By
                .xpath("//textarea[@id='question_form_message']"));
        messageTextArea.sendKeys(message);

        String window2 = driver.getWindowHandle();

        driver.switchTo().frame(driver.findElement(By.cssSelector("iframe[title='reCAPTCHA']")));

        WebElement enterCaptcha = driver.findElement(By.xpath(
                "//span[@class='recaptcha-checkbox goog-inline-block recaptcha-checkbox-unchecked "
                        + "rc-anchor-checkbox']"));
        enterCaptcha.click();

        Thread.sleep(10000);

        driver.switchTo().window(window2);

        WebElement submitButton = driver.findElement(By.xpath("//input[@value='Submit']"));
        submitButton.click();
        Thread.sleep(3000);

        WebElement actualResult = driver.findElement(By
                .xpath("//span[@class='help-block']"));

        Assert.assertEquals(actualResult.getText(), expectedResult);

        driver.quit();
    }

    /*TC_11_07
    1.  Открыть базовую ссылку
    2.  Нажать на единицы измерения Imperial: °F, mph
    3.  Нажать на единицы измерения Metric: °C, m/s
    4.  Подтвердить, что в результате этих действий, единицы измерения температуры изменились с F на С
    */
    @Test
    public void testVerifyFahrenheitFromC() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "/Users/elena/Driver/chromedriver");
        WebDriver driver = new ChromeDriver();
        String expectedResult = "C";

        driver.get(BASE_URL);
        Thread.sleep(5000);
        WebElement imperial = driver.findElement(By
                .xpath("//div[normalize-space()='Imperial: °F, mph']"));
        imperial.click();
        Thread.sleep(4000);

        WebElement imperialTemperature = driver.findElement(By
                .xpath("//span[@class='heading']"));

        WebElement metric = driver.findElement(By
                .xpath("//div[normalize-space()='Metric: °C, m/s']"));
        metric.click();
        Thread.sleep(4000);

        WebElement metricTemperature = driver.findElement(By.xpath("//span[@class='heading']"));

        String[] metricTemperatureText = metricTemperature.getText().split("");

        Assert.assertEquals(metricTemperatureText[metricTemperatureText.length - 1], expectedResult);

        driver.quit();
    }

    /*TC_11_08
    1.  Открыть базовую ссылку
    2.  Нажать на лого компании
    3.  Дождаться, когда произойдет перезагрузка сайта, и подтвердить,
     что текущая ссылка не изменилась */
    @Test
    public void testVerifyLogoReloadUrl() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "/Users/elena/Driver/chromedriver");
        WebDriver driver = new ChromeDriver();
        String expectedResult = "Loading";

        driver.get(BASE_URL);
        Thread.sleep(5000);
        WebElement logo = driver.findElement(By
                .xpath("//img[@src='/themes/openweathermap/assets/img/logo_white_cropped.png']"));

        WebElement loading = driver.findElement(By.xpath("//div[@aria-label='Loading']"));

        String actualResult = loading.getAttribute("aria-label");

        Assert.assertEquals(actualResult, expectedResult);

        driver.quit();
    }

    //TC_11_09
//1.  Открыть базовую ссылку
//2.  В строке поиска в навигационной панели набрать “Rome”
//3.  Нажать клавишу Enter
//4.  Подтвердить, что вы перешли на страницу в ссылке которой содержатся слова “find” и “Rome”
//5. Подтвердить, что в строке поиска на новой странице вписано слово “Rome”
    @Test
    public void testRomeEnterVerifyUrlContainsRomeAndFind() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "/Users/elena/Driver/chromedriver");
        WebDriver driver = new ChromeDriver();
        String rome = "Rome";

        driver.get(BASE_URL);
        Thread.sleep(5000);

        WebElement searchLine = driver.findElement(By
                .xpath("//div[@id='desktop-menu']//input[@placeholder='Weather in your city']"));
        searchLine.sendKeys(rome);
        searchLine.sendKeys(Keys.ENTER);
        Thread.sleep(5000);

        String currentUrl = driver.getCurrentUrl();
        String actualResult = driver.findElement(By.xpath("//input[@class]")).getAttribute("value");

        Assert.assertEquals(actualResult, rome);
        Assert.assertTrue(currentUrl.contains("Rome"));
        Assert.assertTrue(currentUrl.contains("find"));

        driver.quit();
    }

    @Test
    public void testH2TagTextWhenSearchingCityCountry() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "/Users/elena/Driver/chromedriver");
        WebDriver driver = new ChromeDriver();

        driver.get(BASE_URL);
        Thread.sleep(5000);
        String cityName = "Paris";
        String expectedResult = "Paris, FR";


        WebElement searchCityField = driver.findElement(
                By.xpath("//div[@id='weather-widget']//input[@placeholder = 'Search city']")
        );
        searchCityField.click();
        searchCityField.sendKeys(cityName);

        WebElement searchButton = driver.findElement(
                By.xpath("//div[@id='weather-widget']//button[@type='submit']")
        );
        searchButton.click();

        Thread.sleep(1000);

        WebElement parisFRChoiceInDropDownMenu = driver.findElement(
                By.xpath("//ul[@class = 'search-dropdown-menu']/li/span[text ()= 'Paris, FR ']")
        );
        parisFRChoiceInDropDownMenu.click();

        WebElement h2CityNameHeader = driver.findElement(
                By.xpath("//div[@id = 'weather-widget']//h2")
        );

        Thread.sleep(2000);
        String actualResult = h2CityNameHeader.getText();

        Assert.assertEquals(actualResult, expectedResult);

        driver.quit();
    }
}
