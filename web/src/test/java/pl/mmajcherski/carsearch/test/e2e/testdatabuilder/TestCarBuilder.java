package pl.mmajcherski.carsearch.test.e2e.testdatabuilder;

import pl.mmajcherski.carsearch.domain.car.model.Car;

import java.util.ArrayList;
import java.util.List;

public final class TestCarBuilder {

	public static List<Car> aDefaultCarDataSet() {
		List<Car> cars = new ArrayList<>();

		cars.add(new Car.Builder()
				.withId(1L)
				.withMake("Ford")
				.withModel("Mustang")
				.withColor("Persimmon red")
				.withPrice(62000.0, "EUR").build());

		cars.add(new Car.Builder()
				.withId(2L)
				.withMake("Buick")
				.withModel("Lacrosse")
				.withColor("Midnight blue")
				.withPrice(21800.0, "USD").build());

		cars.add(new Car.Builder()
				.withId(3L)
				.withMake("BMW")
				.withModel("X5")
				.withColor("Ocean blue")
				.withPrice(42625.0, "EUR").build());

		cars.add(new Car.Builder()
				.withId(4L)
				.withMake("Audi")
				.withModel("A4")
				.withColor("British racing green")
				.withPrice(28250.0, "EUR").build());

		cars.add(new Car.Builder()
				.withId(5L)
				.withMake("Audi")
				.withModel("A4")
				.withColor("Scarlet red")
				.withPrice(38480.0, "EUR").build());

		return cars;
	}
}
