package com.restassured.cucumber.options;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/java/com/restassured/features", 
plugin = {"json:target/jsonReports/cucumber-report.json", "pretty"}, 
monochrome= true,
glue = {"com.restassured.stepdefinitions"},tags="@Regression")

public class TestRunner {

}
