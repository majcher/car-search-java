package pl.mmajcherski.carsearch.domain.car.model;

public final class TestCarBuilder {

	private Long id = -1L;
	private String make = "Ford";
	private String model = "Mustang";
	private String color = "Red";

	private TestCarBuilder() {
	}

	public static TestCarBuilder aCar() {
		return new TestCarBuilder();
	}

	public TestCarBuilder withId(int id) {
		this.id = Long.valueOf(id);
		return this;
	}

	public TestCarBuilder withMake(String make) {
		this.make = make;
		return this;
	}

	public TestCarBuilder withModel(String model) {
		this.model = model;
		return this;
	}

	public TestCarBuilder withColor(String color) {
		this.color = color;
		return this;
	}

	public Car build() {
		return new Car.Builder()
				.withId(id)
				.withMake(make)
				.withModel(model)
				.withColor(color)
				.withPrice(28999.1, "EUR")
				.build();
	}

}
