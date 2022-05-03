package machine;

import java.util.Scanner;

class Coffee {
    int water;
    int milk;
    int coffeeBeans;
    int cost;
    int cup;

    public Coffee(int water, int milk, int coffeeBeans, int cost, int cup) {
        this.water = water;
        this.milk = milk;
        this.coffeeBeans = coffeeBeans;
        this.cost = cost;
        this.cup = cup;
    }
}

class Espresso extends Coffee{

    public Espresso() {
        super(250, 0, 16, 4, 1);
    }
}

class Latte extends Coffee{

    public Latte() {
        super(350, 75, 20, 7, 1);
    }
}

class Cappuccino extends Coffee{

    public Cappuccino() {
        super(200, 100, 12, 6, 1);
    }
}

class SimpleCoffeeFactory {
    public Coffee createCoffee(String type) throws NoThisTypeOfCoffeeException {
        Coffee coffee = null;

        switch (type) {
            case "1":
                coffee = new Espresso();
                break;
            case "2":
                coffee = new Latte();
                break;
            case "3":
                coffee = new Cappuccino();
                break;
            case "back":
                throw new NoThisTypeOfCoffeeException();
        }
        return coffee;
    }
}

class NotEnoughIngridientsException extends Exception{
    public NotEnoughIngridientsException(String message) {
        super(message);
    }
}

class NoThisTypeOfCoffeeException extends Exception{}

public class CoffeeMachine {
    private static int waterVolume;
    private static int milkVolume;
    private static int coffeeBeansVolume;
    private static int disposableCups;
    private static int money;
    private static final SimpleCoffeeFactory coffeeFactory = new SimpleCoffeeFactory();

    public CoffeeMachine(int waterVolume, int milkVolume, int coffeeBeansVolume, int disposableCups, int money) {
        this.waterVolume = waterVolume;
        this.milkVolume = milkVolume;
        this.coffeeBeansVolume = coffeeBeansVolume;
        this.disposableCups = disposableCups;
        this.money = money;

    }

    public static void main(String[] args) {
        createCoffeeMachine();
        getUserAction();
    }

    public static CoffeeMachine createCoffeeMachine() {
        return new CoffeeMachine(400, 540, 120, 9, 550);
    }

    public static void getUserAction() {
        Scanner scanner = new Scanner(System.in);
        boolean flag  = true;
        while (flag) {
            System.out.println("Write action (buy, fill, take, remaining, exit):");
            String action = scanner.nextLine();
            try {
                switch (action) {
                    case "buy":
                        buyCoffeeAction();
                        break;
                    case "fill":
                        fillCoffeeMachineAction();
                        break;
                    case "take":
                        takeMoneyAction();
                        break;
                    case "remaining":
                        remainingAction();
                        break;
                    case "exit":
                        flag = false;
                        break;
                }
            } catch (NotEnoughIngridientsException exception) {
                System.out.printf("Sorry, not enough %s! \n", exception.getMessage());
                flag = false;
                getUserAction();
            } catch (NoThisTypeOfCoffeeException exception) {
                flag = false;
                getUserAction();
            }
        }
    }

    public static void buyCoffeeAction() throws NotEnoughIngridientsException, NoThisTypeOfCoffeeException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu: ");
        String coffeeType = scanner.nextLine();
        Coffee coffee = coffeeFactory.createCoffee(coffeeType);
        if (waterVolume < coffee.water) {
            throw new NotEnoughIngridientsException("water");
        }
        if (milkVolume < coffee.milk) {
            throw new NotEnoughIngridientsException("milk");
        }
        if (coffeeBeansVolume < coffee.coffeeBeans) {
            throw new NotEnoughIngridientsException("coffee beans");
        }
        System.out.println("I have enough resources, making you a coffee!");
        calculateCoffeeMachineIngridientsVolumeAfterCoffeePurchase(coffee);
    }

    public static void fillCoffeeMachineAction(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Write how many ml of water you want to add: ");
        waterVolume += scanner.nextInt();
        System.out.println("Write how many ml of milk you want to add: ");
        milkVolume += scanner.nextInt();
        System.out.println("Write how many grams of coffee beans you want to add: ");
        coffeeBeansVolume += scanner.nextInt();
        System.out.println("Write how many disposable cups of coffee you want to add: ");
        disposableCups += scanner.nextInt();

    }

    public static void calculateCoffeeMachineIngridientsVolumeAfterCoffeePurchase(Coffee coffee) {
        waterVolume -= coffee.water;
        milkVolume -= coffee.milk;
        coffeeBeansVolume -= coffee.coffeeBeans;
        disposableCups -= coffee.cup;
        money += coffee.cost;
    }

    public static void takeMoneyAction(){
        System.out.printf("I gave you " + "$" + "%s \n", money);
        money = 0;
    }

    public static void printCoffeeMachineStatistics() {
        System.out.println("The coffee machine has:");
        System.out.printf("%d ml of water \n", waterVolume);
        System.out.printf("%d ml of milk \n", milkVolume);
        System.out.printf("%d g of coffee beans \n", coffeeBeansVolume);
        System.out.printf("%d disposable cups \n", disposableCups);
        System.out.printf("$"+ "%d of money \n", money);
        System.out.println();
    }

    public static void remainingAction() {
        printCoffeeMachineStatistics();
    }

}
