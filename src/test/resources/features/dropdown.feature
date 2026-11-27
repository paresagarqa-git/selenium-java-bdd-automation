@ui
Feature: Dropdown
  As a user
  I want to pick a value from a dropdown list

  @regression
  Scenario Outline: Select "<option>" from the dropdown
    Given I am on the dropdown page
    When I select "<option>" from the dropdown
    Then "<option>" should be the selected option

    Examples:
      | option   |
      | Option 1 |
      | Option 2 |
