package guru.springframework;

public interface Expression {
    Expression plus(Expression addend);

    Expression times(int multiplier);

    Money reduce(Bank bank, String to);

}
