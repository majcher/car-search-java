package pl.mmajcherski.carsearch.domain.model.common;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.testng.annotations.Test;

public class MoneyTest {

	@Test
	public void shouldHaveCorrectEqualsAndHashCode() {
		EqualsVerifier.forClass(Money.class).verify();
	}

}
