package model;

public class User {
    protected int id;
    protected String username;
    protected String password;
    protected String role;
    protected double ewalletBalance;
    protected double rekeningBalance;
    protected Cart<MenuItem> cart = new Cart<>();

    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public User(int id, String username, String password, String role, double ewalletBalance, double rekeningBalance) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.ewalletBalance = ewalletBalance;
        this.rekeningBalance = rekeningBalance;
    }

    // Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public double getEwalletBalance() { return ewalletBalance; }
    public void setEwalletBalance(double ewalletBalance) { this.ewalletBalance = ewalletBalance; }

    public double getRekeningBalance() { return rekeningBalance; }
    public void setRekeningBalance(double rekeningBalance) { this.rekeningBalance = rekeningBalance; }

    public Cart<MenuItem> getCart() { return cart; }

    public void topUpEwallet(double amount) {
        if (amount > 0) {
            this.ewalletBalance += amount;
        }
    }

    public void showMenu() {
        System.out.println("User menu");
    }

    public boolean isCustomer() {
        return "customer".equalsIgnoreCase(role);
    }
}
