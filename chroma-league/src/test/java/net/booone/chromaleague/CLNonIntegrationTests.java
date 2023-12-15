package net.booone.chromaleague;

import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@Suite
@SuiteDisplayName("Non integration tests")
@SelectPackages({
        "net.booone.chromaleague.rest",
        "net.booone.chromaleague.tasks"
})
public class CLNonIntegrationTests {
}
