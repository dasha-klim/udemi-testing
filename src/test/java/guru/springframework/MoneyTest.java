package guru.springframework;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MoneyTest {

    @Test
    void testEqualityDollar() {
        Assertions.assertEquals(Money.dollar(5), Money.dollar(5));
        Assertions.assertNotEquals(Money.dollar(5), Money.dollar(8));
        Assertions.assertNotEquals(Money.dollar(5), Money.franc(5));
        Assertions.assertEquals(Money.franc(5), Money.franc(5));
        Assertions.assertNotEquals(Money.franc(5), Money.franc(8));
    }

    @Test
    void testMultiplication() {
        Money five = Money.dollar(5);
        Assertions.assertEquals(Money.dollar(10),
                five.times(2));
        Assertions.assertEquals(Money.dollar(15),
                five.times(3));
        Money fiveF = Money.franc(5);
        Assertions.assertEquals(Money.franc(15),
                fiveF.times(3));
    }

    @Test
    void testCurrency() {
        Assertions.assertEquals("USD", Money.dollar(1).currency());
        Assertions.assertEquals("CHF", Money.franc(1).currency());
    }

    @Test
    void testSimpleAddition() {
        Money five = Money.dollar(5);
        Expression sum = five.plus(five);
        Bank bank = new Bank();
        Money reduced = bank.reduce(sum, "USD");
        Assertions.assertEquals(Money.dollar(10), reduced);
    }

    @Test
    void testPlusReturnsSum() {
        Money five = Money.dollar(5);
        Expression result = five.plus(five);
        Sum sum = (Sum) result;
        Assertions.assertEquals(five, sum.augmend);
        Assertions.assertEquals(five, sum.augmend);
    }

    @Test
    void testReduceSum() {
        Expression sum = new Sum(Money.dollar(3), Money.dollar(4));
        Bank bank = new Bank();
        Money result = bank.reduce(sum, "USD");
        Assertions.assertEquals(Money.dollar(7), result);
    }

    @Test
    void reduceMoney() {
        Bank bank = new Bank();
        Money result = bank.reduce(Money.dollar(1), "USD");
        Assertions.assertEquals(Money.dollar(1), result);
    }

    @Test
    void testReduceMoneyDifferentCurrency() {
        Bank bank = new Bank();
        bank.addRate("CHF", "USD", 2);
        Money result = bank.reduce(Money.franc(2), "USD");
        Assertions.assertEquals(Money.dollar(1), result);
    }

    @Test
    void testIdentityRate() {
        Assertions.assertEquals(1, new Bank().rate("USD", "USD"));
        Assertions.assertEquals(1, new Bank().rate("CHF", "CHF"));
    }

    @Test
    void testMixedAddition() {
        Expression fiveBucks = Money.dollar(5);
        Expression tenFranks = Money.franc(10);
        Bank bank = new Bank();
        bank.addRate("CHF", "USD", 2);
        Money result = bank.reduce(fiveBucks.plus(tenFranks), "USD");
        Assertions.assertEquals(Money.dollar(10), result);
    }

    @Test
    public void testSumPlusMoney() {
        Expression fiveBucks = Money.dollar(5);
        Expression tenFranks = Money.franc(10);
        Bank bank = new Bank();
        bank.addRate("CHF", "USD", 2);
        Expression sum = new Sum(fiveBucks, tenFranks).plus(fiveBucks);
        Money result = bank.reduce(sum, "USD");
        Assertions.assertEquals(Money.dollar(15), result);
    }

    @Test
    public void testSumTimes() {
        Expression fiveBucks = Money.dollar(5);
        Expression tenFranks = Money.franc(10);
        Bank bank = new Bank();
        bank.addRate("CHF", "USD", 2);
        Expression sum = new Sum(fiveBucks, tenFranks).times(2);
        Money result = bank.reduce(sum, "USD");
        Assertions.assertEquals(Money.dollar(20), result);
    }
}
