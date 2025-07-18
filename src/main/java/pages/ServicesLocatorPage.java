package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class ServicesLocatorPage extends BasePage{

    @FindBy(id = "pharmacies")
    private WebElement pharmacyTab;

    @FindBy(how = How.XPATH, using = "//*[@id='services_book_doctors']/div[2]/div[2]/div/div/label")
    private WebElement openNowCheckbox;

    @FindBy(how = How.XPATH, using = "//*[@id='lobby_search']/div/div/div/fieldset[2]/div/a")
    private WebElement searchButton;

    @FindBy(css = ".result") // עדכני בהתאם ל־class של תוצאות
    private List<WebElement> results;

    public ServicesLocatorPage(WebDriver driver) {
        super(driver);
    }

    public void clickPharmacyTab() {
        pharmacyTab.click();
    }

    public void checkOpenNow() {
        if (!openNowCheckbox.isSelected()) {
            openNowCheckbox.click();
        }
    }

    public void clickSearchButton() {
        searchButton.click();
    }


    public boolean resultsExist() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        // מחכה שתופיע לפחות תוצאה אחת
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='search_results_list']/div/div/ul/li/div[1]")));

        List<WebElement> results = driver.findElements(By.xpath("//*[@id='search_results_list']/div/div/ul/li/div[1]"));
        return !results.isEmpty();
    }
}
