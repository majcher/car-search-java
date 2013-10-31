package pl.mmajcherski.carsearch.infrastructure.persistence;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;
import pl.mmajcherski.carsearch.domain.model.car.Car;

import static org.fest.assertions.api.Assertions.assertThat;
import static pl.mmajcherski.carsearch.testdatabuilder.TestCarBuilder.aCar;

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
