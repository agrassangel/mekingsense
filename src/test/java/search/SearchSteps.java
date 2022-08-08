package search;

import general.Setup;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import search.pages.GenericMore;
import search.pages.SearchPage;

import java.util.*;
import java.util.stream.Collectors;

public class SearchSteps {
    private SearchPage searchPage;
    private GenericMore genericMore;

    public SearchSteps() {
        this.searchPage = new SearchPage();
        this.genericMore = new GenericMore();
    }

    public void openViewMoreOption() {
        String href = searchPage.openViewMore();
        Assert.assertEquals(" View more view url doesnt no match", href, Setup.getDriver().getCurrentUrl());
    }

    public void userSetTheRequiredQueryFilters(Map<String, String> filters) {
        //Key must be equal to filters
        Set<String> keyList = filters.keySet();

        for (String key : keyList) {
            mappingKey(key, filters.get(key));
        }


    }

    private void mappingKey(String key, String value) {

        switch (key) {
            case "Precio_Max":
                this.searchPage.setMaxPrice(value);
                break;
            case "Ordenar por":
                this.searchPage.getOrderDropDownItem(value);
                break;
            default:
                this.setFilters(key, value);
        }

    }

    private void setFilters(String filterName, String optionName) {
        List<WebElement> options = this.searchPage.getLateralFilterMenu(filterName).stream()
                .filter(webElement ->
                {
                    String te = null;
                    try {
                        te = webElement.findElement(By.className("ui-search-filter-name")).getText();
                    } catch (Exception e) {
                        te = webElement.getText();
                    }
                    return (te.equalsIgnoreCase(optionName) || te.equalsIgnoreCase("Mostrar más"));
                }).collect(Collectors.toList());

        WebElement eTmp;
        switch (options.size()) {
            case 1:
                eTmp = options.get(0);
                this.genericMore.scrollIntoView(eTmp);
                if (eTmp.getText().equals("Mostrar más")) {
                    eTmp.click();
                    Setup.getWait().visibilityOfElement(By.className("andes-modal__title"), "Element Modal is not open");
                    this.genericMore.searchValue(optionName);
                    List<WebElement> list = this.genericMore.getResultsByCriteria(optionName);
                    Assert.assertFalse("There is no result that matches with teh criteria serached", list.isEmpty());
                    WebElement el = list.get(new Random().nextInt(list.size()));
                    this.genericMore.scrollIntoView(el);
                    el.click();

                } else {
                    eTmp.click();
                }
                break;
            case 2:
                // Assuming right value is on 0 position
                WebElement el = options.get(0);
                this.genericMore.scrollIntoView(el);
                el.click();
                break;
            default:
                Assert.fail("No option have been found for '".concat(optionName).concat("' in filter: ").concat(filterName));
        }


    }

    public void validateResults() {
        List<WebElement> elList = this.searchPage.getAllResultsPrices();
        System.out.println("Total of element filtered are ".concat(String.valueOf(elList.size())));

        // Need criteria to know how the system renders the values sorted
        elList.sort((o1, o2) -> {
            String t = o1.getText();
            double v = Double.parseDouble(o1.getText().replace(".", ""));
            double v2 = Double.parseDouble(o2.getText().replace(".", ""));
            if (v > v2)
                return 1;
            else if (v < v2)
                return -1;
            return 0;
        });


//        elList = elList.stream().sorted().collect(Collectors.toList());

    }
}
