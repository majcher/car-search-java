package pl.mmajcherski.carsearch.infrastructure.persistence.converter;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;
import pl.mmajcherski.carsearch.domain.car.model.Car;
import pl.mmajcherski.carsearch.infrastructure.persistence.elasticsearch.converter.CarJsonConverter;

import static org.fest.assertions.api.Assertions.assertThat;
import static pl.mmajcherski.carsearch.domain.car.model.TestCarBuilder.aCar;

public class CarJsonConverterTest {

	@Autowired
	private CarJsonConverter converter = new CarJsonConverter(new ObjectMapper());

	@Test
	public void shouldBeTheSameAfterTwoWayConversion() {
		// given
		Car car = aCar().build();

		// when
		String json = converter.toJson(car);
		Car resultCar = converter.fromJson(json);

		// then
		assertThat(resultCar).isEqualsToByComparingFields(car);
	}
}
