package com.github.admangarakov.guestbook.view;

import com.vaadin.flow.component.button.testbench.ButtonElement;
import com.vaadin.flow.component.textfield.testbench.TextFieldElement;
import com.vaadin.testbench.TestBenchTestCase;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeDriver;

public class FormTest extends TestBenchTestCase {

    @Before
    public void setup() throws Exception {
        // Create a new browser instance
        setDriver(new ChromeDriver());
        // Open the application
        getDriver().get("http://localhost:8080/");
    }

    @Test
    public void clickButton() {
        // Find the first button (<vaadin-button>) on the page
        ButtonElement button = $(ButtonElement.class).first();

        $(TextFieldElement.class).id("username").setValue("adman");
        $(TextFieldElement.class).id("email").setValue("123@mail.com");
        $(TextFieldElement.class).id("homepage").setValue("http://localhost:8080/");
        $(TextFieldElement.class).id("text").setValue("some text");
        $(TextFieldElement.class).id("captcha").setValue("smwm");

        button.click();
    }

    @After
    public void tearDown() throws Exception {
        getDriver().quit();
    }

}