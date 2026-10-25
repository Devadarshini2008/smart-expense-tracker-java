import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Transaction {
    private final String type;
    private final String category;
    private final double amount;
    private final String description;
    private final YearMonth month;

    public Transaction(String type, String category, double amount,
                       String description, YearMonth month) {
        this.type = type;
        this.category = category;
        this.amount = amount;
        this.description = description;
        this.month = month;
    }

    public String getType() { return type; }
    public String getCategory() { return category; }
    public double getAmount() { return amount; }
    public String getDescription() { return description; }
    public YearMonth getMonth() { return month; }
}

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final List<Transaction> transactions = new ArrayList<>();
    private static double monthlyBudget = 5000.00;

    public static void main(String[] args) {
        boolean running = true;
        System.out.println("Welcome to Smart Expense Tracker!");

        while (running) {
            printMenu();
            int choice = readInt("Choose an option: ");

            switch (choice) {
                case 1 -> addTransaction("INCOME");
                case 2 -> addTransaction("EXPENSE");
                case 3 -> showMonthlySummary();
                case 4 -> setMonthlyBudget();
                case 5 -> showTransactionHistory();
                case 6 -> showCategorySummary();
                case 7 -> {
                    running = false;
                    System.out.println("Thank you for using Smart Expense Tracker.");
                }
                default -> System.out.println("Invalid option. Choose 1–7.");
            }
        }
        scanner.close();
    }

    private static void printMenu() {
        System.out.println("\n========== SMART EXPENSE TRACKER ==========");
        System.out.println("1. Add income");
        System.out.println("2. Add expense");
        System.out.println("3. View monthly summary");
        System.out.println("4. Set monthly budget");
        System.out.println("5. View transaction history");
        System.out.println("6. View expense totals by category");
        System.out.println("7. Exit");
    }

    private static void addTransaction(String type) {
        double amount = readPositiveAmount("Enter amount (₹): ");
        if (amount <= 0) return;

        String category = readRequiredText("Enter category (e.g., Food, Travel): ");
        String description = readRequiredText("Enter description: ");

        transactions.add(new Transaction(
            type, category, amount, description, YearMonth.now()
        ));
        System.out.println(type + " added successfully.");

        if ("EXPENSE".equals(type)) {
            showBudgetAlert();
        }
    }

    private static void showMonthlySummary() {
        double income = getMonthlyTotal("INCOME");
        double expenses = getMonthlyTotal("EXPENSE");

        System.out.println("\n------------ MONTHLY SUMMARY ------------");
        System.out.printf("Month: %s%n", YearMonth.now());
        System.out.printf("Total income:        ₹%.2f%n", income);
        System.out.printf("Total expenses:      ₹%.2f%n", expenses);
        System.out.printf("Balance:             ₹%.2f%n", income - expenses);
        System.out.printf("Monthly budget:      ₹%.2f%n", monthlyBudget);
        System.out.printf("Budget remaining:    ₹%.2f%n", monthlyBudget - expenses);
        showBudgetAlert();
    }

    private static void setMonthlyBudget() {
        double budget = readPositiveAmount("Enter monthly budget (₹): ");
        if (budget <= 0) return;

        monthlyBudget = budget;
        System.out.printf("Monthly budget updated to ₹%.2f%n", monthlyBudget);
        showBudgetAlert();
    }

    private static void showTransactionHistory() {
        if (transactions.isEmpty()) {
            System.out.println("No transactions recorded yet.");
            return;
        }

        System.out.println("\n---------------- TRANSACTION HISTORY ----------------");
        System.out.printf("%-10s %-8s %-14s %-12s %s%n",
                "Month", "Type", "Amount", "Category", "Description");

        for (Transaction t : transactions) {
            System.out.printf("%-10s %-8s ₹%-13.2f %-12s %s%n",
                    t.getMonth(), t.getType(), t.getAmount(),
                    t.getCategory(), t.getDescription());
        }
    }

    private static void showCategorySummary() {
        boolean found = false;
        System.out.println("\n-------- CURRENT MONTH EXPENSES BY CATEGORY --------");

        for (Transaction t : transactions) {
            if (!"EXPENSE".equals(t.getType()) ||
                    !YearMonth.now().equals(t.getMonth())) {
                continue;
            }

            double total = 0;
            for (Transaction other : transactions) {
                if ("EXPENSE".equals(other.getType())
                        && YearMonth.now().equals(other.getMonth())
                        && t.getCategory().equalsIgnoreCase(other.getCategory())) {
                    total += other.getAmount();
                }
            }

            System.out.printf("%-18s ₹%.2f%n", t.getCategory(), total);
            found = true;

            // Avoid printing a category more than once.
            // Earlier transactions of the same category are detected below.
            for (Transaction earlier : transactions) {
                if (earlier == t) break;
                if ("EXPENSE".equals(earlier.getType())
                        && YearMonth.now().equals(earlier.getMonth())
                        && earlier.getCategory().equalsIgnoreCase(t.getCategory())) {
                    found = false; // This entry was already represented.
                    break;
                }
            }
        }

        if (!found) {
            System.out.println("No expenses recorded for this month.");
        }
    }

    private static double getMonthlyTotal(String type) {
        double total = 0;
        for (Transaction t : transactions) {
            if (type.equals(t.getType()) && YearMonth.now().equals(t.getMonth())) {
                total += t.getAmount();
            }
        }
        return total;
    }

    private static void showBudgetAlert() {
        double expenses = getMonthlyTotal("EXPENSE");
        double percentage = (expenses / monthlyBudget) * 100;

        if (percentage >= 100) {
            System.out.printf("ALERT: You have used %.1f%% of your budget. Budget exceeded!%n",
                    percentage);
        } else if (percentage >= 80) {
            System.out.printf("WARNING: You have used %.1f%% of your budget.%n",
                    percentage);
        } else {
            System.out.printf("Budget usage: %.1f%%%n", percentage);
        }
    }

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a whole number.");
            }
        }
    }

    private static double readPositiveAmount(String prompt) {
        System.out.print(prompt);
        try {
            double amount = Double.parseDouble(scanner.nextLine().trim());
            if (!Double.isFinite(amount) || amount <= 0) {
                System.out.println("Amount must be a valid number greater than zero.");
                return -1;
            }
            return amount;
        } catch (NumberFormatException e) {
            System.out.println("Invalid amount. Please enter a number such as 250 or 250.50.");
            return -1;
        }
    }

    private static String readRequiredText(String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = scanner.nextLine().trim();
            if (!value.isEmpty()) return value;
            System.out.println("This field cannot be empty.");
        }
    }
}
