package pl.mmajcherski.carsearch.domain.model.car;

import pl.mmajcherski.carsearch.domain.model.common.Money;

import static com.google.common.base.Preconditions.checkNotNull;

public final class Car {

    private final CarId id;
    private final String make;
    private final String model;
    private final String color;
    private final Money price;

    private Car(CarId id, String make, String model, String color, Money price) {
	    checkNotNull(id);
	    checkNotNull(make);
	    checkNotNull(model);
	    checkNotNull(color);
	    checkNotNull(price);

        this.id = id;
        this.make = make;
        this.model = model;
        this.color = color;
        this.price = price;
    }

    public CarId getId() {
        return id;
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
        private CarId id;
        private String make;
        private String model;
        private String color;
        private Money price;

        public Builder setId(CarId id) {
            this.id = id;
            return this;
        }

        public Builder setMake(String make) {
            this.make = make;
            return this;
        }

        public Builder setModel(String model) {
            this.model = model;
            return this;
        }

        public Builder setColor(String color) {
            this.color = color;
            return this;
        }

        public Builder setPrice(Money price) {
            this.price = price;
            return this;
        }

        public Car build() {
            return new Car(id, make, model, color, price);
        }
    }
}
