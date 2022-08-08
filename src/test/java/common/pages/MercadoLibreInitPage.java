package common.pages;

import general.Setup;
import general.pom.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class MercadoLibreInitPage extends PageObject {

    private String titlePath = "//h1[text()='MercadoLibre']";


    public MercadoLibreInitPage() {
        super();

    }

    public WebElement getTitleElement() {
        Setup.getWait().visibilityOfElement(By.xpath(titlePath), "Element title is not rendered");
        return this.getElement(By.xpath(titlePath));
    }

    public WebElement getCountryById(String country_id) {
        return this.getElement(By.id(country_id));
    }


}
