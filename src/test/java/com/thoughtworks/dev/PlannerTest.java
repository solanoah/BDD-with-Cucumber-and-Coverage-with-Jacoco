package com.thoughtworks.dev;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features={"src/test/resources"},
        format = { "pretty", "json:target/cucumber.json" },
        tags = { "~@ignore" })
public class PlannerTest {
}
