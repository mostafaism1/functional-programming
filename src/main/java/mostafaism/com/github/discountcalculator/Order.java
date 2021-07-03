package mostafaism.com.github.discountcalculator;

import lombok.Data;

@Data
public class Order {
    /*
     * The order model is kept very simplistic for educational purposes. In a more
     * realistic scenario, you can imagine splitting this model into an Order and a
     * Product models.
     */
    private final double unitListPrice;
    private final int quantity;
    private final double discount;
}
