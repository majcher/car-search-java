package pl.mmajcherski.carsearch.infrastructure.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;
import pl.mmajcherski.carsearch.domain.model.car.Car;
import pl.mmajcherski.carsearch.domain.model.car.CarId;
import pl.mmajcherski.carsearch.infrastructure.spring.CoreConfiguration;

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

}
