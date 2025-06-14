package model;

public class MenuItem {
    private int id;
    private String name;
    private double price;
    private int quantity; // digunakan dalam keranjang
    private int restaurantId;
    private Restaurant restaurant;

    // Konstruktor untuk menu dari restoran (tanpa quantity)
    public MenuItem(int id, String name, double price, int restaurantId, Restaurant restaurant) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.restaurantId = restaurantId;
        this.restaurant = restaurant;
        this.quantity = 1; // default 1 jika ditambahkan ke cart
    }

    // Getter dan Setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public int getRestaurantId() { return restaurantId; }
    public void setRestaurantId(int restaurantId) { this.restaurantId = restaurantId; }

    public void incrementQuantity() {
        this.quantity++;
    }

    public Restaurant getRestaurant() { return restaurant; }
    public void setRestaurant(Restaurant restaurant) { this.restaurant = restaurant; }

    @Override
    public String toString() {
        return name + " - Rp" + price + " (" + restaurant.getName() + ")";
    }
}
