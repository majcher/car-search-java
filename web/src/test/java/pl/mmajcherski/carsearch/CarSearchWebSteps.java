package pl.mmajcherski.carsearch;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import pl.mmajcherski.carsearch.page.CarSearchPage;
import pl.mmajcherski.carsearch.page.Pages;

import static org.fest.assertions.api.Assertions.assertThat;

public class CarSearchWebSteps {

	private final CarSearchPage carSearch;

	public CarSearchWebSteps(Pages pages) {
		this.carSearch = pages.carSearch();
	}

	@Given("is the default data set")
	public void defaultDataSet() {
	}

	@When("I execute a search with more than one search result")
	public void searchWithMoreThanOneResult() {
		carSearch.open();
		carSearch.inputSearchText("Audi");
		carSearch.search();
	}

	@Then("the page should contain for each found car the image, make, model, color and the price")
	public void pageShouldContainAllFoundCarsDetails() throws InterruptedException {
		int foundCarsSize = carSearch.getFoundCarsSize();
		assertThat(foundCarsSize).isEqualTo(2);

		carSearch.containsCarImageAtIndex(0);
		carSearch.containsCarMakeAtIndex("Audi", 0);
		carSearch.containsCarModelAtIndex("A4", 0);
		carSearch.containsCarPriceAtIndex("23000 USD", 0);
		carSearch.containsCarColorAtIndex("British racing green", 0);

		carSearch.containsCarImageAtIndex(1);
		carSearch.containsCarMakeAtIndex("Audi", 1);
		carSearch.containsCarModelAtIndex("A4", 1);
		carSearch.containsCarPriceAtIndex("23000 USD", 1);
		carSearch.containsCarColorAtIndex("Scarlet red", 1);
	}

}
