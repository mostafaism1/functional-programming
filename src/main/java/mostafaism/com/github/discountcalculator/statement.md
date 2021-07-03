# Calculate Order Discount

1. Calculate the discount for a list of orders.
2. Each order has only one product.
3. There are several rules to calculate discount.
4. An order should **qualify** to a criteria in order for its associated rule to apply.
5. A discount rule is composed of **2** parts:
   1. **Qualifier**, which tests if an order qualifies for that rule.
   2. **Calculator**, which calculates the rule's dicsount.
6. Several rules may qualify to the same order.
7. The discount is the **average** of the **lowest three** discounts.
8. The system should allow **adding other rules** in the future **without modification**.
