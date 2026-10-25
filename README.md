# Smart Expense Tracker with Budget Alerts

A beginner-friendly Java mini project for recording income and expenses, reviewing monthly totals, setting a budget, and receiving budget-use warnings.

## Features

- Add income and expense transactions
- Assign a category and description to each transaction
- View current-month income, expenses, balance, and remaining budget
- Set a monthly spending budget
- Warn at 80% budget usage and alert at or above 100%
- View transaction history
- View expense totals by category
- Validate numeric inputs and required text

## Technology

- Java 21 or later
- Object-oriented programming
- `ArrayList` collections
- `java.time.YearMonth`
- Exception handling

## Run in VS Code (Windows)

1. Install a JDK and the **Extension Pack for Java** in VS Code.
2. Open this project folder in VS Code.
3. Open **Terminal → New Terminal**.
4. Compile:

   ```powershell
   javac Main.java
   ```

5. Run:

   ```powershell
   java Main
   ```

## Example test

1. Choose `1`, enter income `8000`, category `Pocket Money`, description `Monthly allowance`.
2. Choose `4`, set the budget to `5000`.
3. Choose `2`, enter expense `4200`, category `Food`, description `Food expenses`.
4. Choose `3` to see the summary and 80% warning.

## Current limitations

This starter stores transactions and the budget in memory. Data resets when the application exits. The category summary is a simple console report. MySQL persistence and a graphical user interface are not yet implemented.

## Planned enhancements

- MySQL database persistence using JDBC
- Java Swing graphical interface
- User registration and login
- Edit and delete transactions
- AI-assisted spending insights
- Cloud deployment

## Author

Add your name here.
