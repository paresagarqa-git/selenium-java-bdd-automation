@ui
Feature: Dynamic loading
  As a user
  I want content that loads after a delay to appear
  So that the test proves explicit waits work

  @regression
  Scenario: Hidden element becomes visible after loading
    Given I am on the dynamic loading page
    Then the hidden text should not be visible
    When I click the Start button
    Then I should eventually see the text "Hello World!"
