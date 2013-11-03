package pl.mmajcherski.carsearch.step;

import org.jbehave.core.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.mmajcherski.carsearch.domain.model.car.Car;
import pl.mmajcherski.carsearch.domain.model.car.CarRepository;
import pl.mmajcherski.carsearch.domain.model.common.Money;
import pl.mmajcherski.carsearch.page.CarSearchPage;
import pl.mmajcherski.carsearch.parser.CarColorMakeModelStringParser;

import static org.fest.assertions.api.Assertions.assertThat;
import static pl.mmajcherski.carsearch.testdatabuilder.TestCarBuilder.aDefaultCarDataSet;

@Component
public class CarSearchWebSteps {

	private static final int SLEEP_AFTER_PHASE_MS = 0;

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

		delay();
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

		delay();
	}

	@When("I input car model <model>")
	public void inputSearchModel(@Named("model") String model) {
		carSearch.inputSearchModel(model);

		delay();
	}

	@When("I input some <color> tone")
	public void inputSearchColor(@Named("color") String color) {
		carSearch.inputSearchColor(color);

		delay();
	}

	@When("I execute the search")
	@Alias("I execute a search with more than one search result")
	public void executeSearch() {
		carSearch.search();

		delay();
	}

	@Then("the page should contain for each found car the image, make, model, color and the price")
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

	private String formatMoneyToCarPriceString(Money price) {
		return String.format("%,.0f %s", price.getValue().doubleValue(), price.getCurrency());
	}

	private void delay() {
		try {
			Thread.sleep(SLEEP_AFTER_PHASE_MS);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

}
