package drivers;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverProvider;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.Platform;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.RemoteWebDriver;

import javax.annotation.Nonnull;
import java.net.MalformedURLException;
import java.net.URL;

import static com.codeborne.selenide.Browsers.*;
import static utils.PropertyLoader.loadSystemPropertyOrDefault;

@Slf4j
public class CustomDriverProvider implements WebDriverProvider {

    public final static String BROWSER = "browser";
    public final static String REMOTE_URL = "remoteUrl";
    public final static String WINDOW_WIDTH = "width";
    public final static String WINDOW_HEIGHT = "height";
    public final static String VERSION_LATEST = "latest";
    public final static String LOCAL = "local";
    public final static String SELENOID = "selenoid";
    public final static int DEFAULT_WIDTH = 1920;
    public final static int DEFAULT_HEIGHT = 1080;

    private String[] options = loadSystemPropertyOrDefault("options", "").split(" ");

    @Nonnull
    @Override
    public WebDriver createDriver(@Nonnull Capabilities capabilities) {
        Configuration.browserSize = String.format("%sx%s", loadSystemPropertyOrDefault(WINDOW_WIDTH, DEFAULT_WIDTH),
                loadSystemPropertyOrDefault(WINDOW_HEIGHT, DEFAULT_HEIGHT));
        String expectedBrowser = loadSystemPropertyOrDefault(BROWSER, CHROME);
        String remoteUrl = loadSystemPropertyOrDefault(REMOTE_URL, LOCAL);

//        log.info("remoteUrl=" + remoteUrl + " expectedBrowser= " + expectedBrowser + " BROWSER_VERSION=" + System.getProperty(CapabilityType.BROWSER_VERSION));

        switch (expectedBrowser.toLowerCase()) {
            case (FIREFOX):
                return LOCAL.equalsIgnoreCase(remoteUrl) ? createFirefoxDriver(capabilities) : getRemoteDriver(getFirefoxDriverOptions(capabilities), remoteUrl);
            default:
                return LOCAL.equalsIgnoreCase(remoteUrl) ? createChromeDriver(capabilities) : getRemoteDriver(getChromeDriverOptions(capabilities), remoteUrl);
        }
    }

    private WebDriver getRemoteDriver(MutableCapabilities capabilities, String remoteUrl) {
//        log.info("---------------run Remote Driver---------------------");
        Boolean isSelenoidRun = loadSystemPropertyOrDefault(SELENOID, true);
        if (isSelenoidRun) {
            capabilities.setCapability("enableVNC", true);
        }
        try {
            return new RemoteWebDriver(new URL(remoteUrl), capabilities);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    private WebDriver createFirefoxDriver(Capabilities capabilities) {
        return new FirefoxDriver(getFirefoxDriverOptions(capabilities));
    }

    private WebDriver createChromeDriver(Capabilities capabilities) {
        return new ChromeDriver(getChromeDriverOptions(capabilities));
    }

    private FirefoxOptions getFirefoxDriverOptions(Capabilities capabilities) {
//        log.info("---------------Firefox Driver---------------------");
        var firefoxOptions = !options[0].equals("") ? new FirefoxOptions().addArguments(options) : new FirefoxOptions();
        firefoxOptions.setCapability(CapabilityType.BROWSER_VERSION, loadSystemPropertyOrDefault(CapabilityType.BROWSER_VERSION, VERSION_LATEST));
        firefoxOptions.merge(capabilities);
        return firefoxOptions;
    }

    private ChromeOptions getChromeDriverOptions(Capabilities capabilities) {
//        log.info("---------------Chrome Driver---------------------");
        var chromeOptions = !options[0].equals("") ? new ChromeOptions().addArguments(options) : new ChromeOptions();
        chromeOptions.setCapability(CapabilityType.BROWSER_VERSION, loadSystemPropertyOrDefault(CapabilityType.BROWSER_VERSION, VERSION_LATEST));
        chromeOptions.merge(capabilities);
        return chromeOptions;
    }
}
