package io.hawt.tests.features.stepdefinitions.panel;

import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.hawt.tests.features.pageobjects.pages.preferences.PreferencesPage;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;

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

    @When("User toggles the show vertical navigation field")
    public void userTogglesTheField() {
        $(By.cssSelector("main span.pf-v5-c-switch__toggle")).shouldBe(interactable).click();
    }

    @When("User clicks on Reset button")
    public void userClicksOnButton() {
         $(By.cssSelector("main button.pf-m-danger")).shouldBe(interactable).click();
    }

    @And("User confirms resetting the setting of Hawtio")
    public void userConfirmsResettingTheSettingOfHawtio() {
        $("[data-testid='reset-settings-modal']")
            .shouldBe(visible)
            .shouldHave(text("You are about to reset all the Hawtio settings."));
        $(By.cssSelector(".pf-v5-c-modal-box__footer")).$("[data-testid='reset-btn']").shouldBe(visible, enabled).click();
    }

    @Then("User is presented with a successful alert message")
    public void userIsPresentedWithASuccessfullAlertMessage() {
        $(By.cssSelector("main .pf-v5-c-alert")).shouldBe(visible);
    }



}
