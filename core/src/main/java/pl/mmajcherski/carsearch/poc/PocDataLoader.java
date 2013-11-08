package pl.mmajcherski.carsearch.poc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.mmajcherski.carsearch.domain.car.model.Car;
import pl.mmajcherski.carsearch.domain.car.model.CarRepository;

import javax.annotation.PostConstruct;

@Component
public class PocDataLoader {

	private CarRepository carRepository;

	@Autowired
	public PocDataLoader(CarRepository carRepository) {
		this.carRepository = carRepository;
	}

	@PostConstruct
	public void loadPocCars() {
		carRepository.deleteAll();

		for (Car car : PocCarBuilder.aDefaultCarDataSet()) {
			carRepository.save(car);
		}
	}

}
