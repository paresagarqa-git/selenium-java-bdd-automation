@ui
Feature: Checkboxes
  As a user
  I want to tick and untick checkboxes
  So that I can choose options on a form

  @smoke
  Scenario: Toggle both checkboxes
    Given I am on the checkboxes page
    Then checkbox 1 should be unchecked
    And checkbox 2 should be checked
    When I check checkbox 1
    And I uncheck checkbox 2
    Then checkbox 1 should be checked
    And checkbox 2 should be unchecked
