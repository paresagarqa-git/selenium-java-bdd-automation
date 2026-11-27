@login
Feature: Login
  As a registered user
  I want to log in to the secure area
  So that I can access protected content

  Background:
    Given I am on the login page

  @smoke
  Scenario: Successful login with valid credentials
    When I log in with username "tomsmith" and password "SuperSecretPassword!"
    Then I should land on the secure area
    And I should see the flash message "You logged into a secure area!"

  @smoke
  Scenario: Logout returns the user to the login page
    Given I log in with username "tomsmith" and password "SuperSecretPassword!"
    When I log out
    Then I should be on the login page
    And I should see the flash message "You logged out of the secure area!"

  @regression
  Scenario Outline: Login fails with invalid credentials
    When I log in with username "<username>" and password "<password>"
    Then I should see the flash message "<message>"
    And I should be on the login page

    Examples:
      | username    | password             | message                   |
      | invaliduser | SuperSecretPassword! | Your username is invalid! |
      | tomsmith    | wrongpassword        | Your password is invalid! |
