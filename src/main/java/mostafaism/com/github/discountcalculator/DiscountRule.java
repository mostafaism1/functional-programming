package mostafaism.com.github.discountcalculator;

import java.util.function.Function;

public record DiscountRule(Function<Order, Boolean> qualifier, Function<Order, Double> calculator) {

}
