package pl.mmajcherski.carsearch.domain.model.car;

import com.google.common.base.Objects;
import pl.mmajcherski.carsearch.domain.model.common.Money;
import pl.mmajcherski.ddd.annotation.DomainEntity;

import java.math.BigDecimal;
import java.util.Currency;

import static com.google.common.base.Preconditions.checkNotNull;

@DomainEntity
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

        public Builder withId(Long id) {
            this.id = new CarId(id);
            return this;
        }

        public Builder withMake(String make) {
            this.make = make;
            return this;
        }

        public Builder withModel(String model) {
            this.model = model;
            return this;
        }

        public Builder withColor(String color) {
            this.color = color;
            return this;
        }

	    public Builder withPrice(Double value, String currencyCode) {
		    this.price = new Money(BigDecimal.valueOf(value), Currency.getInstance(currencyCode));
		    return this;
	    }

        public Car build() {
            return new Car(id, make, model, color, price);
        }
    }

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Car car = (Car) o;
		return Objects.equal(car.id, id);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id);
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this)
				.add("id", id)
				.toString();
	}
}
