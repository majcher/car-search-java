package pl.mmajcherski.carsearch.page;

import org.jbehave.web.selenium.WebDriverProvider;

public class Pages {

	private WebDriverProvider driverProvider;
	private CarSearchPage carSearch;

	public Pages(WebDriverProvider driverProvider) {
		this.driverProvider = driverProvider;
	}

	public CarSearchPage carSearch() {
		if (carSearch == null) {
			carSearch = new CarSearchPage(driverProvider);
		}
		return carSearch;
	}

}
