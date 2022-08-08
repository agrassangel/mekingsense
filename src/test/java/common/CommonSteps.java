package common;

import common.pages.MercadoLibreHomePage;
import common.pages.MercadoLibreInitPage;
import general.Setup;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class CommonSteps {
    private MercadoLibreHomePage mercadoLibreHomePage;
    private MercadoLibreInitPage mercadoLibreInitPage;
    JSONArray jsonArray = null;

    public CommonSteps() {
        this.jsonArray = (JSONArray) Setup.getValueStore("countryInfo");
        this.mercadoLibreHomePage = new MercadoLibreHomePage();
        this.mercadoLibreInitPage = new MercadoLibreInitPage();

    }


    public void userIsOnMercadoLibreWebPage() {
        Assert.assertTrue("Element Mercado Libre title is not displayed", this.mercadoLibreInitPage.getTitleElement().isDisplayed());
        Random a = new Random();
        int v=jsonArray.length() - 1;
        JSONObject t = jsonArray.getJSONObject(new Random().nextInt(jsonArray.length()));
        getCountryCodeElement(t.get("countryCode").toString());
        String dns = Setup.getDriver().getCurrentUrl();
        Assert.assertTrue("Url dont is not teh right DNS: ".concat(dns), dns.contains("www.mercadolibre.com"));
    }

    public void switchToCountryBranch(String arg0) {

        this.jsonArray.length();
        String countryCode = null;
        for (int i = 0; i < this.jsonArray.length(); i++) {
            JSONObject temp = this.jsonArray.getJSONObject(i);
            if (temp.get("country").toString().equalsIgnoreCase(arg0)) {
                countryCode = temp.get("countryCode").toString();
            }
        }

        Assert.assertFalse("There is no country code in json provide that match with country name \"".concat(arg0)
                .concat("\""), countryCode.isEmpty());
        Setup.getWait().waitUntilElementAppear(By.id(countryCode));
        Setup.getActions().moveToElement(getCountryCodeElement(countryCode)).click().perform();
    }

    private WebElement getCountryCodeElement(String countryCode) {
        WebElement countryElement = this.mercadoLibreInitPage.getCountryById(countryCode);
        Assert.assertTrue("Country element is not displayed", countryElement.isDisplayed());
        return countryElement;
    }

    public void userIsOnCategoryView(String submenu) {
        List<WebElement> listMenu = this.mercadoLibreHomePage.getMenuList().stream().
                filter(webElement -> webElement.getText().equalsIgnoreCase(submenu)).collect(Collectors.toList());

        Assert.assertTrue("There is not submenu with name equals to: '".concat(submenu).concat("'"), listMenu.size() > 0);
        listMenu.get(0).click();


    }
}
