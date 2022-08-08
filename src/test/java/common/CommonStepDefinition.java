package common;

import io.cucumber.java.en.*;

public class CommonStepDefinition {
    private CommonSteps commonSteps;

    public CommonStepDefinition() {
        commonSteps = new CommonSteps();

    }

    @Given("User is on mercado Libre web page")
    public void userIsOnMercadoLibreWebPage() {
        commonSteps.userIsOnMercadoLibreWebPage();
    }

    @Given("The user is in {string} branch")
    public void theUserIsInBranch(String arg0) {
        commonSteps.switchToCountryBranch(arg0);
    }

    @And("User is on category {string} view")
    public void userIsOnCategoryView(String arg0) {
        commonSteps.userIsOnCategoryView(arg0);

    }
}
