import org.junit.Test;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class MySuperTest {

    RemoteWebDriver driver;
    private final String SAUCE_USERNAME = System.getenv("SAUCE_USERNAME");
    private final String SAUCE_ACCESS_KEY = System.getenv("SAUCE_ACCESS_KEY");
    private final String SAUCE_URL = String.format("https://%s:%s@ondemand.saucelabs.com:443/wd/hub",SAUCE_USERNAME,SAUCE_ACCESS_KEY);


    @Test
    public void testJenkinsCaps() {
        DesiredCapabilities caps = new DesiredCapabilities();

        caps.setBrowserName(System.getenv("SELENIUM_BROWSER"));
        caps.setVersion(System.getenv("SELENIUM_VERSION"));
        caps.setCapability(CapabilityType.PLATFORM, System.getenv("SELENIUM_PLATFORM"));


        try {
            driver = new RemoteWebDriver(new URL(SAUCE_URL),caps);
            printSessionId();

            driver.get("https://google.com");

            System.out.println(driver.getTitle());
            driver.quit();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }


    private void printSessionId() {

        String message = String.format("SauceOnDemandSessionID=%1$s job-name=%2$s",
                (((RemoteWebDriver) driver).getSessionId()).toString(), "testJenkinsCaps");
        System.out.println(message);
    }
}