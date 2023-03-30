public enum DrinksTypes {
    ESPRESSO(250, 0, 16, 45, "C:\\Users\\kitti\\IdeaProjects\\CoffeeMachineGUIFX\\src\\main\\resources\\com\\coffeemachine\\coffeemachineguifx\\order-icon.png"),
    LATTE(350, 75, 20, 30, "C:\\Users\\kitti\\IdeaProjects\\CoffeeMachineGUIFX\\src\\main\\resources\\com\\coffeemachine\\coffeemachineguifx\\order-icon.png"),
    CAPPUCCINO(200, 100, 12, 35, "C:\\Users\\kitti\\IdeaProjects\\CoffeeMachineGUIFX\\src\\main\\resources\\com\\coffeemachine\\coffeemachineguifx\\order-icon.png");

    private final String s;
    private final int waterMilliLitrePerCup;
    private final int milkMilliLitrePerCup;
    private final int beansGramPerCup;
    private final int pricePerCup;

    DrinksTypes(int waterMilliLitrePerCup, int milkMilliLitrePerCup, int beansGramPerCup, int pricePerCup, String s) {
        this.waterMilliLitrePerCup = waterMilliLitrePerCup;
        this.milkMilliLitrePerCup = milkMilliLitrePerCup;
        this.beansGramPerCup = beansGramPerCup;
        this.pricePerCup = pricePerCup;
        this.s = s;
    }

    public int getWaterMilliLitrePerCup() {
        return waterMilliLitrePerCup;
    }

    public int getMilkMilliLitrePerCup() {
        return milkMilliLitrePerCup;
    }

    public int getBeansGramPerCup() {
        return beansGramPerCup;
    }

    public int getPricePerCup() {
        return pricePerCup;
    }

    public String getS() {
        return s;
    }
}
