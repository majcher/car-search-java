package pl.mmajcherski.carsearch.interfaces.finder.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pl.mmajcherski.carsearch.domain.model.car.Car;
import pl.mmajcherski.carsearch.infrastructure.persistence.ElasticSearchCarFinder;
import pl.mmajcherski.carsearch.infrastructure.persistence.ElasticSearchCarRepository;
import pl.mmajcherski.carsearch.infrastructure.spring.CoreConfiguration;
import pl.mmajcherski.cqrs.query.PaginatedResult;

import static org.fest.assertions.api.Assertions.assertThat;
import static pl.mmajcherski.carsearch.interfaces.finder.CarSearchCriteria.anyCar;
import static pl.mmajcherski.carsearch.testdatabuilder.TestCarBuilder.aCar;

@ContextConfiguration(classes = CoreConfiguration.class)
public class ElasticSearchCarFinderTest extends AbstractTestNGSpringContextTests {

	@Autowired
	private ElasticSearchCarRepository carRepository;

	@Autowired
	private ElasticSearchCarFinder carFinder;

	private Car car;

	@BeforeClass
	public void saveCarBeforeTest() {
		// given
		car = aCar().build();

		// when
		carRepository.save(car);
	}

	@Test
	public void shouldFindSavedCarByMake() {
		// given
		String make = car.getMake();

		// when
		PaginatedResult<Car> foundCars = carFinder.findCars(anyCar().containingText(make));

		// then
		Car foundCar = foundCars.getItems().get(0);
		assertThat(foundCar).isEqualsToByComparingFields(car);
	}

	@Test(dependsOnMethods = "shouldFindSavedCarByMake")
	public void shouldFindSavedCarByModel() {
		// given
		String model = car.getModel();

		// when
		PaginatedResult<Car> foundCar = carFinder.findCars(anyCar().containingText(model));

		// then
		assertThat(foundCar.getItems().get(0)).isEqualsToByComparingFields(car);
	}

	@Test(dependsOnMethods = "shouldFindSavedCarByModel")
	public void shouldFindSavedCarByMakeAndModel() {
		// given
		String makeAndModel = car.getMake() + " " + car.getModel();

		// when
		PaginatedResult<Car> foundCar = carFinder.findCars(anyCar().containingText(makeAndModel));

		// then
		assertThat(foundCar.getItems().get(0)).isEqualsToByComparingFields(car);
	}

	@Test(dependsOnMethods = "shouldFindSavedCarByMakeAndModel")
	public void shouldNotFindSavedCarByMakeAndModel() {
		// given
		String makeAndModel = car.getMake() + " BMW " + car.getModel();

		// when
		PaginatedResult<Car> foundCar = carFinder.findCars(anyCar().containingText(makeAndModel));

		// then
		assertThat(foundCar.getItems()).isEmpty();
	}

}
