package tests;

import enums.SearchTerm;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.*;

import java.time.Duration;

public class ClalitPharmacyTest {

    private WebDriver driver;
    private HomePage homePage;
    private ServicesLocatorPage servicesPage;
    private TopSearchOverlayPage topSearchOverlayPage;

    @BeforeClass
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize(); // מגדיל את הדפדפן
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://www.clalit.co.il/he/Pages/default.aspx");

        homePage = new HomePage(driver);
        servicesPage = new ServicesLocatorPage(driver);
        topSearchOverlayPage = new TopSearchOverlayPage(driver);
    }

    @Test(priority = 1)
    public void testPharmacySearchFromServicesPage() {
        homePage.clickServiceLocator();
        servicesPage.clickPharmacyTab();
        servicesPage.checkOpenNow();
        servicesPage.clickSearchButton();

        Assert.assertTrue(servicesPage.resultsExist(), " No search results found in service locator.");
    }

    @Test(priority = 2)
    public void testTopSearchFunctionality() {
        driver.get("https://www.clalit.co.il/he/Pages/default.aspx"); // חזרה לדף הבית
        homePage.openTopSearch();
        topSearchOverlayPage.searchFor(SearchTerm.PHARMACY.getValue());

        Assert.assertTrue(topSearchOverlayPage.searchResultsExist(), " No top search results found.");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
