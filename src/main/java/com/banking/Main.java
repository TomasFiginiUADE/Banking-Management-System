package com.banking;

import com.banking.dao.AccountDAO;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        AccountDAO accountDAO = new AccountDAO();

        int option;

        do {

            System.out.println("""
                    
                    ===== BANKING MANAGEMENT SYSTEM =====
                    
                    1 - Show accounts
                    2 - Deposit money
                    3 - Withdraw money
                    4 - Transfer money
                    5 - Exit
                    
                    Select an option:
                    """);

            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {

                case 1:

                    accountDAO.getAllAccounts();

                    break;

                case 2:

                    System.out.println("Account number:");

                    String depositAccount =
                            scanner.nextLine();

                    if (!accountDAO.accountExists(depositAccount)) {

                        System.out.println("Account not found.");

                        break;
                    }

                    System.out.println("Amount:");

                    double depositAmount =
                            scanner.nextDouble();

                    scanner.nextLine();

                    accountDAO.deposit(
                            depositAccount,
                            depositAmount
                    );

                    break;

                case 3:

                    System.out.println("Account number:");

                    String withdrawAccount =
                            scanner.nextLine();

                    if (!accountDAO.accountExists(withdrawAccount)) {

                        System.out.println("Account not found.");

                        break;
                    }

                    System.out.println("Amount:");

                    double withdrawAmount =
                            scanner.nextDouble();

                    scanner.nextLine();

                    accountDAO.withdraw(
                            withdrawAccount,
                            withdrawAmount
                    );

                    break;

                case 4:

                    System.out.println("Origin account:");

                    String fromAccount =
                            scanner.nextLine();

                    System.out.println("Destination account:");

                    String toAccount =
                            scanner.nextLine();

                    System.out.println("Amount:");

                    double transferAmount =
                            scanner.nextDouble();

                    scanner.nextLine();

                    accountDAO.transfer(
                            fromAccount,
                            toAccount,
                            transferAmount
                    );

                    break;

                case 5:

                    System.out.println("Closing system.");

                    break;

                default:

                    System.out.println("Invalid option.");
            }

        } while (option != 5);

        scanner.close();
    }
}