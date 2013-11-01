package pl.mmajcherski.carsearch.interfaces.searching;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.mmajcherski.carsearch.domain.model.car.Car;
import pl.mmajcherski.carsearch.domain.model.car.CarRepository;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

@Controller
@RequestMapping("/cars")
public class CarSearchController {

	private final CarRepository carRepository;

	@Autowired
	public CarSearchController(CarRepository carRepository) {
		checkNotNull(carRepository);
		this.carRepository = carRepository;

		List<Car> cars = new ArrayList<>();
		cars.add(new Car.Builder()
				.withId(1L)
				.withMake("Ford")
				.withModel("Mustang")
				.withColor("Persimmon red")
				.withPrice(62000, "EUR").build());

		cars.add(new Car.Builder()
				.withId(2L)
				.withMake("Buick")
				.withModel("Lacrosse")
				.withColor("Midnight blue")
				.withPrice(21800, "USD").build());

		cars.add(new Car.Builder()
				.withId(3L)
				.withMake("BMW")
				.withModel("X5")
				.withColor("Ocean blue")
				.withPrice(42625, "EUR").build());

		cars.add(new Car.Builder()
				.withId(4L)
				.withMake("Audi")
				.withModel("A4")
				.withColor("British racing green")
				.withPrice(28250, "EUR").build());

		cars.add(new Car.Builder()
				.withId(5L)
				.withMake("Audi")
				.withModel("A4")
				.withColor("Scarlet red")
				.withPrice(38480, "EUR").build());

		for (Car car : cars) {
			carRepository.save(car);
		}
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public List<Car> list() {
		return carRepository.findByMakeOrModel("Ford");
	}

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	@ResponseBody
	public List<Car> list(@RequestParam("name") String name) {
		return carRepository.findByMakeOrModel(name);
	}

}