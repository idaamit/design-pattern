package pizzeria.toppings;

import pizzeria.Pizza;

public class MushroomsTopping extends ToppingDecorator {

    private ToppingCoverage toppingCoverage;

    public MushroomsTopping(Pizza pizza, ToppingCoverage toppingCoverage) {
        super(pizza);
        this.toppingCoverage = toppingCoverage;
    }

    @Override
    public String bake() {
        return super.bake() + "Mushrooms";
    }

    @Override
    public double calcPrice() {
        return pizza.calcPrice() + 3 * toppingCoverage.getPart();
    }
}
