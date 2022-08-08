package common.pages;

import general.Setup;
import general.pom.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class MercadoLibreHomePage extends PageObject {

    String categorySelector = "a.nav-menu-categories-link";
    String subMenuCategoriesClass = "nav-categs-departments__list";

    public MercadoLibreHomePage() {
        super();
    }

    public List<WebElement> getMenuList() {
        WebElement el = this.getElement(By.cssSelector(categorySelector));
        Setup.getActions().moveToElement(el).perform();

        return this.getElements(By.xpath("//li[contains(@class,'" + subMenuCategoriesClass + "')]/a"));
    }
}
