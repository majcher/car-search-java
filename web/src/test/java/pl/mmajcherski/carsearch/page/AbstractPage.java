package pl.mmajcherski.carsearch.page;

import com.google.common.base.Function;
import org.jbehave.web.selenium.WebDriverPage;
import org.jbehave.web.selenium.WebDriverProvider;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AbstractPage extends WebDriverPage {

	private static final int TIME_OUT_IN_SECONDS = 10;

	public AbstractPage(WebDriverProvider driverProvider) {
		super(driverProvider);
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
