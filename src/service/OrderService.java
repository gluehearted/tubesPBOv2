package service;

import model.*;
import DAO.OrderDAO;
import exception.DatabaseException;
import java.sql.Connection;
import java.util.List;

public class OrderService {
    private OrderDAO orderDAO;
    private PaymentService paymentService;

    public OrderService(Connection conn) {
        this.orderDAO = new OrderDAO(conn);
        this.paymentService = new PaymentService();
    }

    public double calculateTotal(List<MenuItem> items) {
        return items.stream().mapToDouble(item -> item.getPrice() * item.getQuantity()).sum();
    }

    public Order checkout(User user, PaymentMethod paymentMethod) throws Exception {
        if (!user.isCustomer()) {
            throw new Exception("Hanya pelanggan yang dapat melakukan checkout");
        }
        Cart<MenuItem> cart = user.getCart();
        if (cart.isEmpty()) {
            throw new Exception("Keranjang kosong, tidak bisa checkout!");
        }

        double total = calculateTotal(cart.getItems());
        String paymentType = paymentMethod instanceof EWalletPayment ? "ewallet" : "debit";
        if (paymentType.equals("ewallet") && user.getEwalletBalance() >= total) {
            user.setEwalletBalance(user.getEwalletBalance() - total);
            paymentService.processPayment(paymentMethod, total);
        } else if (paymentType.equals("debit") && user.getRekeningBalance() >= total) {
            user.setRekeningBalance(user.getRekeningBalance() - total);
            paymentService.processPayment(paymentMethod, total);
        } else {
            throw new Exception("Saldo tidak cukup di metode pembayaran yang dipilih!");
        }

        Order order = new Order(user, cart.getItems(), total);
        try {
            orderDAO.insert(order);
        } catch (DatabaseException e) {
            throw new Exception("Gagal menyimpan order ke database", e);
        }

        cart.clear();
        printReceipt(order);
        return order;
    }

    public void printReceipt(Order order) {
        System.out.println("===== NOTA PEMESANAN =====");
        System.out.println("Customer: " + order.getUser().getUsername());
        System.out.println("Items:");
        order.getItems().forEach(item ->
            System.out.println(" - " + item.getName() + " (x" + item.getQuantity() + ") : Rp" + (item.getPrice() * item.getQuantity()))
        );
        System.out.println("Total: Rp" + order.getTotalAmount());
        System.out.println("==========================");
    }
}