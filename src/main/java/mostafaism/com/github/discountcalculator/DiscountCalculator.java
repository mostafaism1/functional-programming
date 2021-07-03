package mostafaism.com.github.discountcalculator;

import java.util.List;
import java.util.stream.Collectors;

public class DiscountCalculator {

    private final int COUNT = 3;

    public Order applyDiscount(Order order, List<DiscountRule> discountRules) {
        double discount = discountRules.stream().filter(discountRule -> discountRule.qualifier().apply(order))
                .mapToDouble(discountRule -> discountRule.calculator().apply(order)).sorted().limit(COUNT).average()
                .orElse(0D);
        return new Order(order.getUnitListPrice(), order.getQuantity(), discount);
    }

    public List<Order> applyDiscountBatch(List<Order> orders, List<DiscountRule> discountRules) {
        return orders.stream().map(order -> this.applyDiscount(order, discountRules)).collect(Collectors.toList());
    }

}
