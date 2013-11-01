package pl.mmajcherski.carsearch.interfaces.finder;

import pl.mmajcherski.carsearch.domain.model.car.Car;
import pl.mmajcherski.cqrs.query.PaginatedResult;

public interface CarFinder {

	public PaginatedResult<Car> findCars(CarSearchCriteria criteria);

}
