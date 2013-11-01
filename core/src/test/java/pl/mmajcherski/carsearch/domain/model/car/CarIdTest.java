package pl.mmajcherski.carsearch.domain.model.car;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.testng.annotations.Test;

public class CarIdTest {

	@Test
	public void shouldHaveCorrectEqualsAndHashCode() {
		EqualsVerifier.forClass(CarId.class).verify();
	}

}
