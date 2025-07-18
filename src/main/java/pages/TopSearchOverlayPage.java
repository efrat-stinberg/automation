package pages;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class TopSearchOverlayPage extends BasePage {

    @FindBy(xpath = "//*[@id='DeltaPlaceHolderMain']/header/div[1]/div/div/div[3]/ul/li[3]/a/em")
    private WebElement searchIcon;

    @FindBy(xpath = "//*[@id='ClalitNewMaster_ClalitSearchBoxUC_mainSearchField']")
    private WebElement searchInput;

    @FindBy(xpath = "//*[@id='searchButton']")
    private WebElement searchButton;

    @FindBy(xpath = "//*[@id='search_results_list']/div/ul/li[2]/div[1]")
    private List<WebElement> searchResults;

    public TopSearchOverlayPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void openTopSearch() {
        searchIcon.click();
    }

    public void searchFor(String text) {
        searchInput.clear();
        searchInput.sendKeys(text);
        searchButton.click();
    }

    public boolean searchResultsExist() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            wait.until(ExpectedConditions.visibilityOfAllElements(searchResults));
            return !searchResults.isEmpty();
        } catch (Exception e) {
            return false;
        }
    }
}
