package general.pom;

import com.github.javafaker.Faker;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import general.Setup;

import java.util.List;

// page_url = about:blank
public abstract class PageObject {
    // No page elements added
    private WebDriver driver;
    protected String urlpath = "";
    private WebDriverWait wait;
    public Faker faker;

    /**
     * Constructor method
     */
    public PageObject() {
        setDriver();
        PageFactory.initElements(this.getDriver(), this);
        faker = new Faker();
    }

    /**
     * This method initializes the driver used by the WebPage
     */
    private void setDriver() {
        this.driver = Setup.getDriver();
    }

    /***
     *
     * @return the Driver used
     */
    public WebDriver getDriver() {
        return this.driver;
    }

    /**
     * @return return an instance of java Faker
     */
    public Faker getFaker() {
        return this.faker;
    }

    /**
     * @param by it identifies the By locator
     * @return Returns the web element found according the locator
     */
    public WebElement getElement(By by) {
        return this.driver.findElement(by);
    }

    /**
     * @param by it identifies the By locator
     * @return Returns a list of web elements found according the locator
     */
    public List<WebElement> getElements(By by) {
        return this.driver.findElements(by);
    }

    /**
     * @return The current browser url will be returned
     */
    public String getCurrentUrl() {
        return this.driver.getCurrentUrl();
    }

    /**
     * @return the path used by the current webpage will be returned
     */
    public String getPagePath() {
        return this.urlpath;
    }

    /**
     * This method scrolls the document DOMsearching for an specific element
     *
     * @param scrollElementxpath This is the element scrollable in the DOM
     * @param targetElementxpath Receives the target element
     */
    public void scroll(String scrollElementxpath, By targetElementxpath) {

        WebElement el = this.getElement(targetElementxpath);
        int desired_y = el.getSize().height / 2 + el.getLocation().y;
        int current_y = (Integer.parseInt(String.valueOf(Setup.executeScript("return window.innerHeight"))) / 2)
                + Integer.parseInt(String.valueOf(Setup.executeScript("return window.pageYOffset")));
        int scroll_y_by = (desired_y + 150) + (current_y + 150);

        Setup.executeScript("var el=" + "document.evaluate('" + scrollElementxpath + "',"
                + " document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;"
                + " el.scrollTo(0, arguments[0]);", scroll_y_by);
        Setup.getWait().thread(500);
    }

    public void scrollIntoView(By by) {
        this.scrollIntoView(this.getElement(by));
    }

    public void scrollIntoView(WebElement element) {
        Setup.executeScript("arguments[0].scrollIntoView({behavior: 'auto',\n" +
                "            block: 'center',\n" +
                "            inline: 'center'\n" +
                "        });", element);
        Setup.getWait().thread(5000);
    }

    public void clickOnButton(By by) {
        this.scrollIntoView(by);
        this.clickOnButton(this.getElement(by));
    }

    public void clickOnButton(WebElement element) {
        this.scrollIntoView(element);
        try {
            Setup.getActions().moveToElement(element).click().build().perform();
        } catch (Exception e) {

        }
    }

    public void checkElementVisibility(By by) {
        this.getElement(by).isDisplayed();
    }


    public abstract void validateElements();


}