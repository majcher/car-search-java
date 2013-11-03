package pl.mmajcherski.carsearch.parser;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.fest.assertions.api.Assertions.assertThat;

public final class CarColorMakeModelStringParserTest {

	@DataProvider(name = "Text data provider")
	public Object[][] parserTextProvider() {
		return new Object[][] {
				{"British racing green Audi A4", "British racing green", "Audi", "A4"},
				{"Ocean blue BMW X5", "Ocean blue", "BMW", "X5"},
				{"Blue Ford Mustang", "Blue", "Ford", "Mustang"}
		};
	}

	@Test(dataProvider = "Text data provider")
	public void shouldParseColorMakeModelString(String text, String color, String make, String model) {
		CarColorMakeModelStringParser p = CarColorMakeModelStringParser.forText(text);

		assertThat(p.getColor()).isEqualTo(color);
		assertThat(p.getMake()).isEqualTo(make);
		assertThat(p.getModel()).isEqualTo(model);
	}

}
