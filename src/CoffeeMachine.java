public class CoffeeMachine {
    private int water;
    private int milk;
    private int beans;
    private int cups;
    private int cash;

    CoffeeMachine(int water, int milk, int beans, int cups, int cash) {
        this.water = water;
        this.milk = milk;
        this.beans = beans;
        this.cups = cups;
        this.cash = cash;
    }

    boolean canMakeCoffee(int waterNeeded, int milkNeeded, int beansNeeded) {
        return water >= waterNeeded &&
                milk >= milkNeeded &&
                beans >= beansNeeded &&
                cups >= 1;
    }
    public String makeCoffee(DrinksTypes drinks) {
        if (canMakeCoffee(drinks.getWaterMilliLitrePerCup(), drinks.getMilkMilliLitrePerCup(), drinks.getBeansGramPerCup())) {
            water -= drinks.getWaterMilliLitrePerCup();
            milk -= drinks.getMilkMilliLitrePerCup();
            beans -= drinks.getBeansGramPerCup();
            cups--;
            cash += drinks.getPricePerCup();
            return "I have enough resources, making you a coffee!";
        } else {
            StringBuilder sb = new StringBuilder();
            if (water < drinks.getWaterMilliLitrePerCup()) {
                sb.append("water");
            } else if (milk < drinks.getMilkMilliLitrePerCup()) {
                if (sb.length() > 0) {
                    sb.append(", ");
                }
                sb.append("milk");
            } else if (beans < drinks.getBeansGramPerCup()) {
                if (sb.length() > 0) {
                    sb.append(", ");
                }
                sb.append("coffee beans");
            } else if (cups == 0) {
                if (sb.length() > 0) {
                    sb.append(", ");
                }
                sb.append("disposable cups");
            }
            return "Sorry, not enough" + sb;
        }
    }
    public String makeCoffee(DrinksTypes drinks, int quantity) {
        if (canMakeCoffee(drinks.getWaterMilliLitrePerCup() * quantity, drinks.getMilkMilliLitrePerCup() * quantity, drinks.getBeansGramPerCup() * quantity)) {
            water -= drinks.getWaterMilliLitrePerCup() * quantity;
            milk -= drinks.getMilkMilliLitrePerCup() * quantity;
            beans -= drinks.getBeansGramPerCup() * quantity;
            cups -= quantity;
            cash += drinks.getPricePerCup() * quantity;
            return "I have enough resources, making you a coffee!";
        } else {
            StringBuilder sb = new StringBuilder();
            if (water < drinks.getWaterMilliLitrePerCup() * quantity) {
                sb.append("water");
            } else if (milk < drinks.getMilkMilliLitrePerCup() * quantity) {
                if (sb.length() > 0) {
                    sb.append(", ");
                }
                sb.append("milk");
            } else if (beans < drinks.getBeansGramPerCup() * quantity) {
                if (sb.length() > 0) {
                    sb.append(", ");
                }
                sb.append("coffee beans");
            } else if (cups < quantity) {
                if (sb.length() > 0) {
                    sb.append(", ");
                }
                sb.append("disposable cups");
            }
            return "Sorry, not enough" + sb;
        }
    }

    public int getWater() {
        return water;
    }

    public void setWater(int water) {
        this.water = water;
    }

    public int getMilk() {
        return milk;
    }

    public void setMilk(int milk) {
        this.milk = milk;
    }

    public int getBeans() {
        return beans;
    }

    public void setBeans(int beans) {
        this.beans = beans;
    }

    public int getCups() {
        return cups;
    }

    public void setCups(int cups) {
        this.cups = cups;
    }

    public int getCash() {
        return cash;
    }

    public void setCash(int cash) {
        this.cash = cash;
    }
}
