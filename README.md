# Selenium Java BDD Automation

[![Selenium BDD Tests](https://github.com/paresagarqa-git/selenium-java-bdd-automation/actions/workflows/selenium-tests.yml/badge.svg)](https://github.com/paresagarqa-git/selenium-java-bdd-automation/actions/workflows/selenium-tests.yml)

A UI test automation framework built with **Selenium WebDriver**, **Java**, **Cucumber (BDD)** and **TestNG**, using the **Page Object Model**.
Tests run against [the-internet.herokuapp.com](https://the-internet.herokuapp.com), a stable practice site for automation.

## Tech stack

| Tool | Purpose |
|------|---------|
| Java 17 + Maven | Language and build |
| Selenium 4 | Browser automation |
| Cucumber 7 | BDD: Gherkin feature files + step definitions |
| TestNG | Test runner (via `cucumber-testng`) |
| Cucumber PicoContainer | Dependency injection, shares state between step classes |
| WebDriverManager | Downloads the right browser driver automatically |
| GitHub Actions | CI: runs headless Chrome on every push and PR |

## Project structure

```
src/test/
├── java/com/sagar/automation/
│   ├── config/          ConfigReader: reads config.properties, -D overrides win
│   ├── context/         TestContext: one per scenario, holds page objects
│   ├── driver/          DriverFactory: ThreadLocal WebDriver (chrome/firefox/edge, headless)
│   ├── hooks/           Hooks: start browser, screenshot on failure, quit
│   ├── pages/           Page objects (BasePage + one class per page)
│   ├── runners/         TestRunner: Cucumber + TestNG entry point
│   ├── stepdefinitions/ Glue code that maps Gherkin steps to page actions
│   └── utils/           ScreenshotUtil
└── resources/
    ├── config/config.properties
    └── features/        *.feature files (Gherkin)
```

## Scenarios covered

| Feature | Scenarios | Tags |
|---------|-----------|------|
| Login | Valid login, logout, invalid username, invalid password | `@login @smoke @regression` |
| Checkboxes | Toggle both checkboxes | `@ui @smoke` |
| Dropdown | Select Option 1 / Option 2 | `@ui @regression` |
| Dynamic loading | Hidden element appears after loading (explicit wait) | `@ui @regression` |
| JavaScript alerts | Accept alert, type into a prompt | `@ui @smoke @regression` |

## Running the tests

Prerequisites: JDK 17+, Maven 3.8+, Google Chrome.

```bash
# Everything, with a visible browser
mvn test

# Headless (what CI uses)
mvn test -Dheadless=true

# Only some scenarios, by tag
mvn test -Dcucumber.filter.tags="@smoke"
mvn test -Dcucumber.filter.tags="@login and not @regression"

# Another browser
mvn test -Dbrowser=firefox

# Check every step has a definition, without opening a browser
mvn test -Dcucumber.execution.dry-run=true
```

Any key in `config.properties` can be overridden with `-Dkey=value`.

## Reports

After a run, open:

- `target/cucumber-reports/cucumber.html`: Cucumber HTML report (failed scenarios include an embedded screenshot)
- `target/screenshots/`: PNG screenshot for every failed scenario
- `target/cucumber-reports/rerun.txt`: list of failed scenarios
- `target/surefire-reports/`: TestNG results

## CI

`.github/workflows/selenium-tests.yml` runs on every push to `main`, every pull request, and on demand
(**Actions → Selenium BDD Tests → Run workflow**, with an optional tag expression).
The Cucumber report is uploaded as an artifact on every run; screenshots are uploaded when a test fails.
