package com.banking.dao;

import com.banking.database.DatabaseConnection;
import com.banking.model.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountDAO {

    public void getAllAccounts() {

        String query = "SELECT * FROM accounts";

        try (

                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement preparedStatement =
                        connection.prepareStatement(query);

                ResultSet resultSet =
                        preparedStatement.executeQuery()

        ) {

            while (resultSet.next()) {

                Account account = new Account(

                        resultSet.getInt("id"),
                        resultSet.getString("owner_name"),
                        resultSet.getString("account_number"),
                        resultSet.getDouble("balance")
                );

                System.out.println(account);
            }

        } catch (SQLException e) {

            System.out.println("Error retrieving accounts.");

            e.printStackTrace();
        }
    }
    public boolean accountExists(String accountNumber) {

        String query = """
            SELECT id
            FROM accounts
            WHERE account_number = ?
            """;

        try (

                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement preparedStatement =
                        connection.prepareStatement(query)

        ) {

            preparedStatement.setString(1, accountNumber);

            ResultSet resultSet =
                    preparedStatement.executeQuery();

            return resultSet.next();

        } catch (SQLException e) {

            e.printStackTrace();

            return false;
        }
    }
    public void deposit(String accountNumber, double amount) {
        if (!accountExists(accountNumber)) {

            System.out.println("Account not found.");

            return;
        }
        String query = """
            UPDATE accounts
            SET balance = balance + ?
            WHERE account_number = ?
            """;

        try (

                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement preparedStatement =
                        connection.prepareStatement(query)

        ) {

            preparedStatement.setDouble(1, amount);
            preparedStatement.setString(2, accountNumber);

            int rowsAffected =
                    preparedStatement.executeUpdate();

            if (rowsAffected > 0) {

                System.out.println("Deposit completed successfully.");

            } else {

                System.out.println("Account not found.");
            }

        } catch (SQLException e) {

            System.out.println("Error processing deposit.");

            e.printStackTrace();
        }
    }
    public void withdraw(String accountNumber, double amount) {
        if (!accountExists(accountNumber)) {

            System.out.println("Account not found.");

            return;
        }
        String selectQuery = """
            SELECT balance
            FROM accounts
            WHERE account_number = ?
            """;

        String updateQuery = """
            UPDATE accounts
            SET balance = balance - ?
            WHERE account_number = ?
            """;

        try (

                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement selectStatement =
                        connection.prepareStatement(selectQuery)

        ) {

            selectStatement.setString(1, accountNumber);

            ResultSet resultSet =
                    selectStatement.executeQuery();

            if (resultSet.next()) {

                double currentBalance =
                        resultSet.getDouble("balance");

                if (currentBalance >= amount) {

                    try (

                            PreparedStatement updateStatement =
                                    connection.prepareStatement(updateQuery)

                    ) {

                        updateStatement.setDouble(1, amount);
                        updateStatement.setString(2, accountNumber);

                        updateStatement.executeUpdate();

                        System.out.println("Withdrawal completed successfully.");
                    }

                } else {

                    System.out.println("Insufficient funds.");
                }

            } else {

                System.out.println("Account not found.");
            }

        } catch (SQLException e) {

            System.out.println("Error processing withdrawal.");

            e.printStackTrace();
        }
    }
    public void transfer(
            String fromAccount,
            String toAccount,
            double amount
    ) {

        if (!accountExists(fromAccount)) {

            System.out.println("Origin account not found.");

            return;
        }

        if (!accountExists(toAccount)) {

            System.out.println("Destination account not found.");

            return;
        }

        String balanceQuery = """
            SELECT balance
            FROM accounts
            WHERE account_number = ?
            """;

        String withdrawQuery = """
            UPDATE accounts
            SET balance = balance - ?
            WHERE account_number = ?
            """;

        String depositQuery = """
            UPDATE accounts
            SET balance = balance + ?
            WHERE account_number = ?
            """;

        Connection connection = null;

        try {

            connection =
                    DatabaseConnection.getConnection();

            connection.setAutoCommit(false);

            PreparedStatement balanceStatement =
                    connection.prepareStatement(balanceQuery);

            balanceStatement.setString(1, fromAccount);

            ResultSet resultSet =
                    balanceStatement.executeQuery();

            if (resultSet.next()) {

                double currentBalance =
                        resultSet.getDouble("balance");

                if (currentBalance < amount) {

                    System.out.println("Insufficient funds.");

                    return;
                }
            }

            PreparedStatement withdrawStatement =
                    connection.prepareStatement(withdrawQuery);

            withdrawStatement.setDouble(1, amount);
            withdrawStatement.setString(2, fromAccount);

            withdrawStatement.executeUpdate();

            PreparedStatement depositStatement =
                    connection.prepareStatement(depositQuery);

            depositStatement.setDouble(1, amount);
            depositStatement.setString(2, toAccount);

            depositStatement.executeUpdate();

            connection.commit();

            System.out.println("Transfer completed successfully.");

        } catch (SQLException e) {

            try {

                if (connection != null) {

                    connection.rollback();
                }

            } catch (SQLException rollbackError) {

                rollbackError.printStackTrace();
            }

            System.out.println("Transfer failed.");

            e.printStackTrace();

        } finally {

            try {

                if (connection != null) {

                    connection.setAutoCommit(true);

                    connection.close();
                }

            } catch (SQLException e) {

                e.printStackTrace();
            }
        }
    }
}