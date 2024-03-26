package io.hawt.tests.features.hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.hawt.tests.features.pageobjects.ScreenRecorder.MyScreenRecorder;
import org.openqa.selenium.OutputType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codeborne.selenide.Selenide;

import io.cucumber.java.AfterStep;
import io.cucumber.java.Scenario;

public class ScreenshotHook {

    private static final Logger LOG = LoggerFactory.getLogger(ScreenshotHook.class);
    
    @Before
    public void Record(Scenario s){
        MyScreenRecorder.startRecording(s.getName());
    }

    @After
    public void endRecord() {
        MyScreenRecorder.stopRecording();
    }

    @AfterStep
    public void afterStep(Scenario scenario) {
        if (scenario.isFailed()) {
            try {
                scenario.attach(Selenide.screenshot(OutputType.BYTES), "image/png", "screenshot");
            } catch (Exception e) {
                LOG.error("Failed to take a screenshoot", e);
            }
        }
    }
}
