package com.sagar.automation.stepdefinitions;

import com.sagar.automation.context.TestContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class UiInteractionSteps {

    private final TestContext context;

    public UiInteractionSteps(TestContext context) {
        this.context = context;
    }

    // ---------- Checkboxes ----------

    @Given("I am on the checkboxes page")
    public void iAmOnTheCheckboxesPage() {
        context.checkboxesPage().open();
    }

    @Then("checkbox {int} should be {checkState}")
    public void checkboxShouldBe(int position, boolean checked) {
        assertEquals(context.checkboxesPage().isChecked(position), checked,
                "Checkbox " + position + " state");
    }

    @When("I {checkAction} checkbox {int}")
    public void iSetCheckbox(boolean check, int position) {
        context.checkboxesPage().setChecked(position, check);
    }

    // ---------- Dropdown ----------

    @Given("I am on the dropdown page")
    public void iAmOnTheDropdownPage() {
        context.dropdownPage().open();
    }

    @When("I select {string} from the dropdown")
    public void iSelectFromTheDropdown(String option) {
        context.dropdownPage().selectByVisibleText(option);
    }

    @Then("{string} should be the selected option")
    public void shouldBeTheSelectedOption(String option) {
        assertEquals(context.dropdownPage().getSelectedOption(), option);
    }

    // ---------- Dynamic loading ----------

    @Given("I am on the dynamic loading page")
    public void iAmOnTheDynamicLoadingPage() {
        context.dynamicLoadingPage().open();
    }

    @Then("the hidden text should not be visible")
    public void theHiddenTextShouldNotBeVisible() {
        assertFalse(context.dynamicLoadingPage().isFinishTextDisplayed());
    }

    @When("I click the Start button")
    public void iClickTheStartButton() {
        context.dynamicLoadingPage().clickStart();
    }

    @Then("I should eventually see the text {string}")
    public void iShouldEventuallySeeTheText(String expected) {
        assertEquals(context.dynamicLoadingPage().waitForFinishText(), expected);
    }

    // ---------- JavaScript alerts ----------

    @Given("I am on the JavaScript alerts page")
    public void iAmOnTheJavaScriptAlertsPage() {
        context.javaScriptAlertsPage().open();
    }

    @When("I click the {string} button")
    public void iClickTheButton(String label) {
        context.javaScriptAlertsPage().clickButton(label);
    }

    @Then("the alert should say {string}")
    public void theAlertShouldSay(String expected) {
        assertEquals(context.javaScriptAlertsPage().getAlertText(), expected);
    }

    @When("I accept the alert")
    public void iAcceptTheAlert() {
        context.javaScriptAlertsPage().acceptAlert();
    }

    @When("I dismiss the alert")
    public void iDismissTheAlert() {
        context.javaScriptAlertsPage().dismissAlert();
    }

    @When("I type {string} into the prompt and accept it")
    public void iTypeIntoThePromptAndAcceptIt(String text) {
        context.javaScriptAlertsPage().typeInPromptAndAccept(text);
    }

    @Then("the result should be {string}")
    public void theResultShouldBe(String expected) {
        assertEquals(context.javaScriptAlertsPage().getResult(), expected);
    }

    @Then("the result should contain {string}")
    public void theResultShouldContain(String expected) {
        String actual = context.javaScriptAlertsPage().getResult();
        assertTrue(actual.contains(expected), "Result was: " + actual);
    }
}
