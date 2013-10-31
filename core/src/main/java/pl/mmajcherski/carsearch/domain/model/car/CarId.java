package pl.mmajcherski.carsearch.domain.model.car;

import static com.google.common.base.Preconditions.checkNotNull;

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

        if (!id.equals(carId.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
