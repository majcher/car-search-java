package pl.mmajcherski.carsearch.test.e2e.story.step;

import org.jbehave.core.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.mmajcherski.carsearch.domain.car.model.Car;
import pl.mmajcherski.carsearch.domain.car.model.CarRepository;
import pl.mmajcherski.carsearch.domain.common.model.Money;
import pl.mmajcherski.carsearch.page.CarSearchPage;
import pl.mmajcherski.carsearch.test.e2e.story.parser.CarColorMakeModelStringParser;

import java.util.Locale;

import static org.fest.assertions.api.Assertions.assertThat;
import static pl.mmajcherski.carsearch.poc.PocCarBuilder.aDefaultCarDataSet;

@Component
public class CarSearchWebSteps {

	private final CarSearchPage carSearch;
	private final CarRepository carRepository;

	@Autowired
	public CarSearchWebSteps(CarSearchPage carSearch, CarRepository carRepository) {
		this.carSearch = carSearch;
		this.carRepository = carRepository;
	}

	@Given("is the default data set")
	public void defaultDataSet() {
		carRepository.deleteAll();

		for (Car car : aDefaultCarDataSet()) {
			carRepository.save(car);
		}
	}

	@Given("an opened search specification page")
	public void openedSearchSpecPage() {
		carSearch.open();
	}

	@Given("an opened search result page for parameters - make: <make> model: <model>, color: <color>")
	public void openSearchResultPageForParameters(@Named("make") String make, @Named("model") String model, @Named("color") String color) {
		carSearch.open();

		carSearch.inputSearchMake(make);
		carSearch.inputSearchModel(model);
		carSearch.inputSearchColor(color);

		carSearch.search();

		assertThat(carSearch.getFoundCarsSize()).isGreaterThan(0);
	}

	@When("I open the root of the web application in my browser")
	public void openWebApplicationRoot() {
		carSearch.openRootPage();
	}

	@When("I leave all search parameters blank")
	public void searchWithAllParamtersBlank() {
		// no-op
	}

	@When("I want to search for <phrase>")
	public void searchForPhrase(@Named("phrase") String phrase) {
		// no-op - duplicated step
	}

	@When("I want to search for some <color> tone")
	public void searchForColor(@Named("color") String color) {
		// no-op - duplicated step
	}

	@When("I input car make <make>")
	public void inputSearchMake(@Named("make") String make) {
		carSearch.inputSearchMake(make);
	}

	@When("I input car model <model>")
	public void inputSearchModel(@Named("model") String model) {
		carSearch.inputSearchModel(model);
	}

	@When("I input some <color> tone")
	public void inputSearchColor(@Named("color") String color) {
		carSearch.inputSearchColor(color);
	}

	@When("I execute the search")
	@Alias("I execute a search with more than one search result")
	public void executeSearch() {
		carSearch.search();
	}

	@When("I want to refine search")
	public void refineSearch() {
		// no-op
	}

	@When("I click some kind of button to return to the search specification page")
	public void clickBackToSearchPageButton() {
		carSearch.clickBackToSearchPageButton();
	}

	@Then("I want to see the search specification page")
	public void pageSpecificationPageShouldBeShown() {
		assertThat(carSearch.isOpen()).isTrue();
	}

	@Then("the page should contain for each found car the image, make, model, color and the price")
	@Alias("the web application shows a search result page containing all cars specified in the default data set")
	public void pageShouldContainAllFoundCarsDetails() {
		int foundCarsSize = carSearch.getFoundCarsSize();
		assertThat(foundCarsSize).isEqualTo(5);

		int i = 0;
		for (Car car : aDefaultCarDataSet()) {
			carSearch.containsCarImageAtIndex(i);
			carSearch.containsCarMakeAtIndex(car.getMake(), i);
			carSearch.containsCarModelAtIndex(car.getModel(), i);
			carSearch.containsCarPriceAtIndex(formatMoneyToCarPriceString(car.getPrice()), i);
			carSearch.containsCarColorAtIndex(car.getColor(), i);

			i++;
		}
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
	}

	@Then("the web application shows a search result page showing list of cars: <cars>")
	public void pageShouldContainSearchResultsShowingListOfCars(@Named("cars") String cars) {
		String[] carsArray = cars.split(",");
		int i = 0;
		for (String carText : carsArray) {
			CarColorMakeModelStringParser parser = CarColorMakeModelStringParser.forText(carText.trim());

			carSearch.containsCarMakeAtIndex(parser.getMake(), i);
			carSearch.containsCarModelAtIndex(parser.getModel(), i);
			carSearch.containsCarColorAtIndex(parser.getColor(), i);

			i++;
		}
	}

	@Then("the web application shows an empty search result page with the message \"$message\"")
	public void pageShouldContainZeroCarsAndAMessage(String message) {
		assertThat(carSearch.getFoundCarsSize()).isZero();
		carSearch.containsInformationMessage(message);
	}

	@Then("the web application shows the search specification page with pre filled search parameters regarding the last search - make: <make> model: <model>, color: <color>")
	public void pageShouldContainSearchFormWithFilledParams(@Named("make") String make, @Named("model") String model, @Named("color") String color) {
		assertThat(carSearch.getSearchMakeInputText()).isEqualTo(make);
		assertThat(carSearch.getSearchModelInputText()).isEqualTo(model);
		assertThat(carSearch.getSearchColorInputText()).isEqualTo(color);
	}

	private String formatMoneyToCarPriceString(Money price) {
		return String.format(Locale.ENGLISH, "%,.0f %s", price.getValue().doubleValue(), price.getCurrency());
	}

}
