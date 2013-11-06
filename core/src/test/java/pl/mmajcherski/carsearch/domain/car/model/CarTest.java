package pl.mmajcherski.carsearch.domain.car.model;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.testng.annotations.Test;

public class CarTest {

	@Test
	public void shouldHaveCorrectEqualsAndHashCode() {
		EqualsVerifier.forClass(Car.class).verify();
	}

}
