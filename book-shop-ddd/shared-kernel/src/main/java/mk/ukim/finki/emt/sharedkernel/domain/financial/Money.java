package mk.ukim.finki.emt.sharedkernel.domain.financial;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NonNull;
import mk.ukim.finki.emt.sharedkernel.domain.base.ValueObject;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Objects;

@Embeddable
@Getter
public class Money implements ValueObject {

    @Enumerated(value = EnumType.STRING)
    private final Currency currency;

    private final double amount;

    protected Money() {
        this.currency = null;
        this.amount = 0.0;
    }

    @JsonCreator
    public Money(@JsonProperty("price") double amount) {
        this.amount = amount;
        this.currency = Currency.MKD;
    }

    public Money(@NonNull Currency currency,@NonNull double amount) {
        this.currency = currency;
        this.amount = amount;
    }

    public static Money valueOf(Currency currency, double amount) {
        return new Money(currency, amount);
    }

    public Money mkdToEur(){
        double amount = this.amount / 61.5;
        return new Money(Currency.EUR, Double.parseDouble(String.format("%.2f", amount)));
    }

    public Money eurToMkd(){
        double amount = this.amount * 61.5;
        return new Money(Currency.MKD, Math.round(amount));
    }

    public Money add(Money money) {
        if (!currency.equals(money.getCurrency())) {
            if (this.currency.equals(Currency.MKD)){
                money = money.eurToMkd();
            } else {
                money = money.mkdToEur();
            }
        }
        return new Money(currency, amount + money.amount);
    }

    public Money subtract(Money money) {
        if (!currency.equals(money.getCurrency())) {
            throw new IllegalArgumentException("Cannot subtract two Money objects with different currencies");
        }
        return new Money(currency, amount - money.amount);
    }

    public Money divide(Money money) {
        if (!currency.equals(money.getCurrency())) {
            throw new IllegalArgumentException("Cannot divide two Money objects with different currencies");
        }
        return new Money(currency, amount / money.amount);
    }

    public Money multiply(Money money) {
        if (!currency.equals(money.getCurrency())) {
            throw new IllegalArgumentException("Cannot multiply two Money objects with different currencies");
        }
        return new Money(currency, amount * money.amount);
    }

    public Money multiply(int m) {
        return new Money(currency, amount * m);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return amount == money.amount && currency == money.currency;
    }

    @Override
    public int hashCode() {
        return Objects.hash(currency, amount);
    }

}
