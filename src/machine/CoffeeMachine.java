package machine;

import java.util.Scanner;

/**
 * Created by Roman Strukov on 01.08.2019.
 */

public class CoffeeMachine {
    private int water;
    private int milk;
    private int coffeeBeans;
    private int cups;
    private int money;
    private Status status;
    private SubStatus subStatus;
    private int fillWater;
    private int fillMilk;
    private int fillBeans;
    private int fillCups;

    public CoffeeMachine() {
        this.water = 400;
        this.milk = 540;
        this.coffeeBeans = 120;
        this.cups = 9;
        this.money = 550;
        this.status = Status.WAIT;
        this.subStatus = SubStatus.WATER;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CoffeeMachine coffeeMachine = new CoffeeMachine();
        coffeeMachine.printRequest();
        while (coffeeMachine.status != Status.EXIT) {
            String action = scanner.nextLine();
            coffeeMachine.action(action);
        }
    }

    private void action(String action) {
        if (status == Status.WAIT) {
            switch (action) {
                case "buy":
                    status = Status.BUY;
                    System.out.print("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu: ");
                    break;
                case "fill":
                    status = Status.FILL;
                    System.out.print("Write how many ml of water do you want to add: ");
                    break;
                case "take":
                    status = Status.GIVE;
                    giveMoney();
                    break;
                case "remaining":
                    status = Status.PRINT;
                    printState();
                    break;
                case "exit":
                    this.status = Status.EXIT;
                    break;
                default:
                    System.out.println("Unknown action");
                    break;
            }
        } else if (status == Status.BUY) {
            if (action.equals("back")) {
                printRequest();
            } else {
                int choice = Integer.parseInt(action) - 1;
                buyCoffee(choice);
            }
        } else if (status == Status.FILL) {
            if (subStatus == SubStatus.WATER) {
                fillWater = Integer.parseInt(action);
                subStatus = SubStatus.MILK;
                System.out.print("Write how many ml of milk do you want to add: ");
            } else if (subStatus == SubStatus.MILK) {
                fillMilk = Integer.parseInt(action);
                subStatus = SubStatus.BEANS;
                System.out.print("Write how many grams of coffee beans do you want to add: ");
            } else if (subStatus == SubStatus.BEANS) {
                fillBeans = Integer.parseInt(action);
                subStatus = SubStatus.CUPS;
                System.out.print("Write how many disposable cups of coffee do you want to add: ");
            } else if (subStatus == SubStatus.CUPS) {
                fillCups = Integer.parseInt(action);
                subStatus = SubStatus.WATER;
                fillMachine(fillWater, fillMilk, fillBeans, fillCups);
                printRequest();
            }
        }
    }

    private void printState() {
        System.out.println("The coffee machine has:");
        System.out.println(water + " of water");
        System.out.println(milk + " of milk");
        System.out.println(coffeeBeans + " of coffee beans");
        System.out.println(cups + " of disposable cups");
        System.out.println(money + " of money");
        printRequest();
    }

    private void printRequest() {
        status = Status.WAIT;
        System.out.print("\nWrite action (buy, fill, take, remaining, exit): ");
    }

    private void giveMoney() {
        System.out.println("I gave you $" + money);
        money = 0;
        printRequest();
    }

    private void buyCoffee(int choice) {
        String errorMessage = "Sorry, not enough ";
        int[] waterForCoffee = new int[]{250, 350, 200};
        int[] milkForCoffee = new int[]{0, 75, 100};
        int[] beansForCoffee = new int[]{16, 20, 12};
        int[] moneyForCoffee = new int[]{4, 7, 6};

        if (water < waterForCoffee[choice]) {
            System.out.println(errorMessage + "water");
        } else if (milk < milkForCoffee[choice]) {
            System.out.println(errorMessage + "milk");
        } else if (coffeeBeans < beansForCoffee[choice]) {
            System.out.println(errorMessage + "coffee beans");
        } else if (cups < 1) {
            System.out.println(errorMessage + "disposable cups");
        } else {
            System.out.println("I have enough resources, making you a coffee!");
            water -= waterForCoffee[choice];
            milk -= milkForCoffee[choice];
            coffeeBeans -= beansForCoffee[choice];
            money += moneyForCoffee[choice];
            cups -= 1;
        }
        printRequest();
    }

    private void fillMachine(int water, int milk, int coffee, int cups) {
        this.water += water;
        this.milk += milk;
        this.coffeeBeans += coffee;
        this.cups += cups;
    }

    private enum Status {
        FILL,
        BUY,
        EXIT,
        GIVE,
        PRINT,
        WAIT
    }

    private enum SubStatus {
        WATER,
        MILK,
        BEANS,
        CUPS
    }
}

