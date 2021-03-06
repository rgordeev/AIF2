package io.aif.language.common.settings;

import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;

public class ISettingsTest {

    @Test(groups = "unit-tests")
    public void testGetVersion() throws Exception {
        // input parameter

        // mocks

        // expected result
        final String expectedResult = "1.2.0-alpha4";

        // creating instances
        final ISettings testInstance = ISettings.SETTINGS;

        // execution test
        final String actualResult = testInstance.getVersion();

        // asserts
        assertEquals(actualResult, expectedResult);

        // verify

    }
}