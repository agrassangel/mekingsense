package search.pages;

import general.pom.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

public class GenericMore extends PageObject {

    private String searchInputId = "//input[@data-testid='search_bar']";
    private String filterResultsXpath = "//div[@class='ui-search-search-modal-list']/descendant::a";

    public void searchValue(String criteria) {
        this.getElement(By.xpath(searchInputId)).sendKeys(criteria);
    }

    public List<WebElement> getResultsByCriteria(String criteria) {
        return this.getElements(By.xpath(filterResultsXpath)).stream()
                .filter(webElement -> webElement.findElement(
                        By.tagName("span")).getText().toLowerCase().contains(criteria.toLowerCase()))
                .collect(Collectors.toList());
    }


}
