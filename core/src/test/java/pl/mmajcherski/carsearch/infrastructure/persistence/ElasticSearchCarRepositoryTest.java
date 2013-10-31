package pl.mmajcherski.carsearch.infrastructure.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pl.mmajcherski.carsearch.domain.model.car.Car;
import pl.mmajcherski.carsearch.domain.model.car.CarId;
import pl.mmajcherski.carsearch.infrastructure.spring.CoreConfiguration;

import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;
import static pl.mmajcherski.carsearch.testdatabuilder.TestCarBuilder.aCar;

@ContextConfiguration(classes = CoreConfiguration.class)
public class ElasticSearchCarRepositoryTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private ElasticSearchCarRepository carRepository;

    private Car car;

    @Test
    public void shouldSaveCar() {
        // given
        car = aCar().build();

        // when
        carRepository.save(car);
    }

    @Test(dependsOnMethods = "shouldSaveCar")
    public void shouldFindSavedCarById() {
        // given
        CarId id = car.getId();

        // when
        Car foundCar = carRepository.find(id);

        // then
        assertThat(foundCar).isEqualsToByComparingFields(car);
    }

    @Test(dependsOnMethods = "shouldFindSavedCarById")
    public void shouldFindSavedCarByMake() {
        // given
        String make = car.getMake();

        // when
        List<Car> foundCar = carRepository.findByMakeOrModel(make);

        // then
        assertThat(foundCar.get(0)).isEqualsToByComparingFields(car);
    }

    @Test(dependsOnMethods = "shouldFindSavedCarByMake")
    public void shouldFindSavedCarByModel() {
        // given
        String model = car.getModel();

        // when
        List<Car> foundCar = carRepository.findByMakeOrModel(model);

        // then
        assertThat(foundCar.get(0)).isEqualsToByComparingFields(car);
    }

    @Test(dependsOnMethods = "shouldFindSavedCarByModel")
    public void shouldFindSavedCarByMakeAndModel() {
        // given
        String makeAndModel = car.getMake() + " " + car.getModel();

        // when
        List<Car> foundCar = carRepository.findByMakeOrModel(makeAndModel);

        // then
        assertThat(foundCar.get(0)).isEqualsToByComparingFields(car);
    }

    @Test(dependsOnMethods = "shouldFindSavedCarByMakeAndModel")
    public void shouldNotFindSavedCarByMakeAndModel() {
        // given
        String makeAndModel = car.getMake() + " BMW " + car.getModel();

        // when
        List<Car> foundCar = carRepository.findByMakeOrModel(makeAndModel);

        // then
        assertThat(foundCar).isEmpty();
    }

}
