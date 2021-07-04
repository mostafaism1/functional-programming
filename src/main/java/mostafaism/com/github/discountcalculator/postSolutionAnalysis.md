# Post Solution Analysis

### Q. How would you implement this using an imperative style, let's say an OO style?

## Answer

- You would probably need to create an **interface / abstract class** for the discount rules, with 2 methods:
  1. `qualifies`
  2. `computeDiscount`
- You would then have multiple **concrete implementations** of this interface for each discount rule you want to support in your system.
- To compute the discount for an order, you would first get a list with all the discount rules.
- Then you'd iterate over all the discount rules, check if they qualify for the order, if so you compute the discount.
- Then you'd sort all the discounts.
- Take the first 3.
- Compute average.

### Q. Compare the 2 styles.

- I suppose that the fact that an OO style requires a separate class implementation for each discount rule in the system, can cause a class explosion, if the number of discount rules is large.
- Functional style abstracts/hides the explicit iteration over the discount rules, making the code clearer.
- Functional style makes the sorting operation, extracting first n, and computing the average declarative, making the code clearer and maintainable.
