package page.presto.pagefactoryclasses;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SearchPageFactory {
	WebDriver driver;
	
	//find search text box
	@FindBy(id="twotabsearchtextbox")
	WebElement searchBox;
	
	//find search button
	@FindBy (xpath="//*[@id=\"nav-search\"]/form/div[2]/div/input")
	WebElement searchButton;
	
	//find cart button
	@FindBy (id="nav-cart-count")
	WebElement cartButton;
	
	
	//driver set up
	public SearchPageFactory(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	
	//send input to search box e.g. inputs  =  "Headphone"
	public void sendInputText(String inputs) {
		searchBox.sendKeys(inputs);
	}
	
	//Perform click action on search button
	public void clickSearchButton() {
		searchButton.click();
	}
	
	//getcart total cart Items
	public String getCartButtonText() {
		return cartButton.getText();
	}
	
	//Perform Click action on cart button
	public void clickCartButton() {
		cartButton.click();
	}
	
	
	
	
}
