package view;

import model.User;
import model.CartDTO;
import model.CartItem;
import service.CartService;
import service.PaymentService;
import model.PaymentMethod;

import javax.swing.*;
import java.awt.*;
import java.util.logging.Logger;

public class CheckoutPanel extends javax.swing.JPanel {
    private static final Logger LOGGER = Logger.getLogger(CheckoutPanel.class.getName());
    private final JPanel parentPanel;
    private final CardLayout cardLayout;
    private final User user;
    private final CartService cartService;
    private CartDTO cartDTO;

    private JRadioButton rbEwallet;
    private JRadioButton rbRekening;
    private ButtonGroup paymentGroup;

    public CheckoutPanel(JPanel parentPanel, CardLayout cardLayout, User user) {
        this.parentPanel = parentPanel;
        this.cardLayout = cardLayout;
        this.user = user;
        this.cartService = new CartService();
        initComponents();
        addPaymentOptions(); // Tambahkan radio button setelah initComponents
        loadCheckoutInfo();
    }

    // Tambahkan radio button ke panel
    private void addPaymentOptions() {
        rbEwallet = new JRadioButton("E-Wallet");
        rbRekening = new JRadioButton("Rekening");
        paymentGroup = new ButtonGroup();
        paymentGroup.add(rbEwallet);
        paymentGroup.add(rbRekening);
        rbEwallet.setSelected(true);

        // Tambahkan ke jPanel1 (panel utama)
        jPanel1.add(rbEwallet);
        jPanel1.add(rbRekening);

        // Optional: update label saldo sesuai pilihan
        rbEwallet.addActionListener(e -> updateSaldoLabel());
        rbRekening.addActionListener(e -> updateSaldoLabel());
    }

    private void updateSaldoLabel() {
        if (rbEwallet.isSelected()) {
            lblSaldo.setText("Saldo E-Wallet : Rp " + String.format("%.0f", user.getEwalletBalance()));
        } else if (rbRekening.isSelected()) {
            lblSaldo.setText("Saldo Rekening : Rp " + String.format("%.0f", user.getRekeningBalance()));
        }
    }

    private void loadCheckoutInfo() {
        cartDTO = cartService.getCartForCustomer(user.getId());
        lblTotalHarga.setText("Total harga : Rp " + String.format("%.0f", cartDTO.getTotal()));
        lblJumlah.setText("Jumlah : " + cartDTO.getTotalItems());
        updateSaldoLabel();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btnKembali = new javax.swing.JButton();
        lblTotalHarga = new javax.swing.JLabel();
        lblJumlah = new javax.swing.JLabel();
        lblSaldo = new javax.swing.JLabel();
        btnBayar = new javax.swing.JButton();

        btnKembali.setText("Kembali");
        btnKembali.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKembaliActionPerformed(evt);
            }
        });

        lblTotalHarga.setText("Total harga :");

        lblJumlah.setText("Jumlah :");

        lblSaldo = new javax.swing.JLabel();
        lblSaldo.setText("Saldo :");

        btnBayar.setText("Bayar");
        btnBayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBayarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(19, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblSaldo)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                            .addComponent(btnKembali)
                            .addGap(202, 202, 202)
                            .addComponent(btnBayar)
                            .addGap(30, 30, 30))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lblTotalHarga)
                                .addComponent(lblJumlah))
                            .addContainerGap()))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(lblSaldo)
                .addGap(48, 48, 48)
                .addComponent(lblTotalHarga)
                .addGap(18, 18, 18)
                .addComponent(lblJumlah)
                .addGap(68, 68, 68)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnKembali)
                    .addComponent(btnBayar))
                .addContainerGap(64, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnKembaliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKembaliActionPerformed
        LOGGER.info("Kembali ke Halaman Dashboard Customer");
        cardLayout.show(parentPanel, "CustomerDashboard");
    }//GEN-LAST:event_btnKembaliActionPerformed

    private void btnBayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBayarActionPerformed
        double total = cartDTO.getTotal();
        PaymentMethod paymentMethod;

        if (rbEwallet.isSelected()) {
            if (user.getEwalletBalance() < total) {
                JOptionPane.showMessageDialog(this, "Saldo E-Wallet tidak cukup!");
                return;
            }
            paymentMethod = new PaymentMethod() {
                @Override
                public void pay(double amount) {
                    user.setEwalletBalance(user.getEwalletBalance() - amount);
                }
            };
        } else if (rbRekening.isSelected()) {
            if (user.getRekeningBalance() < total) {
                JOptionPane.showMessageDialog(this, "Saldo Rekening tidak cukup!");
                return;
            }
            paymentMethod = new PaymentMethod() {
                @Override
                public void pay(double amount) {
                    user.setRekeningBalance(user.getRekeningBalance() - amount);
                }
            };
        } else {
            JOptionPane.showMessageDialog(this, "Pilih metode pembayaran!");
            return;
        }

        PaymentService paymentService = new PaymentService();
        paymentService.processPayment(paymentMethod, total);

        cartService.clearCart(cartDTO.getCartId());

        JOptionPane.showMessageDialog(this, "Pembayaran berhasil!");
        loadCheckoutInfo();
    }//GEN-LAST:event_btnBayarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBayar;
    private javax.swing.JButton btnKembali;
    private javax.swing.JLabel lblJumlah;
    private javax.swing.JLabel lblSaldo;
    private javax.swing.JLabel lblTotalHarga;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}