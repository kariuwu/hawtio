package io.hawt.tests.features.stepdefinitions.panel;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.hawt.tests.features.pageobjects.pages.preferences.PreferencesPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.webdriver;

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


    @When("User slides log level")
    public void userSlidesLogLevel() {
        SelenideElement thumb = $("div.pf-v5-c-slider__thumb");
        SelenideElement tickDebug = $("div.pf-v5-c-slider__step:nth-child(5) > div:nth-child(1)");
        SelenideElement tickOff = $("div.pf-v5-c-slider__step:nth-child(1) > div:nth-child(1)");
        Selenide.actions()
            .clickAndHold(thumb)
            .dragAndDrop(tickOff, tickDebug)
            .perform();
    }


    @Then("User adds child logger")
    public void userAddsChildLogger() {
        SelenideElement loggAddSvg = $("span.pf-v5-c-menu-toggle__controls:nth-child(2) > span:nth-child(1) > svg:nth-child(1)");
        loggAddSvg.click();
        final By childLogList = By.cssSelector(".pf-v5-c-menu__list");
        $(childLogList).$(byText("hawtio-camel")).click();
    }


    @And("User sees added child logger")
    public void userSeesAddedChildLogger() {
        $(".pf-v5-c-data-list__item-content").$(byText("hawtio-camel")).shouldBe(visible);
    }


    @And("User is able to delete child logger")
    public void userIsAbleToDeleteChildLogger() {
        $(".pf-v5-c-data-list__item-action > button:nth-child(1)").click();
    }

}
