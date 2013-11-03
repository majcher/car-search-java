package pl.mmajcherski.carsearch.parser;

import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;

public final class CarColorMakeModelStringParserTest {

	@Test
	public void shouldParseSimpleColorMakeModelString() {
		CarColorMakeModelStringParser p = CarColorMakeModelStringParser.forText("Blue Ford Mustang");

		assertThat(p.getColor()).isEqualTo("Blue");
		assertThat(p.getMake()).isEqualTo("Ford");
		assertThat(p.getModel()).isEqualTo("Mustang");
	}

	@Test
	public void shouldParseLongColorMakeModelString() {
		CarColorMakeModelStringParser p = CarColorMakeModelStringParser.forText("British racing green Audi A4");

		assertThat(p.getColor()).isEqualTo("British racing green");
		assertThat(p.getMake()).isEqualTo("Audi");
		assertThat(p.getModel()).isEqualTo("A4");
	}

}
