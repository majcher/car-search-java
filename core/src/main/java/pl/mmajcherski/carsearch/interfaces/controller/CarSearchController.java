package pl.mmajcherski.carsearch.interfaces.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.mmajcherski.carsearch.domain.model.car.Car;
import pl.mmajcherski.carsearch.domain.model.car.CarRepository;
import pl.mmajcherski.carsearch.interfaces.finder.CarFinder;
import pl.mmajcherski.cqrs.query.PaginatedResult;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;
import static pl.mmajcherski.carsearch.interfaces.finder.CarSearchCriteria.anyCar;

@Controller
@RequestMapping("/cars")
public class CarSearchController {

	private final CarRepository carRepository;
	private final CarFinder carFinder;

	@Autowired
	public CarSearchController(CarRepository carRepository, CarFinder carFinder) {
		checkNotNull(carRepository);
		checkNotNull(carFinder);

		this.carRepository = carRepository;
		this.carFinder = carFinder;

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

		for (Car car : cars) {
			carRepository.save(car);
		}
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public PaginatedResult<Car> list() {
		return carFinder.findCars(anyCar());
	}

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	@ResponseBody
	public PaginatedResult<Car> list(@RequestParam("name") String name) {
		return carFinder.findCars(anyCar().containingText(name));
	}

}