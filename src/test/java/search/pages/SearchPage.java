package search.pages;

import general.Setup;
import general.pom.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchPage extends PageObject {
    private String viewMoreClass = "dynamic__carousel-link";

    private String priceMaxXpath = "//input[@data-testid='Maximum-price']";
    private String priceButtonXpath = "//button[@data-testid='submit-price']";

    private String orderButtonXpath = "//button[@class='andes-dropdown__trigger' and @aria-label='Más relevantes']";
    private String priceResultClass = "price-tag-fraction";

    public SearchPage() {
        super();
    }

    public String openViewMore() {
        WebElement more = this.getElement(By.className(viewMoreClass));
        String tempHref = more.getAttribute("href");
        this.scrollIntoView(more);
        Setup.getActions().moveToElement(more).click().perform();
//        more.click();
        return tempHref;
    }

    public List<WebElement> getLateralFilterMenu(String name) {
        return this.getElements(By.xpath("//div[text()='".concat(name).concat("']/../ul/li/a")));
    }

    public WebElement setMaxPrice(String price) {
        WebElement maxp = this.getElement(By.xpath(priceMaxXpath));
        this.scrollIntoView(maxp);
        maxp.sendKeys(price);
        this.getElement(By.xpath(priceButtonXpath)).submit();
        return maxp;
    }

    public Map<String, WebElement> getOrderDropDownItem(String value) {
        this.getElement(By.xpath(orderButtonXpath)).click();
       // Setup.getWait().visibilityOfElement(By.className("andes-list__item andes-list__item--size-compact"), "Order menu is not visible");
        List<WebElement> list = this.getElements(By.className("andes-list__item--size-compact"));
        Map<String, WebElement> store = new HashMap<>();
        for (WebElement el : list) {

            store.put(el.findElement(By.tagName("span")).getText(), el);

        }
        if (!value.isEmpty()) {
            store.get(value).click();
        }
        return store;
    }

    public List<WebElement> getAllResultsPrices() {
        return this.getElements(By.className(priceResultClass));
    }
}
