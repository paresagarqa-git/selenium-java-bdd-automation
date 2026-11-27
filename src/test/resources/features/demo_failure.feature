@demo
Feature: Demo failure
  Fails on purpose, so you can see the screenshot and video that a failed scenario produces.
  It is excluded from normal runs. Run it with:
    mvn test -Dcucumber.filter.tags="@demo"
  or in CI: Actions > Selenium BDD Tests > Run workflow, tags = @demo

  Scenario: Login shows the wrong welcome message (intentional failure)
    Given I am on the login page
    When I log in with username "tomsmith" and password "SuperSecretPassword!"
    Then I should land on the secure area
    And I should see the flash message "Welcome back, Sagar!"
