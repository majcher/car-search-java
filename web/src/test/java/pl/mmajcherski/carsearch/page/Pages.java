package pl.mmajcherski.carsearch.page;

import org.jbehave.web.selenium.WebDriverProvider;

public class Pages {

	private WebDriverProvider driverProvider;
	private CarSearch carSearch;

	public Pages(WebDriverProvider driverProvider) {
		this.driverProvider = driverProvider;
	}

	public CarSearch carSearch() {
		if (carSearch == null) {
			carSearch = new CarSearch(driverProvider);
		}
		return carSearch;
	}

}
