package pl.mmajcherski.carsearch.interfaces.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.mmajcherski.carsearch.domain.car.model.Car;
import pl.mmajcherski.carsearch.domain.car.service.CarFinder;
import pl.mmajcherski.carsearch.domain.common.model.PagedResult;

import static com.google.common.base.Preconditions.checkNotNull;
import static pl.mmajcherski.carsearch.domain.car.service.CarFinder.SearchCriteria.anyCar;

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
	public PagedResult<Car> list(@RequestParam(value = "make", required = false) String make,
	                                 @RequestParam(value = "model", required = false) String model,
	                                 @RequestParam(value = "color", required = false) String color) {
		return carFinder.findCars(anyCar().withMake(make).withModel(model).withColor(color));
	}

}