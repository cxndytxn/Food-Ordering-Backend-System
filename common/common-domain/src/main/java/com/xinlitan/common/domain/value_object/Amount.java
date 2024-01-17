package com.xinlitan.common.domain.value_object;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class Amount {
    private final BigDecimal amount;

    public static final Amount ZERO = new Amount(BigDecimal.ZERO);

    public Amount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Amount amount1 = (Amount) o;
        return Objects.equals(amount, amount1.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount);
    }

    public boolean isGreaterThanZero() {
        return this.amount != null && this.amount.compareTo(BigDecimal.ZERO) > 0;
    }

    public boolean isGreaterThan(Amount amount) {
        return this.amount != null && this.amount.compareTo(amount.getAmount()) > 0;
    }

    public Amount add(Amount amount) {
        return new Amount(setScale(this.amount.add(amount.getAmount())));
    }

    public Amount subtract(Amount amount) {
        return new Amount(setScale(this.amount.subtract(amount.getAmount())));
    }

    public Amount multiply(int itemQuantity) {
        return new Amount(setScale(this.amount.multiply(new BigDecimal(itemQuantity))));
    }

    private BigDecimal setScale(BigDecimal input) {
        return input.setScale(2, RoundingMode.HALF_EVEN);
    }
}
