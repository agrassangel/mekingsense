import io.cucumber.junit.platform.engine.Constants;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.platform.suite.api.ConfigurationParameter;

import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;


@RunWith(io.cucumber.junit.Cucumber.class)
@CucumberOptions(
        features = "src/test/resources",
        glue = ".",
        publish = true
        ,
        plugin = {
                "pretty",
//                "io.qameta.allure.cucumber4jvm.AllureCucumber4Jvm",
                "json:build/cucumber-report/cucumber.json",
                "html:build/cucumber-report/cucumber.html",
                "junit:build/cucumber-report/cucumber.xml"}
)
@CucumberContextConfiguration

@ConfigurationParameter(key = Constants.GLUE_PROPERTY_NAME, value = ".")
public class TestRunner {


}