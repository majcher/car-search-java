package pl.mmajcherski.carsearch.page;

import org.jbehave.web.selenium.WebDriverProvider;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CarSearchPage extends AbstractPage {

	@Autowired
	public CarSearchPage(WebDriverProvider driverProvider) {
		super(driverProvider);
	}

	public void open() {
		get("http://localhost:8080/");

		waitForSpinnerToDisappear();
	}

	public void carsMenu() {
		findElement(By.linkText("Cars")).click();
	}

	public void inputSearchText(String text) {
		findElement(By.id("search-input")).sendKeys(text);
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

}
