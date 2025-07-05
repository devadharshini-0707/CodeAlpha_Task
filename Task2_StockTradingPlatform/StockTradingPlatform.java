import java.util.*;i
import java.io.*;

public class Main {

    static class Stock {
        String name;
        String symbol;
        double price;

        Stock(String name, String symbol, double price) {
            this.name = name;
            this.symbol = symbol;
            this.price = price;
        }
    }

    static class User {
        String username;
        double balance;
        Map<String, Integer> portfolio;

        User(String username, double balance) {
            this.username = username;
            this.balance = balance;
            this.portfolio = new HashMap<>();
        }

        void buyStock(Stock stock, int quantity) {
            double cost = stock.price * quantity;
            if (balance >= cost) {
                balance -= cost;
                portfolio.put(stock.symbol, portfolio.getOrDefault(stock.symbol, 0) + quantity);
                System.out.println("Bought " + quantity + " of " + stock.name);
            } else {
                System.out.println("Insufficient balance.");
            }
        }

        void sellStock(Stock stock, int quantity) {
            int owned = portfolio.getOrDefault(stock.symbol, 0);
            if (owned >= quantity) {
                portfolio.put(stock.symbol, owned - quantity);
                balance += stock.price * quantity;
                System.out.println("Sold " + quantity + " of " + stock.name);
                if (portfolio.get(stock.symbol) == 0) {
                    portfolio.remove(stock.symbol);
                }
            } else {
                System.out.println("You don't have enough shares to sell.");
            }
        }

        void showPortfolio(List<Stock> stockList) {
            System.out.println("\nPortfolio of " + username + ":");
            if (portfolio.isEmpty()) {
                System.out.println("No stocks owned.");
            } else {
                for (String symbol : portfolio.keySet()) {
                    int qty = portfolio.get(symbol);
                    double price = 0;
                    for (Stock s : stockList) {
                        if (s.symbol.equals(symbol)) {
                            price = s.price;
                            break;
                        }
                    }
                    System.out.println(symbol + " - Quantity: " + qty + " | Value: Rs. " + (qty * price));
                }
            }
            System.out.println("Remaining Balance: Rs. " + balance);
        }

        void savePortfolioToFile() {
            try (PrintWriter writer = new PrintWriter(new FileWriter("portfolio.txt"))) {
                writer.println(username);
                writer.println(balance);
                for (Map.Entry<String, Integer> entry : portfolio.entrySet()) {
                    writer.println(entry.getKey() + "," + entry.getValue());
                }
                System.out.println("Portfolio saved to file.");
            } catch (IOException e) {
                System.out.println("Error saving portfolio: " + e.getMessage());
            }
        }

        void loadPortfolioFromFile() {
            File file = new File("portfolio.txt");
            if (!file.exists()) return;

            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String name = reader.readLine();
                double bal = Double.parseDouble(reader.readLine());
                if (name != null && name.equals(this.username)) {
                    this.balance = bal;
                    String line;
                    while ((line = reader.readLine()) != null) {
                        String[] parts = line.split(",");
                        if (parts.length == 2) {
                            portfolio.put(parts[0], Integer.parseInt(parts[1]));
                        }
                    }
                    System.out.println("Portfolio loaded from file.");
                }
            } catch (IOException e) {
                System.out.println("Error loading portfolio: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        List<Stock> stockList = new ArrayList<>();
        stockList.add(new Stock("Tata Motors", "TATAMOT", 420));
        stockList.add(new Stock("Infosys", "INFY", 1480));
        stockList.add(new Stock("Reliance", "RELI", 2560));

        System.out.print("Enter your name: ");
        String name = sc.nextLine();
        User user = new User(name, 10000);
        user.loadPortfolioFromFile();

        while (true) {
            System.out.println("\nAvailable Stocks:");
            for (int i = 0; i < stockList.size(); i++) {
                Stock s = stockList.get(i);
                System.out.println((i + 1) + ". " + s.name + " (" + s.symbol + ") - Rs. " + s.price);
            }

            System.out.println("\n1. Buy Stock");
            System.out.println("2. Sell Stock");
            System.out.println("3. View Portfolio");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            int choice = sc.nextInt();

            if (choice == 1) {
                System.out.print("Enter stock number: ");
                int index = sc.nextInt() - 1;
                System.out.print("Enter quantity: ");
                int qty = sc.nextInt();

                if (index >= 0 && index < stockList.size()) {
                    user.buyStock(stockList.get(index), qty);
                } else {
                    System.out.println("Invalid stock number.");
                }

            } else if (choice == 2) {
                System.out.print("Enter stock number to sell: ");
                int index = sc.nextInt() - 1;
                System.out.print("Enter quantity to sell: ");
                int qty = sc.nextInt();

                if (index >= 0 && index < stockList.size()) {
                    user.sellStock(stockList.get(index), qty);
                } else {
                    System.out.println("Invalid stock number.");
                }

            } else if (choice == 3) {
                user.showPortfolio(stockList);
            } else {
                user.savePortfolioToFile();
                System.out.println("Thank you for trading, " + user.username + "!");
                break;
            }
        }
    }
}
