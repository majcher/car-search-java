package pl.mmajcherski.carsearch.domain.car;

import pl.mmajcherski.carsearch.domain.common.Money;

import static com.google.common.base.Preconditions.checkNotNull;

public final class Car {

    private final String make;
    private final String model;
    private final String color;
    private final Money price;

    private Car(String make, String model, String color, Money price) {
        this.make = make;
        this.model = model;
        this.color = color;
        this.price = price;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public String getColor() {
        return color;
    }

    public Money getPrice() {
        return price;
    }

    public static class Builder {
        private String make;
        private String model;
        private String color;
        private Money price;

        public Builder setMake(String make) {
            checkNotNull(make);
            this.make = make;
            return this;
        }

        public Builder setModel(String model) {
            checkNotNull(model);
            this.model = model;
            return this;
        }

        public Builder setColor(String color) {
            checkNotNull(color);
            this.color = color;
            return this;
        }

        public Builder setPrice(Money price) {
            checkNotNull(price);
            this.price = price;
            return this;
        }

        public Car build() {
            return new Car(make, model, color, price);
        }
    }
}
