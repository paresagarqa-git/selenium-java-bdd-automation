package com.sagar.automation.stepdefinitions;

import io.cucumber.java.ParameterType;

/**
 * Custom Cucumber parameter types, so feature files can say "checked" / "unchecked"
 * and step methods still receive a boolean.
 */
public class ParameterTypes {

    @ParameterType("checked|unchecked")
    public boolean checkState(String value) {
        return value.equals("checked");
    }

    @ParameterType("check|uncheck")
    public boolean checkAction(String value) {
        return value.equals("check");
    }
}
