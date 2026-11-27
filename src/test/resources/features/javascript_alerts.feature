@ui
Feature: JavaScript alerts
  As a user
  I want to respond to browser alert, confirm and prompt dialogs

  Background:
    Given I am on the JavaScript alerts page

  @smoke
  Scenario: Accept a simple JS alert
    When I click the "Click for JS Alert" button
    Then the alert should say "I am a JS Alert"
    When I accept the alert
    Then the result should be "You successfully clicked an alert"

  @regression
  Scenario: Enter text into a JS prompt
    When I click the "Click for JS Prompt" button
    And I type "Selenium BDD" into the prompt and accept it
    Then the result should be "You entered: Selenium BDD"
