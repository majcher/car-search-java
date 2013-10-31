package pl.mmajcherski.carsearch.interfaces.searching;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.mmajcherski.carsearch.domain.model.car.Car;
import pl.mmajcherski.carsearch.domain.model.car.CarId;
import pl.mmajcherski.carsearch.domain.model.car.CarRepository;
import pl.mmajcherski.carsearch.domain.model.common.Money;

import java.math.BigDecimal;
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
				.setId(new CarId(1L))
				.setMake("Ford")
				.setModel("Mustang")
				.setColor("Persimmon red")
				.setPrice(new Money(new BigDecimal(23000), "USD")).build());

		cars.add(new Car.Builder()
				.setId(new CarId(2L))
				.setMake("Buick")
				.setModel("Lacrosse")
				.setColor("Midnight blue")
				.setPrice(new Money(new BigDecimal(23000), "USD")).build());

		cars.add(new Car.Builder()
				.setId(new CarId(3L))
				.setMake("BMW")
				.setModel("X5")
				.setColor("Ocean blue")
				.setPrice(new Money(new BigDecimal(23000), "USD")).build());

		cars.add(new Car.Builder()
				.setId(new CarId(4L))
				.setMake("Audi")
				.setModel("A4")
				.setColor("British racing green")
				.setPrice(new Money(new BigDecimal(23000), "USD")).build());

		cars.add(new Car.Builder()
				.setId(new CarId(5L))
				.setMake("Audi")
				.setModel("A4")
				.setColor("Scarlet red")
				.setPrice(new Money(new BigDecimal(23000), "USD")).build());

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