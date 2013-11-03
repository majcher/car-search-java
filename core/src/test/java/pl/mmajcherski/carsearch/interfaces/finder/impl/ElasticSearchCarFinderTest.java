package pl.mmajcherski.carsearch.interfaces.finder.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pl.mmajcherski.carsearch.domain.model.car.Car;
import pl.mmajcherski.carsearch.domain.model.car.CarId;
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

	@BeforeMethod
	public void saveCarBeforeTest() {
		carRepository.deleteAll();

		carRepository.save(aCar().withId(1).withMake("Ford").withModel("Mustang").build());
		carRepository.save(aCar().withId(2).withMake("BMW").withModel("X5").build());
		carRepository.save(aCar().withId(3).withMake("Audi").withModel("A3").build());
		carRepository.save(aCar().withId(4).withMake("Audi").withModel("A4").build());
		carRepository.save(aCar().withId(5).withMake("Audi").withModel("A5").build());
	}

	@Test
	public void shouldFindSavedCarByMake() {
		// given
		Car car = carRepository.find(new CarId(1L)).get();
		String make = car.getMake();

		// when
		PaginatedResult<Car> foundCars = carFinder.findCars(anyCar().containingText(make));

		// then
		assertThat(foundCars.getItems()).isNotEmpty();
		Car foundCar = foundCars.getItems().get(0);
		assertReflectionEquals(foundCar, car);
	}

	@Test
	public void shouldFindSavedCarByModel() {
		// given
		Car car = carRepository.find(new CarId(1L)).get();
		String model = car.getModel();

		// when
		PaginatedResult<Car> foundCars = carFinder.findCars(anyCar().containingText(model));

		// then
		assertThat(foundCars.getItems()).isNotEmpty();
		Car foundCar = foundCars.getItems().get(0);
		assertReflectionEquals(foundCar, car);
	}

	@Test
	public void shouldFindSavedCarByMakeAndModel() {
		// given
		Car car = carRepository.find(new CarId(1L)).get();
		String makeAndModel = car.getMake() + " " + car.getModel();

		// when
		PaginatedResult<Car> foundCars = carFinder.findCars(anyCar().containingText(makeAndModel));

		// then
		assertThat(foundCars.getItems()).isNotEmpty();
		Car foundCar = foundCars.getItems().get(0);
		assertReflectionEquals(foundCar, car);
	}

	@Test
	public void shouldNotFindSavedCarByMakeAndModel() {
		// given
		Car car = carRepository.find(new CarId(1L)).get();
		String makeAndModel = car.getMake() + " BMW " + car.getModel();

		// when
		PaginatedResult<Car> foundCars = carFinder.findCars(anyCar().containingText(makeAndModel));

		// then
		assertThat(foundCars.getItems()).isEmpty();
	}

	@Test
	public void shouldProvideOnlyCarsPerPage() {
		// given
		String make = "Audi";

		// when
		PaginatedResult<Car> foundCars = carFinder.findCars(
				anyCar().containingText(make).withPageSize(1).withPageNumber(0));

		// then
		assertThat(foundCars.getItems()).hasSize(1);
		Car foundCar = foundCars.getItems().get(0);
		assertThat(foundCar.getMake()).isEqualTo("Audi");
		assertThat(foundCar.getModel()).isEqualTo("A3");
	}

	@Test
	public void shouldProvideCarsForGivenPage() {
		// given
		String make = "Audi";

		// when
		PaginatedResult<Car> foundCars = carFinder.findCars(
				anyCar().containingText(make).withPageSize(1).withPageNumber(1));

		// then
		assertThat(foundCars.getItems()).hasSize(1);
		Car foundCar = foundCars.getItems().get(0);
		assertThat(foundCar.getMake()).isEqualTo("Audi");
		assertThat(foundCar.getModel()).isEqualTo("A4");
	}

	@Test
	public void shouldProvideTotalCarsFound() {
		// given
		String make = "Audi";

		// when
		PaginatedResult<Car> foundCars = carFinder.findCars(
				anyCar().containingText(make).withPageSize(1).withPageNumber(0));

		// then
		assertThat(foundCars.getTotalItemsCount()).isEqualTo(3);
	}

	@AfterClass
	public void deleteDataAfterTest() {
		carRepository.deleteAll();
	}

}
