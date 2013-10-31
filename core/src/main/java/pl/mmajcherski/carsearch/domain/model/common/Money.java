package pl.mmajcherski.carsearch.domain.model.common;

import java.math.BigDecimal;

import static com.google.common.base.Preconditions.checkNotNull;

public final class Money {

    private final BigDecimal value;
    private final String currency;

    public Money(BigDecimal value, String currency) {
        checkNotNull(value);
        checkNotNull(currency);

        this.value = value;
        this.currency = currency;
    }

    public BigDecimal getValue() {
        return value;
    }

    public String getCurrency() {
        return currency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Money money = (Money) o;

        if (currency != null ? !currency.equals(money.currency) : money.currency != null) return false;
        if (value != null ? !value.equals(money.value) : money.value != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = value != null ? value.hashCode() : 0;
        result = 31 * result + (currency != null ? currency.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Money{" +
                "value=" + value +
                ", currency='" + currency + '\'' +
                '}';
    }
}
