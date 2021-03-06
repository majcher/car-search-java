package pl.mmajcherski.carsearch.page;

import org.jbehave.web.selenium.WebDriverProvider;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.mmajcherski.carsearch.test.e2e.page.AbstractPage;

import java.util.List;

@Component
public class CarSearchPage extends AbstractPage {

	@Autowired
	public CarSearchPage(WebDriverProvider driverProvider) {
		super(driverProvider);
	}

	public void open() {
		get("http://localhost:8080/#/search");

		waitForLogo();
		waitForSpinnerToDisappear();
	}

	public boolean isOpen() {
		boolean hasSearchButton = findElement(By.id("search-button")).isDisplayed();
		boolean hasSearchByMakeInput = findElement(By.id("search-make")).isDisplayed();
		boolean hasSearchByModelInput = findElement(By.id("search-model")).isDisplayed();

		return hasSearchButton && hasSearchByMakeInput && hasSearchByModelInput;
	}

	public void carsMenu() {
		findElement(By.linkText("Cars")).click();
	}

	public void inputSearchMake(String make) {
		findElement(By.id("search-make")).sendKeys(make);
	}

	public void inputSearchModel(String model) {
		findElement(By.id("search-model")).sendKeys(model);
	}

	public void inputSearchColor(String color) {
		findElement(By.id("search-color")).sendKeys(color);
	}

	public String getSearchMakeInputText() {
		return findElement(By.id("search-make")).getAttribute("value");
	}

	public String getSearchModelInputText() {
		return findElement(By.id("search-model")).getAttribute("value");
	}

	public String getSearchColorInputText() {
		return findElement(By.id("search-color")).getAttribute("value");
	}

	public void search() {
		findElement(By.id("search-button")).click();

		waitForSpinnerToDisappear();
	}

	public int getFoundCarsSize() {
		return findElements(By.className("car-image")).size();
	}

	public void containsCarImageAtIndex(int index) {
		WebElement element = findElements(By.className("car-image")).get(index);
		if (element.getAttribute("src").isEmpty()) {
			throw new RuntimeException("No car image at index: " + index);
		}
	}

	public void containsCarMakeAtIndex(String make, int index) {
		if (!findElements(By.className("car-make")).get(index).getText().contains(make)) {
			 throw new RuntimeException("No car make: " + make + " at index: " + index);
		}
	}

	public void containsCarModelAtIndex(String model, int index) {
		if (!findElements(By.className("car-make")).get(index).getText().contains(model)) {
			throw new RuntimeException("No car model: " + model + " at index: " + index);
		}
	}

	public void containsCarPriceAtIndex(String price, int index) {
		if (!findElements(By.className("car-price")).get(index).getText().contains(price)) {
			throw new RuntimeException("No car price: " + price + " at index: " + index);
		}
	}

	public void containsCarColorAtIndex(String color, int index) {
		if (!findElements(By.className("car-color")).get(index).getText().contains(color)) {
			throw new RuntimeException("No car color: " + color + " at index: " + index);
		}
	}

	public void containsInformationMessage(String message) {
		List<WebElement> messageElements = findElements(By.id("car-search-info-message"));
		if (messageElements.size() != 1 || !messageElements.get(0).getText().contains(message)) {
			throw new RuntimeException("No information message: '" + message + "' shown");
		}
	}

	public void clickBackToSearchPageButton() {
		findElement(By.id("back-to-search-button")).click();

		waitForSpinnerToDisappear();
	}
}
