package machine;

import java.util.Scanner;

public class CoffeeMachine {
    private static final Scanner scanner = new Scanner(System.in);

    private int water;
    private int milk;
    private int coffeeBeans;
    private int disposableCups;
    private int money;
    private int cleanCounter;

    public static void main(String[] args) {
        Coffee espresso = new Coffee("espresso", 250, 0, 16, 4);
        Coffee latte = new Coffee("latte", 350, 75, 20, 7);
        Coffee cappuccino = new Coffee("cappuccino", 200, 100, 12, 6);

        Coffee[] coffees = new Coffee[] {espresso, latte, cappuccino};
        CoffeeMachine coffeeMachine = new CoffeeMachine(400, 540, 120, 9, 550);

        menu(coffeeMachine, coffees);
        scanner.close();
    }

    static void menu(CoffeeMachine coffeeMachine, Coffee[] coffees) {
        String choice;
        while (true) {
            System.out.println("Write action (buy, fill, take, clean, remaining, exit)");
            choice = enterInput();

            System.out.println();
            switch (choice.toLowerCase()) {
                case "buy":
                    buy(coffeeMachine, coffees);
                    break;
                case "fill":
                    fill(coffeeMachine);
                    break;
                case "take":
                    coffeeMachine.take();
                    break;
                case "clean":
                    coffeeMachine.clean();
                case "remaining":
                    coffeeMachine.printCoffeeMachine();
                    break;
                case "exit":
                    return;
            }
            System.out.println();
        }
    }
    
    static void fill(CoffeeMachine coffeeMachine) {
        System.out.println("Write how many ml of water you want to add:");
        coffeeMachine.addWater(enterInteger());
        
        System.out.println("Write how many ml of milk you want to add:");
        coffeeMachine.addMilk(enterInteger());

        System.out.println("Write how many grams of coffee beans you want to add:");
        coffeeMachine.addCoffeeBeans(enterInteger());

        System.out.println("Write how many disposable cups you want to add:");
        coffeeMachine.addCups(enterInteger());
    }

    static void buy(CoffeeMachine coffeeMachine, Coffee[] coffees) {
        if (coffeeMachine.getCleanCounter() >= 10) {
            System.out.println("I need cleaning!");
            return;
        }

        String choice;
        StringBuilder menu = new StringBuilder();
        menu.append("What do you want to buy? ");
        for (int i = 0; i < coffees.length; i++) {
            menu.append((i + 1)).append(" - ").append(coffees[i].getName()).append(", ");
        }
        menu.append("back - to main menu:");
        while (true) {
            System.out.println(menu);
            choice = enterInput();
            for (int i = 0; i < coffees.length; i++) {
                if (((i + 1) + "").equals(choice)) {
                    coffeeMachine.makeCoffee(coffees[i]);
                    return;
                }
            }
            if ("back".equals(choice)) {
                return;
            } else {
                System.out.println("Wrong input.");
            }
        }
    }

    static String enterInput() {
        String input = scanner.next();
        scanner.nextLine();
        return input;
    }
    
    static int enterInteger() {
        while (true) {
            try {
                return Integer.parseInt(enterInput());
            } catch (Exception e) {
                System.out.println("Wrong input.");
            }
        }
    }

    public CoffeeMachine(int water, int milk, int coffeeBeans, int disposableCups, int money) {
        this.water = water;
        this.milk = milk;
        this.coffeeBeans = coffeeBeans;
        this.disposableCups = disposableCups;
        this.money = money;
        this.cleanCounter = 0;
    }

    void makeCoffee(Coffee coffee) {
        if (checkCoffee(coffee).length() > 17) {
            System.out.println(checkCoffee(coffee));
            return;
        }

        this.water -= coffee.getWater();
        this.milk -= coffee.getMilk();
        this.coffeeBeans -= coffee.getCoffeeBeans();
        this.disposableCups--;
        this.money += coffee.getPrice();
        this.cleanCounter++;
        System.out.println("I have enough resources, making you a coffee!");
    }
    
    String checkCoffee(Coffee coffee) {
        StringBuilder error = new StringBuilder();
        error.append("Sorry, not enough");
        if (this.water < coffee.getWater()) {
            error.append(" water!");
        }
        if (this.milk < coffee.getMilk()) {
            error.append(" milk!");
        }
        if (this.coffeeBeans < coffee.getCoffeeBeans()) {
            error.append(" coffee beans!");
        }
        if (this.disposableCups < 1) {
            error.append(" disposable cups!");
        }
        return error.toString();
    }

    public void printCoffeeMachine() {
        String coffeeMachine = "The coffee machine has:\n" +
                this.water + " ml of water\n" +
                this.milk + " ml of milk\n" +
                this.coffeeBeans + " g of coffee beans\n" +
                this.disposableCups + " disposable cups\n" +
                "$" + this.money + " of money";
        System.out.println(coffeeMachine);
    }

    public void clean() {
        System.out.println("I have been cleaned!");
        this.cleanCounter = 0;
    }

    public void take() {
        System.out.println("I gave you $" + this.money);
        this.money = 0;
    }

    public void addWater(int water) {
        this.water += water;
    }

    public void addMilk(int milk) {
        this.milk += milk;
    }

    public void addCoffeeBeans(int coffeeBeans) {
        this.coffeeBeans += coffeeBeans;
    }

    public void addCups(int disposableCups) {
        this.disposableCups += disposableCups;
    }

    public int getCleanCounter() {
        return this.cleanCounter;
    }
}

class Coffee {
    private final String name;
    private final int water;
    private final int milk;
    private final int coffeeBeans;
    private final int price;

    public Coffee(String name, int water, int milk, int coffeeBeans, int price) {
        this.name = name;
        this.water = water;
        this.milk = milk;
        this.coffeeBeans = coffeeBeans;
        this.price = price;
    }

    int getPrice() {
        return this.price;
    }

    int getWater() {
        return this.water;
    }

    int getMilk() {
        return this.milk;
    }

    int getCoffeeBeans() {
        return this.coffeeBeans;
    }

    String getName() {
        return name;
    }
}