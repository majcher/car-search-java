package pl.mmajcherski.carsearch.testdatabuilder;

import pl.mmajcherski.carsearch.domain.model.car.Car;

public final class TestCarBuilder {

	private Long id = -1L;
	private String make = "Ford";
	private String model = "Mustang";

	private TestCarBuilder(){}

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

    public Car build() {
        return new Car.Builder()
                .withId(id)
                .withMake(make)
                .withModel(model)
                .withColor("Red")
                .withPrice(28999.1, "EUR")
                .build();
    }
}
