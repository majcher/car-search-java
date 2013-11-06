package pl.mmajcherski.carsearch.domain.car.model;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.testng.annotations.Test;

public class CarIdTest {

	@Test
	public void shouldHaveCorrectEqualsAndHashCode() {
		EqualsVerifier.forClass(CarId.class).verify();
	}

}
