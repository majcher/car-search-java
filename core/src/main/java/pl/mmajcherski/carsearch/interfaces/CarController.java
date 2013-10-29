package pl.mmajcherski.carsearch.interfaces;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.mmajcherski.carsearch.domain.car.Car;
import pl.mmajcherski.carsearch.domain.common.Money;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/cars")
public class CarController {

	private final List<Car> cars;

	public CarController() {
		List<Car> cars = new ArrayList<>();
		cars.add(new Car.Builder()
                .setMake("Ford")
                .setModel("Mustang")
                .setColor("Persimmon red")
                .setPrice(new Money(new BigDecimal(23000), "USD")).build());

		cars.add(new Car.Builder().setMake("BMW").build());

		cars.add(new Car.Builder().setMake("Audi").build());

		this.cars = Collections.unmodifiableList(cars);
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public @ResponseBody List<Car> list() {
		return cars;
	}

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public @ResponseBody List<Car> list(@RequestParam("name") String name) {
		List<Car> foundCars = new ArrayList<>();
		for (Car car : cars) {
			if (car.getMake().toLowerCase().contains(name.toLowerCase()))  {
				foundCars.add(car);
			}
		}
        return foundCars;
	}

}