package pl.mmajcherski.carsearch.interfaces.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.mmajcherski.carsearch.domain.model.car.Car;
import pl.mmajcherski.carsearch.interfaces.finder.CarFinder;
import pl.mmajcherski.cqrs.query.PaginatedResult;

import static com.google.common.base.Preconditions.checkNotNull;
import static pl.mmajcherski.carsearch.interfaces.finder.CarSearchCriteria.anyCar;

@Controller
@RequestMapping("/cars")
public class CarSearchController {

	private final CarFinder carFinder;

	@Autowired
	public CarSearchController(CarFinder carFinder) {
		checkNotNull(carFinder);
		this.carFinder = carFinder;
	}

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	@ResponseBody
	public PaginatedResult<Car> list(@RequestParam(value = "make", required = false) String make,
	                                 @RequestParam(value = "model", required = false) String model,
	                                 @RequestParam(value = "color", required = false) String color) {
		return carFinder.findCars(anyCar().withMake(make).withModel(model).withColor(color));
	}

}