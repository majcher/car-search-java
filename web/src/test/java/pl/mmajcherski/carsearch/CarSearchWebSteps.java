package pl.mmajcherski.carsearch;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import pl.mmajcherski.carsearch.page.CarSearch;
import pl.mmajcherski.carsearch.page.Pages;

public class CarSearchWebSteps {

	private final CarSearch carSearch;

	public CarSearchWebSteps(Pages pages) {
		this.carSearch = pages.carSearch();
	}

	@Given("is the default data set")
	public void defaultDataSet() {
	}

	@When("I execute a search with more than one search result")
	public void searchWithMoreThanOneResult() {
		carSearch.open();
		carSearch.inputSearchText("Ford");
		carSearch.search();
	}

	@Then("the page should contain for each found car the image, make, model, color and the price")
	public void pageShouldContainAllFoundCarsDetails() throws InterruptedException {
		carSearch.pageContainsCarImage();
		carSearch.pageContainsCarMake("Ford");
		carSearch.pageContainsCarModel("Mustang");
		carSearch.pageContainsCarPrice("23000 USD");
		carSearch.pageContainsCarColor("Persimmon red");
	}

}
