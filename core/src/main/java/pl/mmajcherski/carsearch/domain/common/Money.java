package pl.mmajcherski.carsearch.domain.common;

import static com.google.common.base.Preconditions.*;

import java.math.BigDecimal;

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
}
