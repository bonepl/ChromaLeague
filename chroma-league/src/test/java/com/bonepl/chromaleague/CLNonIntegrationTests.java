package com.bonepl.chromaleague;

import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@Suite
@SuiteDisplayName("Non integration tests")
@SelectPackages({
        "com.bonepl.chromaleague.rest",
        "com.bonepl.chromaleague.tasks"
})
public class CLNonIntegrationTests {
}
