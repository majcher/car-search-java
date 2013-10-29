package pl.mmajcherski.carsearch.page;

import org.jbehave.web.selenium.WebDriverProvider;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.concurrent.TimeUnit;

public class CarSearch extends AbstractPage {

	public CarSearch(WebDriverProvider driverProvider) {
		super(driverProvider);
	}

	public void open() {
		get("http://localhost:8080/");
	}

	public void carsMenu() {
		findElement(By.linkText("Cars")).click();
	}

	public void inputSearchText(String text) {
		findElement(By.id("search-input")).sendKeys(text);
	}

	public void search() {
		findElement(By.id("search-button")).click();
	}

	public void pageContainsCarImage() {
		WebElement element = findElements(By.className("car-image")).get(0);
		if (element.getAttribute("src").isEmpty()) {
			throw new RuntimeException("No car image");
		}
	}

	public void pageContainsCarMake(String make) {
		if (!findElements(By.className("car-make")).get(0).getText().contains(make)) {
			 throw new RuntimeException("No car make: " + make);
		}
	}

	public void pageContainsCarModel(String model) {
		if (!findElements(By.className("car-make")).get(0).getText().contains(model)) {
			throw new RuntimeException("No car model: " + model);
		}
	}

	public void pageContainsCarPrice(String price) {
		if (!findElements(By.className("car-price")).get(0).getText().contains(price)) {
			throw new RuntimeException("No car price: " + price);
		}
	}

	public void pageContainsCarColor(String color) {
		if (!findElements(By.className("car-color")).get(0).getText().contains(color)) {
			throw new RuntimeException("No car color: " + color);
		}
	}

}
