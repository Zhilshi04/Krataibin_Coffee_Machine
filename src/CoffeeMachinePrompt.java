import java.util.Scanner;

public class CoffeeMachinePrompt extends CoffeeMachine{
    private static final Scanner scanner = new Scanner(System.in);
    public CoffeeMachinePrompt(int water, int milk, int beans, int cups, int cash) {
        super(water, milk, beans, cups, cash);
    }

    public void doBuyCoffee() {
        System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:");
        String option = scanner.next();
        if (option.equals("back")) {
            // go back to main
        } else {
            int coffeeType = Integer.parseInt(option);
            DrinksTypes drinks = DrinksTypes.values()[coffeeType - 1];

            switch (drinks) { //ดูเงื่อนไขจาก values ของ Enum
                case ESPRESSO, CAPPUCCINO, LATTE -> { // ถ้าตรงกับ ESPRESSO หรือ CAPPUCCINO หรือ LATTE
                    String coffeeMessage = super.makeCoffee(drinks); //ให้เรียก super.makeCoffee(values ของ Enum); และค่าที่ return จาก makeCoffee => coffeeMessage
                    System.out.println(coffeeMessage); //print coffeeMessage
                }
                default -> {
                }
                // do nothing
            }
        }
    }

    public void doFilling() {
        System.out.println("Write how many ml of water you want to add:");
        int addedWater = scanner.nextInt();
        super.setWater(super.getWater() + addedWater);

        System.out.println("Write how many ml of milk you want to add:");
        int addedMilk = scanner.nextInt();
        super.setMilk(super.getMilk() + addedMilk);

        System.out.println("Write how many grams of coffee beans you want to add:");
        int addedBeans = scanner.nextInt();
        super.setBeans(super.getBeans() + addedBeans);

        System.out.println("Write how many disposable cups you want to add:");
        int addedCups = scanner.nextInt();
        super.setCups(super.getCups() + addedCups);
    }

    public void doTakeMoney() {
        System.out.println("I gave you $" + super.getCash());
        super.setCash(0);
    }

    public boolean execute(String action) {
        boolean done = false;
        switch (action) {
            case "buy" -> doBuyCoffee();
            case "fill" -> doFilling();
            case "take" -> doTakeMoney();
            case "remaining" -> printState();
            case "exit" -> {
                done = true;
            }
            default -> {
            }
            // do nothing
        }
        return done;
    }
    public static void main(String[] args) {
        CoffeeMachinePrompt machineTextUI = new CoffeeMachinePrompt(400, 540, 120, 9, 550);
        boolean done = false;
        do {
            System.out.println("\nWrite action (buy, fill, take, remaining, exit): ");
            String action = scanner.next();
            done = machineTextUI.execute(action);
        } while (!done);
    }

    public void printState() {
        System.out.println("\nThe coffee machine has:");
        System.out.printf("%d ml of water\n", super.getWater());
        System.out.printf("%d ml of milk\n", super.getMilk());
        System.out.printf("%d g of coffee beans%n", super.getBeans());
        System.out.printf("%d disposable cups%n", super.getCups());
        System.out.printf("$%d of money%n", super.getCash());

    }
}
