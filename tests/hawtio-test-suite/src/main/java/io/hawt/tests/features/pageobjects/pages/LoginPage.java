package io.hawt.tests.features.pageobjects.pages;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;

/**
 * Represents a Login page.
 */
public class LoginPage {
    private final static SelenideElement loginDiv = $("div.pf-c-login");
    private final static SelenideElement loginInput = $("#pf-login-username-id");
    private final static SelenideElement passwordInput = $("#pf-login-password-id");
    private final static SelenideElement loginButton = $("button[type='submit']");

    /**
     * Login to hawtio as given user with given password.
     */
    public void login(String username, String password) throws Exception {
        if (WebDriverRunner.url().contains("login")) {
            loginDiv.shouldBe(visible).should(exist);
            loginInput.shouldBe(editable).setValue(username);
            passwordInput.shouldBe(editable).setValue(password);
            loginButton.shouldBe(enabled).click();
        }
    }

    /**
     * Check whether the Login page is open and active
     */
    public void loginPageIsOpened() throws Exception {
        loginInput.shouldBe(editable).should(exist);
        passwordInput.shouldBe(editable);
        loginButton.shouldBe(enabled);
    }
}
