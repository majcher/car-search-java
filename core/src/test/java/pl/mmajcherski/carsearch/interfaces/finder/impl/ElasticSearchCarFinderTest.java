package pl.mmajcherski.carsearch.interfaces.finder.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pl.mmajcherski.carsearch.domain.model.car.Car;
import pl.mmajcherski.carsearch.infrastructure.persistence.ElasticSearchCarFinder;
import pl.mmajcherski.carsearch.infrastructure.persistence.ElasticSearchCarRepository;
import pl.mmajcherski.carsearch.infrastructure.spring.CoreConfiguration;
import pl.mmajcherski.carsearch.infrastructure.test.BaseIntegrationTest;
import pl.mmajcherski.cqrs.query.PaginatedResult;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;
import static pl.mmajcherski.carsearch.interfaces.finder.CarSearchCriteria.anyCar;
import static pl.mmajcherski.carsearch.testdatabuilder.TestCarBuilder.aCar;

@ContextConfiguration(classes = CoreConfiguration.class)
public class ElasticSearchCarFinderTest extends BaseIntegrationTest {

	@Autowired private ElasticSearchCarRepository carRepository;
	@Autowired private ElasticSearchCarFinder carFinder;

	private Car car;

	@BeforeClass
	public void saveCarBeforeTest() {
		// given
		car = aCar().build();

		// when
		carRepository.deleteAll();
		carRepository.save(car);
	}

	@Test
	public void shouldFindSavedCarByMake() {
		// given
		String make = car.getMake();

		// when
		PaginatedResult<Car> foundCars = carFinder.findCars(anyCar().containingText(make));

		// then
		assertThat(foundCars.getItems()).isNotEmpty();
		Car foundCar = foundCars.getItems().get(0);
		assertReflectionEquals(foundCar, car);
	}

	@Test(dependsOnMethods = "shouldFindSavedCarByMake")
	public void shouldFindSavedCarByModel() {
		// given
		String model = car.getModel();

		// when
		PaginatedResult<Car> foundCars = carFinder.findCars(anyCar().containingText(model));

		// then
		assertThat(foundCars.getItems()).isNotEmpty();
		Car foundCar = foundCars.getItems().get(0);
		assertReflectionEquals(foundCar, car);
	}

	@Test(dependsOnMethods = "shouldFindSavedCarByModel")
	public void shouldFindSavedCarByMakeAndModel() {
		// given
		String makeAndModel = car.getMake() + " " + car.getModel();

		// when
		PaginatedResult<Car> foundCars = carFinder.findCars(anyCar().containingText(makeAndModel));

		// then
		assertThat(foundCars.getItems()).isNotEmpty();
		Car foundCar = foundCars.getItems().get(0);
		assertReflectionEquals(foundCar, car);
	}

	@Test(dependsOnMethods = "shouldFindSavedCarByMakeAndModel")
	public void shouldNotFindSavedCarByMakeAndModel() {
		// given
		String makeAndModel = car.getMake() + " BMW " + car.getModel();

		// when
		PaginatedResult<Car> foundCars = carFinder.findCars(anyCar().containingText(makeAndModel));

		// then
		assertThat(foundCars.getItems()).isEmpty();
	}

	@AfterClass
	public void deleteDataAfterTest() {
		carRepository.deleteAll();
	}

}
