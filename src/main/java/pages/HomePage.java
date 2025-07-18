package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage{

    @FindBy(id = "ClalitNewMaster_QuickLinksUC_rptQuickLinks_ctl03_nav_item_link")
    private WebElement serviceLocatorButton;

    public HomePage(WebDriver driver) {
        super(driver);
    }
    @FindBy(css = ".icon-search") // CSS לאייקון זכוכית מגדלת – עדכני לפי האתר
    private WebElement topSearchIcon;

    public void openTopSearch() {
        topSearchIcon.click();
    }

    public void clickServiceLocator() {
        serviceLocatorButton.click();
    }
}
