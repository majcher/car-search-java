package pl.mmajcherski.carsearch.infrastructure.persistence;

import com.google.common.base.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import pl.mmajcherski.carsearch.domain.model.car.Car;
import pl.mmajcherski.carsearch.domain.model.car.CarId;
import pl.mmajcherski.carsearch.infrastructure.spring.CoreConfiguration;
import pl.mmajcherski.carsearch.infrastructure.test.BaseIntegrationTest;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;
import static pl.mmajcherski.carsearch.testdatabuilder.TestCarBuilder.aCar;

@ContextConfiguration(classes = CoreConfiguration.class)
public class ElasticSearchCarRepositoryTest extends BaseIntegrationTest {

    @Autowired
    private ElasticSearchCarRepository carRepository;

    private Car car;

    @Test
    public void shouldSaveCar() {
        // given
        car = aCar().build();

        // when
	    carRepository.deleteAll();
        carRepository.save(car);
    }

    @Test(dependsOnMethods = "shouldSaveCar")
    public void shouldFindSavedCarById() {
        // given
        CarId id = car.getId();

        // when
        Optional<Car> foundCar = carRepository.find(id);

        // then
	    assertThat(foundCar.isPresent()).isTrue();
	    assertReflectionEquals(car, foundCar.get());
    }

	@Test(dependsOnMethods = "shouldFindSavedCarById")
	public void shouldNotFindAfterDelete() {
		// given
		CarId id = car.getId();

		// when
		carRepository.deleteAll();
		Optional<Car> foundCar = carRepository.find(id);

		// then
		assertThat(foundCar.isPresent()).isFalse();
	}

	@AfterClass
	public void deleteDataAfterTest() {
		carRepository.deleteAll();
	}

}
