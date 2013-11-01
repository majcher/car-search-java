package pl.mmajcherski.carsearch.domain.model.car;

public interface CarRepository {

    Car find(CarId id);

    void save(Car car);

}
