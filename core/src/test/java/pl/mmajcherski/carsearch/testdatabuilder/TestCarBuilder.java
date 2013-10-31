package pl.mmajcherski.carsearch.testdatabuilder;

import pl.mmajcherski.carsearch.domain.model.car.Car;
import pl.mmajcherski.carsearch.domain.model.car.CarId;
import pl.mmajcherski.carsearch.domain.model.common.Money;

import java.math.BigDecimal;

public final class TestCarBuilder {

    private TestCarBuilder(){}

    public static TestCarBuilder aCar() {
        return new TestCarBuilder();
    }

    public Car build() {
        return new Car.Builder()
                .setId(new CarId(1L))
                .setMake("Ford")
                .setModel("Mustang")
                .setColor("Red")
                .setPrice(new Money(BigDecimal.valueOf(23000), "USD"))
                .build();
    }
}
