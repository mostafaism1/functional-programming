package mostafaism.com.github.discountcalculator;

import static org.assertj.core.api.BDDAssertions.then;

import java.util.List;

import org.assertj.core.data.Offset;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DiscountCalculatorTest {

    private DiscountCalculator discountCalculator;
    Offset<Double> offset;

    @BeforeEach
    void setup() {
        discountCalculator = new DiscountCalculator();
        offset = Offset.offset(0.000001D);
    }

    @Test
    void shouldNotApplyDiscount_givenNoQualifyingDiscountRule() {
        // Given.
        Order order = new Order(1, 1, 0);
        DiscountRule discountRule = new DiscountRule((Order o) -> {
            return false;
        }, (Order o) -> {
            return o.getUnitListPrice() * o.getQuantity() * 0.1;
        });
        List<DiscountRule> discountRules = List.of(discountRule);
        double expected = 0D;

        // When.
        double actual = discountCalculator.applyDiscount(order, discountRules).getDiscount();

        // Then.
        then(actual).isEqualTo(expected, offset);
    }

    @Test
    void shouldApplyDiscount_givenOneQualifyingDiscountRule() {
        // Given.
        Order order = new Order(1, 1, 0);
        DiscountRule discountRule = new DiscountRule((Order o) -> {
            return true;
        }, (Order o) -> {
            return o.getUnitListPrice() * o.getQuantity() * 0.1;
        });
        List<DiscountRule> discountRules = List.of(discountRule);
        double expected = 1 * 1 * 0.1;

        // When.
        double actual = discountCalculator.applyDiscount(order, discountRules).getDiscount();

        // Then.
        then(actual).isEqualTo(expected, offset);
    }

    @Test
    void shouldApplyDiscount_givenTwoQualifyingDiscountRules() {
        // Given.
        Order order = new Order(2, 3, 0);
        DiscountRule discountRule1 = new DiscountRule((Order o) -> {
            return true;
        }, (Order o) -> {
            return o.getUnitListPrice() * o.getQuantity() * 0.1;
        });
        DiscountRule discountRule2 = new DiscountRule((Order o) -> {
            return o.getUnitListPrice() > 1;
        }, (Order o) -> {
            return o.getUnitListPrice() * o.getQuantity() * 0.2;
        });
        List<DiscountRule> discountRules = List.of(discountRule1, discountRule2);
        double expected = ((2 * 3 * 0.1) + (2 * 3 * 0.2)) / 2;

        // When.
        double actual = discountCalculator.applyDiscount(order, discountRules).getDiscount();

        // Then.
        then(actual).isEqualTo(expected, offset);
    }

    @Test
    void shouldApplyDiscount_givenThreeQualifyingDiscountRules() {
        // Given.
        Order order = new Order(3, 3, 0);
        DiscountRule discountRule1 = new DiscountRule((Order o) -> {
            return true;
        }, (Order o) -> {
            return o.getUnitListPrice() * o.getQuantity() * 0.1;
        });
        DiscountRule discountRule2 = new DiscountRule((Order o) -> {
            return o.getUnitListPrice() > 1;
        }, (Order o) -> {
            return o.getUnitListPrice() * o.getQuantity() * 0.2;
        });
        DiscountRule discountRule3 = new DiscountRule((Order o) -> {
            return o.getUnitListPrice() > 2;
        }, (Order o) -> {
            return o.getUnitListPrice() * o.getQuantity() * 0.3;
        });
        List<DiscountRule> discountRules = List.of(discountRule1, discountRule2, discountRule3);
        double expected = ((3 * 3 * 0.1) + (3 * 3 * 0.2) + (3 * 3 * 0.3)) / 3;

        // When.
        double actual = discountCalculator.applyDiscount(order, discountRules).getDiscount();

        // Then.
        then(actual).isEqualTo(expected, offset);
    }

    @Test
    void shouldApplyDiscountUsingAverageOfLowestThreeQualifyingDiscountRules_givenFourQualifyingDiscountRules() {
        // Given.
        Order order = new Order(4, 3, 0);
        DiscountRule discountRule1 = new DiscountRule((Order o) -> {
            return true;
        }, (Order o) -> {
            return o.getUnitListPrice() * o.getQuantity() * 0.1;
        });
        DiscountRule discountRule2 = new DiscountRule((Order o) -> {
            return o.getUnitListPrice() > 1;
        }, (Order o) -> {
            return o.getUnitListPrice() * o.getQuantity() * 0.2;
        });
        DiscountRule discountRule3 = new DiscountRule((Order o) -> {
            return o.getUnitListPrice() > 2;
        }, (Order o) -> {
            return o.getUnitListPrice() * o.getQuantity() * 0.3;
        });
        DiscountRule discountRule4 = new DiscountRule((Order o) -> {
            return o.getUnitListPrice() > 3;
        }, (Order o) -> {
            return o.getUnitListPrice() * o.getQuantity() * 0.4;
        });
        List<DiscountRule> discountRules = List.of(discountRule1, discountRule2, discountRule3, discountRule4);
        double expected = ((4 * 3 * 0.1) + (4 * 3 * 0.2) + (4 * 3 * 0.3)) / 3;

        // When.
        double actual = discountCalculator.applyDiscount(order, discountRules).getDiscount();

        // Then.
        then(actual).isEqualTo(expected, offset);
    }

    @Test
    void shouldNotApplyDiscountBatch_givenOneOrderAndNoQualifyingDiscountRule() {
        // Given.
        Order order = new Order(1, 1, 0);
        List<Order> orders = List.of(order);
        DiscountRule discountRule = new DiscountRule((Order o) -> {
            return false;
        }, (Order o) -> {
            return o.getUnitListPrice() * o.getQuantity() * 0.1;
        });
        List<DiscountRule> discountRules = List.of(discountRule);
        double expected = 0D;

        // When.
        List<Order> actual = discountCalculator.applyDiscountBatch(orders, discountRules);

        // Then.
        then(actual.size()).isOne();
        then(actual.get(0).getDiscount()).isEqualTo(expected, offset);
    }

    @Test
    void shouldApplyDiscountBatch_givenOneOrderAndOneQualifyingDiscountRule() {
        // Given.
        Order order = new Order(4, 3, 0);
        List<Order> orders = List.of(order);
        DiscountRule discountRule = new DiscountRule((Order o) -> {
            return true;
        }, (Order o) -> {
            return o.getUnitListPrice() * o.getQuantity() * 0.1;
        });
        List<DiscountRule> discountRules = List.of(discountRule);
        double expected = 4 * 3 * 0.1;

        // When.
        List<Order> actual = discountCalculator.applyDiscountBatch(orders, discountRules);

        // Then.
        then(actual.size()).isOne();
        then(actual.get(0).getDiscount()).isEqualTo(expected, offset);
    }

    @Test
    void shouldApplyDiscountBatch_givenTwoOrdersAndOneQualifyingDiscountRule() {
        // Given.
        Order order1 = new Order(1, 2, 0);
        Order order2 = new Order(2, 3, 0);
        List<Order> orders = List.of(order1, order2);
        DiscountRule discountRule = new DiscountRule((Order o) -> {
            return true;
        }, (Order o) -> {
            return o.getUnitListPrice() * o.getQuantity() * 0.1;
        });
        List<DiscountRule> discountRules = List.of(discountRule);
        double expected1 = 1 * 2 * 0.1;
        double expected2 = 2 * 3 * 0.1;

        // When.
        List<Order> actual = discountCalculator.applyDiscountBatch(orders, discountRules);

        // Then.
        then(actual.size()).isEqualTo(2);
        then(actual.get(0).getDiscount()).isEqualTo(expected1, offset);
        then(actual.get(1).getDiscount()).isEqualTo(expected2, offset);
    }

}
