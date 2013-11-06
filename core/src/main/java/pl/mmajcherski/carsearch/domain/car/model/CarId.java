package pl.mmajcherski.carsearch.domain.car.model;

import com.google.common.base.Objects;
import pl.mmajcherski.ddd.annotation.ValueObject;

import static com.google.common.base.Preconditions.checkNotNull;

@ValueObject
public final class CarId {

    private final Long id;

    public CarId(Long id) {
	    checkNotNull(id);

        this.id = id;
    }

    public Long getValue() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CarId carId = (CarId) o;
	    return Objects.equal(carId.id, id);
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
