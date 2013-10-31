package pl.mmajcherski.carsearch.domain.model.car;

import java.util.List;

public interface CarRepository {

    Car find(CarId id);

    List<Car> findByMakeOrModel(String make);

    void save(Car car);
}
