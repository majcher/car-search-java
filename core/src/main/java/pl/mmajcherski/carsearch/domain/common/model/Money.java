package pl.mmajcherski.carsearch.domain.common.model;

import com.google.common.base.Objects;
import pl.mmajcherski.ddd.annotation.ValueObject;

import java.math.BigDecimal;
import java.util.Currency;

import static com.google.common.base.Preconditions.checkNotNull;

@ValueObject
public final class Money {

	private final BigDecimal value;
	private final Currency currency;

	public Money(BigDecimal value, Currency currency) {
		checkNotNull(value);
		checkNotNull(currency);

		this.value = value;
		this.currency = currency;
	}

	public BigDecimal getValue() {
		return value;
	}

	public Currency getCurrency() {
		return currency;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		Money money = (Money) o;
		return Objects.equal(money.value, value)
				&& Objects.equal(money.currency, currency);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(value, currency);
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this)
				.add("value", value)
				.add("currency", currency)
				.toString();
	}
}
