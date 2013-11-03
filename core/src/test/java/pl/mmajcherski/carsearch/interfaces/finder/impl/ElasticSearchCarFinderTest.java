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

		carRepository.save(aCar().withId(1)
				.withMake("Ford").withModel("Mustang")
				.withColor("Persimmon red").build());

		carRepository.save(aCar().withId(2)
				.withMake("BMW").withModel("X5")
				.withColor("Ocean blue").build());

		carRepository.save(aCar().withId(3)
				.withMake("Audi").withModel("A3")
				.withColor("Scarlet red").build());

		carRepository.save(aCar().withId(4).
				withMake("Audi").withModel("A4")
				.withColor("British racing green").build());

		carRepository.save(aCar().withId(5)
				.withMake("Audi").withModel("A5")
				.withColor("Scarlet red").build());
	}

	@Test
	public void shouldFindSavedCarByMake() {
		// given
		Car car = carRepository.find(new CarId(1L)).get();
		String make = car.getMake();

		// when
		PaginatedResult<Car> foundCars = carFinder.findCars(anyCar().withMake(make));

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
		PaginatedResult<Car> foundCars = carFinder.findCars(anyCar().withModel(model));

		// then
		assertThat(foundCars.getItems()).isNotEmpty();
		Car foundCar = foundCars.getItems().get(0);
		assertReflectionEquals(foundCar, car);
	}

	@Test
	public void shouldFindSavedCarByMakeAndModel() {
		// given
		Car car = carRepository.find(new CarId(1L)).get();
		String make = car.getMake();
		String model = car.getModel();

		// when
		PaginatedResult<Car> foundCars = carFinder.findCars(anyCar().withMake(make).withModel(model));

		// then
		assertThat(foundCars.getItems()).isNotEmpty();
		Car foundCar = foundCars.getItems().get(0);
		assertReflectionEquals(foundCar, car);
	}

	@Test
	public void shouldNotFindSavedCarByMakeWithExtraWords() {
		// given
		Car car = carRepository.find(new CarId(1L)).get();
		String make = car.getMake() + " BMW";

		// when
		PaginatedResult<Car> foundCars = carFinder.findCars(anyCar().withMake(make));

		// then
		assertThat(foundCars.getItems()).isEmpty();
	}

	@Test
	public void shouldFindSavedCarsByColor() {
		// given
		Car redFord = carRepository.find(new CarId(1L)).get();
		Car redAudiA3 = carRepository.find(new CarId(3L)).get();
		Car redAudiA5 = carRepository.find(new CarId(5L)).get();

		String color = "red";

		// when
		PaginatedResult<Car> foundCars = carFinder.findCars(anyCar().withColor(color));

		// then
		assertThat(foundCars.getItems()).contains(redFord, redAudiA3, redAudiA5);
	}

	@Test
	public void shouldFindSavedCarsByMakeAndColor() {
		// given
		Car greenAudiA4 = carRepository.find(new CarId(4L)).get();

		String make = "Audi";
		String color = "green";

		// when
		PaginatedResult<Car> foundCars = carFinder.findCars(anyCar().withMake(make).withColor(color));

		// then
		assertThat(foundCars.getItems()).contains(greenAudiA4);
	}

	@Test
	public void shouldProvideOnlyCarsPerPage() {
		// given
		String make = "Audi";

		// when
		PaginatedResult<Car> foundCars = carFinder.findCars(
				anyCar().withMake(make).withPageSize(1).withPageNumber(0));

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
				anyCar().withMake(make).withPageSize(1).withPageNumber(1));

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
				anyCar().withMake(make).withPageSize(1).withPageNumber(0));

		// then
		assertThat(foundCars.getTotalItemsCount()).isEqualTo(3);
	}

	@AfterClass
	public void deleteDataAfterTest() {
		carRepository.deleteAll();
	}

}
