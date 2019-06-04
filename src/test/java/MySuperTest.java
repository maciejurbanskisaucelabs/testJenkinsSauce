import org.junit.Assert;
import org.junit.BeforeClass;
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
    private static String buildTag;
    
    @BeforeClass
    public static void setupClass() {
        //If available add build tag. When running under Jenkins BUILD_TAG is automatically set.
        //You can set this manually on manual runs.
        buildTag = System.getenv("BUILD_TAG");
        if (buildTag == null) {
            buildTag = System.getenv("SAUCE_BUILD_NAME");
        }
    }

    @Test
    public void testJenkinsCaps() {
        DesiredCapabilities caps = new DesiredCapabilities();

        caps.setBrowserName(System.getenv("SELENIUM_BROWSER"));
        caps.setVersion(System.getenv("SELENIUM_VERSION"));
        caps.setCapability(CapabilityType.PLATFORM, System.getenv("SELENIUM_PLATFORM"));
        caps.setCapability("deviceOrientation", System.getenv("SELENIUM_DEVICE_ORIENTATION"));
        
        if (buildTag != null) {
            caps.setCapability("build", buildTag);
        }


        try {
            driver = new RemoteWebDriver(new URL(SAUCE_URL),caps);
            printSessionId();

            driver.get("https://google.com");

            Assert.assertTrue("Title is Google",driver.getTitle().equals("Google"));
            driver.quit();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }


    private void printSessionId() {

        String message = String.format("SauceOnDemandSessionID=%1$ss",
                (((RemoteWebDriver) driver).getSessionId()).toString());
        System.out.println(message);
    }
}
