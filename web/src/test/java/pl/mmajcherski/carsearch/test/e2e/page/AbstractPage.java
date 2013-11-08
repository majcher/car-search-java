package pl.mmajcherski.carsearch.test.e2e.page;

import com.google.common.base.Function;
import org.jbehave.web.selenium.WebDriverPage;
import org.jbehave.web.selenium.WebDriverProvider;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class AbstractPage extends WebDriverPage {

	private static final int TIME_OUT_IN_SECONDS = 10;

	public AbstractPage(WebDriverProvider driverProvider) {
		super(driverProvider);
	}

	public void openRootPage() {
		get("http://localhost:8080/");

		waitForLogo();
		waitForSpinnerToDisappear();
	}

	protected void waitForLogo() {
		WebDriverWait wait = new WebDriverWait(getDriverProvider().get(), TIME_OUT_IN_SECONDS);
		wait.until(new Function<WebDriver, Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				WebElement header = driver.findElement(By.id("car-search-header"));
				return header.isDisplayed();
			}
		});
	}

	protected void waitForSpinnerToDisappear() {
		WebDriverWait wait = new WebDriverWait(getDriverProvider().get(), TIME_OUT_IN_SECONDS);
		wait.until(new Function<WebDriver, Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				WebElement spinner = driver.findElement(By.className("spinner"));
				return !spinner.isDisplayed();
			}
		});
	}

}
