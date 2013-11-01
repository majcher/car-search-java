package pl.mmajcherski.carsearch.testdatabuilder;

import pl.mmajcherski.carsearch.domain.model.car.Car;

public final class TestCarBuilder {

    private TestCarBuilder(){}

    public static TestCarBuilder aCar() {
        return new TestCarBuilder();
    }

    public Car build() {
        return new Car.Builder()
                .withId(1L)
                .withMake("Ford")
                .withModel("Mustang")
                .withColor("Red")
                .withPrice(28999, "EUR")
                .build();
    }
}
