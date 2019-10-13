package com.presto.testclasses;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import com.presto.utilities.Utilities;

import page.presto.pagefactoryclasses.SearchPageFactory;

public class TestCase001_SearchPage {
	public WebDriver driver;
	private String baseURL;
	SearchPageFactory searchPage;
	private static final Logger log = LogManager.getLogger(TestCase001_SearchPage.class.getName());
	// for expected cart count based on findings
	public int expectedCartcount = 0;

	@BeforeSuite
	public void beforeClass() throws Exception {
		log.info("********* Started TC001_SearchPage  **********");

		Utilities.setDriver(driver);
		baseURL = "https://www.amazon.com/";
		driver = new FirefoxDriver();

		// pass driver to constructor
		searchPage = new SearchPageFactory(driver);
		driver.manage().window().maximize();
	}

	@Test
	public void testAddBestSeller() {
		log.info(" Test testAddBestSeller started");
		driver.get(baseURL);
		searchPage.sendInputText("HeadPhones");
		searchPage.clickSearchButton();

		// find all Best Seller items using xpath
		// Logic of xpath - find ancestor of 'Best-Seller' and inside that find siblings
		// of 'a-section a-spacing-medium' and select a tag for an image product
		List<WebElement> bestSellerAllItems = driver.findElements(By.xpath(
				"//span[text()='Best Seller']/ancestor::div[@class='a-section a-spacing-medium']/div//following-sibling::span[@data-component-type='s-product-image']/a"));

		// create a list of href attribute from best seller web elements
		List<String> hrefList = new ArrayList<String>();
		for (WebElement singleItem : bestSellerAllItems) {
			// get the href attribute value from webElement
			String tempHref = singleItem.getAttribute("href");
			// add the href attribute value the list
			hrefList.add(tempHref);
		}
		//if no "Best Seller Items found then add log to the result"
		if(hrefList.size() < 1) {
			log.info("*******************No Best Seller Items Found for the search results*******************");
		}
		
		log.info(" List best Sellers total count found " + hrefList.size());

		// Add all items found to the list
		for (String sel : hrefList) {
			// visit each list href item
			driver.get(sel);

			WebDriverWait wait = new WebDriverWait(driver, 20);
			// Explicit wait until the add-to-cart-button visible and perform click action
			wait.until(ExpectedConditions.elementToBeClickable(By.id("add-to-cart-button"))).click();
			driver.navigate().refresh();
			// wait until you see 'Added to Cart' text visible on webpage
			By byXpath = By.xpath("//*[contains(text(),'Added to')]");
			WebElement myDynamicElement = (new WebDriverWait(driver, 15))
					.until(ExpectedConditions.presenceOfElementLocated(byXpath));
			expectedCartcount = hrefList.size();

			System.out.println(myDynamicElement.getText());
			// verify items added to cart
			Assert.assertEquals(myDynamicElement.getText(), "Added to Cart");
			log.info("------Item added to Cart---------");
			driver.navigate().refresh();
		}
		log.info(expectedCartcount + " Items added to Cart");
	}

	@Test
	public void verifyCartItems() {
		// get count of cart items by performing click action on element cart

		// get the cart count to compare the items total String cartCount =
		String ActualcartCount = searchPage.getCartButtonText();
		log.info("Final Cart count found " + ActualcartCount);
		// verify all 'Best Seller' items are added to cart
		Assert.assertEquals(expectedCartcount, Integer.parseInt(ActualcartCount));
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
		log.info("*********  Finished TC001_SearchPager **********");
	}
}
