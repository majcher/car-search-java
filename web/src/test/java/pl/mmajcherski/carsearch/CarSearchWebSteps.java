package pl.mmajcherski.carsearch;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.mmajcherski.carsearch.page.CarSearchPage;
import pl.mmajcherski.carsearch.page.Pages;

import static org.fest.assertions.api.Assertions.assertThat;

public class CarSearchWebSteps {

	private static final Logger LOG = LoggerFactory.getLogger(CarSearchWebSteps.class);
	private static final int SLEEP_AFTER_PHASE_MS = 0;

	private final CarSearchPage carSearch;

	public CarSearchWebSteps(Pages pages) {
		this.carSearch = pages.carSearch();
	}

	@Given("is the default data set")
	public void defaultDataSet() {
	}

	@Given("an opened search specification page")
	public void openedSearchSpecPage() {
		carSearch.open();

		delay();
	}

	@When("I execute a search with more than one search result")
	public void searchWithMoreThanOneResult() {
		searchForPhrase("Audi");
		executeSearch();

		delay();
	}

	@When("I want to search for <phrase>")
	public void searchForPhrase(@Named("phrase") String phrase) {
		carSearch.inputSearchText(phrase);

		delay();
	}

	@When("I input <phrase>")
	public void inputSearchPhrase(@Named("phrase") String phrase) {
		// duplicate for #searchForPhrase()
	}

	@When("I execute the search")
	public void executeSearch() {
		carSearch.search();

		delay();
	}

	@Then("the page should contain for each found car the image, make, model, color and the price")
	public void pageShouldContainAllFoundCarsDetails() throws InterruptedException {
		int foundCarsSize = carSearch.getFoundCarsSize();
		assertThat(foundCarsSize).isEqualTo(2);

		carSearch.containsCarImageAtIndex(0);
		carSearch.containsCarMakeAtIndex("Audi", 0);
		carSearch.containsCarModelAtIndex("A4", 0);
		carSearch.containsCarPriceAtIndex("28,250 EUR", 0);
		carSearch.containsCarColorAtIndex("British racing green", 0);

		carSearch.containsCarImageAtIndex(1);
		carSearch.containsCarMakeAtIndex("Audi", 1);
		carSearch.containsCarModelAtIndex("A4", 1);
		carSearch.containsCarPriceAtIndex("38,480 EUR", 1);
		carSearch.containsCarColorAtIndex("Scarlet red", 1);

		delay();
	}

	@Then("the web application shows a search result page showing cars by <make> in size of <count>")
	public void pageShouldContainSearchResultsShowingCarsByGivenBrand(
			@Named("make") String make, @Named("count") int count) {
		int foundCarsSize = carSearch.getFoundCarsSize();
		assertThat(foundCarsSize).isEqualTo(count);

		for (int i=0; i<count; i++) {
			carSearch.containsCarMakeAtIndex(make, i);
		}

		delay();
	}

	@Then("the web application shows a search result page showing cars <make> <model> in size of <count>")
	public void pageShouldContainSearchResultsShowingCarsByGivenBrandAndModel(
			@Named("make") String make, @Named("model") String model, @Named("count") int count) {
		int foundCarsSize = carSearch.getFoundCarsSize();
		assertThat(foundCarsSize).isEqualTo(count);

		for (int i=0; i<count; i++) {
			carSearch.containsCarMakeAtIndex(make, i);
			carSearch.containsCarModelAtIndex(make, i);
		}

		delay();
	}

	private void delay() {
		try {
			Thread.sleep(SLEEP_AFTER_PHASE_MS);
		} catch (InterruptedException e) {
			LOG.error("Sleep interrupted", e);
		}
	}

}
