package pl.mmajcherski.carsearch.domain.car.model;

import com.google.common.base.Optional;

public interface CarRepository {

    Optional<Car> find(CarId id);

    void save(Car car);

	void deleteAll();
}
