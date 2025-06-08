package service;

import model.*;

import java.util.List;

public class OrderService {

    public double calculateTotal(List<MenuItem> items) {
        return items.stream().mapToDouble(MenuItem::getPrice).sum();
    }

    public Order checkout(Customer customer) throws Exception {
        Cart<MenuItem> cart = customer.getCart();
        if (cart.isEmpty()) {
            throw new Exception("Keranjang kosong, tidak bisa checkout!");
        }

        double total = calculateTotal(cart.getItems());

        if (customer.getEwalletBalance() >= total) {
            customer.setEwalletBalance(customer.getEwalletBalance() - total);
        } else if (customer.getRekeningBalance() >= total) {
            customer.setRekeningBalance(customer.getRekeningBalance() - total);
        } else {
            throw new Exception("Saldo tidak cukup di kedua metode pembayaran!");
        }

        // Buat order baru
        Order order = new Order(customer, cart.getItems(), total);

        // Kosongkan cart setelah checkout
        cart.clear();

        // Cetak nota
        printReceipt(order);

        return order;
    }

    public void printReceipt(Order order) {
        System.out.println("===== NOTA PEMESANAN =====");
        System.out.println("Customer: " + order.getCustomer().getUsername());
        System.out.println("Items:");
        order.getItems().forEach(item ->
            System.out.println(" - " + item.getName() + " : Rp" + item.getPrice())
        );
        System.out.println("Total: Rp" + order.getTotalAmount());
        System.out.println("==========================");
    }
}