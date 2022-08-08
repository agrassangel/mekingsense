package search;

import general.Setup;
import io.cucumber.java.en.*;

import java.util.HashMap;
import java.util.Map;

public class SearchStepDefinitions {
    private SearchSteps searchSteps;

    public SearchStepDefinitions() {
        searchSteps = new SearchSteps();

    }

    //    This method need to be moved to a common StepDefinition


    @And("^User has the required query filters (.*?)$")
    public void userHasTheRequiredQueryFilters(String arg0) {
        Map<String, String> filters = new HashMap<String, String>();
        String listPairs[] = arg0.replace("\"", "").split(",");
        for (String pair : listPairs) {
            String data[] = pair.split(":");
            String key = data[0].trim();
            String value = data[1].trim();
            filters.put(key, value);
        }
        Setup.setKeyValueStore("filtersQuery", filters);

    }

    @When("User set the required query filters")
    public void userSetTheRequiredQueryFilters() {
        Map<String, String> filters = (Map<String, String>) Setup.getValueStore("filtersQuery");
        this.searchSteps.openViewMoreOption();
        this.searchSteps.userSetTheRequiredQueryFilters(filters);

    }


    @Then("System will show the result according to the criteria")
    public void systemWillShowTheResultAccordingToTheCriteria() {
        this.searchSteps.validateResults();

    }


}
