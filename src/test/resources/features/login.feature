@login
Feature: Login
  As a registered user
  I want to log in to the secure area
  So that I can access protected content

  Credentials are kept in src/test/resources/testdata/users.json and
  referenced here by key, so this file describes behaviour, not data.

  Background:
    Given I am on the login page

  @smoke
  Scenario: Successful login with valid credentials
    When I log in as the "valid" user
    Then I should land on the secure area
    And I should see the flash message "You logged into a secure area!"

  @smoke
  Scenario: Logout returns the user to the login page
    Given I log in as the "valid" user
    When I log out
    Then I should be on the login page
    And I should see the flash message "You logged out of the secure area!"

  @regression @negative
  Scenario Outline: Login is rejected for the "<user>" user
    When I log in as the "<user>" user
    Then I should see the flash message "<message>"
    And I should be on the login page

    Examples:
      | user               | message                   |
      | invalid_username   | Your username is invalid! |
      | invalid_password   | Your password is invalid! |
      | empty_username     | Your username is invalid! |
      | empty_password     | Your password is invalid! |
      | empty_both         | Your username is invalid! |
      | uppercase_username | Your username is invalid! |
      | uppercase_password | Your password is invalid! |
      | sql_injection      | Your username is invalid! |

