package Week1;

import java.util.Scanner;
import java.util.concurrent.locks.ReentrantLock;

class BankAccount {
    private double balance;
    private final ReentrantLock lock = new ReentrantLock(); // Ensures thread safety

    public BankAccount(double initialBalance) {
        this.balance = initialBalance;
    }

    // Synchronized deposit method
    public void deposit(double amount) {
        lock.lock();
        try {
            if (amount > 0) {
                balance += amount;
                System.out.println(
                        Thread.currentThread().getName() + " deposited: $" + amount + " | New Balance: $" + balance);
            } else {
                System.out.println("Invalid deposit amount.");
            }
        } finally {
            lock.unlock();
        }
    }

    // Synchronized withdraw method
    public void withdraw(double amount) {
        lock.lock();
        try {
            if (amount > 0 && balance >= amount) {
                balance -= amount;
                System.out.println(
                        Thread.currentThread().getName() + " withdrew: $" + amount + " | New Balance: $" + balance);
            } else if (amount > 0) {
                System.out.println(Thread.currentThread().getName() + " tried to withdraw $" + amount
                        + " but insufficient funds! Current Balance: $" + balance);
            } else {
                System.out.println("Invalid withdrawal amount.");
            }
        } finally {
            lock.unlock();
        }
    }

    public double getBalance() {
        return balance;
    }
}

// Thread class for deposits
class DepositThread extends Thread {
    private BankAccount account;
    private double amount;

    public DepositThread(BankAccount account, double amount) {
        this.account = account;
        this.amount = amount;
    }

    @Override
    public void run() {
        account.deposit(amount);
    }
}

// Thread class for withdrawals
class WithdrawThread extends Thread {
    private BankAccount account;
    private double amount;

    public WithdrawThread(BankAccount account, double amount) {
        this.account = account;
        this.amount = amount;
    }

    @Override
    public void run() {
        account.withdraw(amount);
    }
}

public class Task2 {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.print("Enter initial bank balance: ");
        double initialBalance = s.nextDouble();
        BankAccount account = new BankAccount(initialBalance);

        while (true) {
            System.out.println("\n1. Deposit Money");
            System.out.println("2. Withdraw Money");
            System.out.println("3. Show Balance");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");

            int choice = s.nextInt();

            if (choice == 4) {
                System.out.println("Exiting...");
                break;
            }

            System.out.print("Enter amount: ");
            double amount = s.nextDouble();

            Thread transactionThread;
            if (choice == 1) {
                transactionThread = new DepositThread(account, amount);
            } else if (choice == 2) {
                transactionThread = new WithdrawThread(account, amount);
            } else {
                System.out.println("Invalid option. Try again.");
                continue;
            }

            transactionThread.start(); // Start the thread

            try {
                transactionThread.join(); // Ensure the transaction completes before the next input
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("Updated Balance: $" + account.getBalance());
        }

        s.close();
    }
}
