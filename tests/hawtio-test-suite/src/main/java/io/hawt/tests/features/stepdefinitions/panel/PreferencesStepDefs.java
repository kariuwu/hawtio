package io.hawt.tests.features.stepdefinitions.panel;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.hawt.tests.features.pageobjects.pages.preferences.PreferencesPage;

public class PreferencesStepDefs {
    private final PreferencesPage preferencesPage = new PreferencesPage();

    @When("^User is on \"([^\"]*)\" tab of Preferences page$")
    public void userIsOnTabOfPreferencesPage(String tabName) {
        preferencesPage.switchTab(tabName);
    }

    @Then("The content of Preferences page is open$")
    public void theContentOfPreferencesPageIsOpen() {
        preferencesPage.checkContent();
    }
}
