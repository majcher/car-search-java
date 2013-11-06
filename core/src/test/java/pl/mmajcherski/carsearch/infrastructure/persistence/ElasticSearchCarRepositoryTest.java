package pl.mmajcherski.carsearch.infrastructure.persistence;

import com.google.common.base.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pl.mmajcherski.carsearch.domain.car.model.Car;
import pl.mmajcherski.carsearch.domain.car.model.CarId;
import pl.mmajcherski.carsearch.infrastructure.di.spring.CoreConfiguration;
import pl.mmajcherski.carsearch.infrastructure.persistence.elasticsearch.ElasticSearchCarRepository;
import pl.mmajcherski.carsearch.infrastructure.test.BaseIntegrationTest;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;
import static pl.mmajcherski.carsearch.domain.car.model.TestCarBuilder.aCar;

@ContextConfiguration(classes = CoreConfiguration.class)
public class ElasticSearchCarRepositoryTest extends BaseIntegrationTest {

    @Autowired
    private ElasticSearchCarRepository carRepository;

    private Car car;

	@BeforeMethod
	public void cleanBeforeTest() {
		car = aCar().build();
		carRepository.deleteAll();
		carRepository.save(car);
	}

    @Test
    public void shouldFindSavedCarById() {
        // given
        CarId id = car.getId();

        // when
        Optional<Car> foundCar = carRepository.find(id);

        // then
	    assertThat(foundCar.isPresent()).isTrue();
	    assertReflectionEquals(car, foundCar.get());
    }

	@Test
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
