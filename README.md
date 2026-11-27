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
| Chrome, Firefox, Edge | Cross-browser runs, headless in CI |
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

# Cross-browser: chrome (default), firefox or edge
mvn test -Dbrowser=firefox
mvn test -Dbrowser=edge -Dheadless=true

# Parallel: scenarios run on 4 threads by default, each with its own browser
mvn test -Dthreads=8
mvn test -Dthreads=1     # one at a time, easier to debug

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

## Parallel execution

`TestRunner` overrides Cucumber's `scenarios()` DataProvider with `parallel = true`, and Surefire passes
`dataproviderthreadcount=${threads}` to TestNG. Every scenario gets its own browser because
`DriverFactory` keeps the WebDriver in a `ThreadLocal`, and PicoContainer creates a fresh `TestContext`
(and fresh page objects) per scenario. The console prints `[thread] browser | scenario` so you can see it.

## CI

`.github/workflows/selenium-tests.yml` runs on every push to `main`, every pull request, and on demand
(**Actions → Selenium BDD Tests → Run workflow**, with an optional tag expression and thread count).

A **browser matrix** runs Chrome, Firefox and Edge as three parallel jobs, and inside each job the
scenarios run in parallel threads. Each browser uploads its own `cucumber-report-<browser>` and
`surefire-reports-<browser>`; `failure-screenshots-<browser>` is uploaded when a test fails.
