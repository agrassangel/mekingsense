package general;


import io.cucumber.java.After;
import io.cucumber.java.Before;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.json.*;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.AbstractDriverOptions;

public final class Setup {
    private static WebDriver driver;
    private static HashMap<String, Object> store = new HashMap<String, Object>();
    private static Actions actions;
    private static WaitingObject waitingObject;
    private static String defaultURL = "";
    private static JavascriptExecutor jsExecutor;
    public static Map<String, Object> timeouts;
    private final String propertyFilePath = "configs//config.properties";
    private static Properties properties;

    @Before

    /**
     * This method will be initialized before all test cases, setting up the required initial configurations
     */
    public void InitSetup() {
        loadDefaultProperties();
        readCountryInfo();
        timeouts = new HashMap<String, Object>();
        timeouts.put("implicit", 30000);
        timeouts.put("pageLoad", 5000000);
        timeouts.put("script", 300000);
        AbstractDriverOptions driverOptions;
        System.out.println(properties.get("ChromeDriver").toString());

        if (Boolean.parseBoolean(properties.get("setFirefoxAsBrowser").toString())) {
            System.setProperty("webdriver.chrome.driver", properties.get("FirefoxDriver").toString());
            System.setProperty("webdriver.chrome.silentOutput", "true");
            driverOptions = new FirefoxOptions();
            driverOptions.setCapability("timeouts", timeouts);
            driver = new FirefoxDriver((FirefoxOptions) driverOptions);

        } else {
            System.setProperty("webdriver.chrome.driver", properties.get("ChromeDriver").toString());
            System.setProperty("webdriver.chrome.silentOutput", "true");
            driverOptions = new ChromeOptions();
            driverOptions.setCapability("timeouts", timeouts);
            driver = new ChromeDriver((ChromeOptions) driverOptions);
        }
        driver.get(properties.get("defaultUrl").toString());
        driver.manage().window().maximize();

        initObject();
    }

    /**
     * All support files will be initialized
     */
    private static void initObject() {
        waitingObject = new WaitingObject(driver);
        actions = new Actions(driver);
        setJsExecutor((JavascriptExecutor) driver);
    }

    public static Map<String, Object> getTimeouts() {
        return timeouts;
    }

    void setTimeouts(Map<String, Object> timeouts) {
        Setup.timeouts = timeouts;
    }

    public static Object executeScript(String script, Object... arg) {
        return getJsExecutor().executeScript(script, arg);
    }

    /**
     * Returns the webDriver to be used during the test
     *
     * @return
     */
    public static WebDriver getDriver() {
        return driver;
    }

    /**
     * Returns an instance of the Actions class
     *
     * @return
     */
    public static Actions getActions() {
        return actions;
    }

    /**
     * Returns the value stored in the Map using a key identifier
     *
     * @param key
     * @return
     */
    public static Object getValueStore(String key) {
        return store.get(key);
    }

    /**
     * @return Return an instance of wait.
     */
    public static WaitingObject getWait() {
        return waitingObject;
    }

    /**
     * This method save a property in a format key-value
     *
     * @param key
     * @param value
     */
    public static void setKeyValueStore(String key, Object value) {
        store.put(key, value);
    }

    /**
     * Open new url
     *
     * @param url
     */
    public static void openUrl(String url) {
        driver.get(url);
        //waitingObject.waitForLoading(3600);
    }

    @After
    public void close() {
        driver.close();
    }

    /**
     * Load the system default properties stored in a configuration file
     */

    public static void loadDefaultProperties() {
        properties = new Properties();
        try {
            InputStream input = new FileInputStream("configs//config.properties");
            properties.load(input);
        } catch (java.io.IOException e) {

        }
        setKeyValueStore("defaultProperties", properties);
        System.setProperty("defaultURL", properties.getProperty("defaultUrl"));
    }

    public static JavascriptExecutor getJsExecutor() {
        return jsExecutor;
    }

    public static void setJsExecutor(JavascriptExecutor jsExecutor) {
        Setup.jsExecutor = jsExecutor;
    }


    public static void readCountryInfo() {
        JSONTokener jsonToken = null;
        try {
            jsonToken = new JSONTokener(new FileReader("src/main/resources/countries.json"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        JSONArray jsonArray = new JSONArray(jsonToken);
//        System.out.println(jsonArray.toString());
//        JSONObject v = jsonArray.getJSONObject(0);
//        System.out.println(v.get("country"));
        setKeyValueStore("countryInfo", jsonArray);


    }
}
