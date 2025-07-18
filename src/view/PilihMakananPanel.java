/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view;
import DAO.MenuItemDAO;
import DAO.MenuItemDAOImpl;
import DAO.RestaurantDAO;
import DAO.RestaurantDAOImpl;
import DAO.CartDAO;
import DAO.CartDAOImpl;
import model.MenuItem;
import model.Restaurant;
import db.DatabaseConnection;
import model.User;
import service.CartService;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.ArrayList;
import java.util.logging.Logger;
import java.awt.CardLayout;

/**
 *
 * @author Gabriel Prakosa A
 */

 public class PilihMakananPanel extends javax.swing.JPanel {
    private static final Logger LOGGER = Logger.getLogger(PilihMakananPanel.class.getName());
    private List<Restaurant> restaurantList = new ArrayList<>();
    private List<MenuItem> currentMenuList = new ArrayList<>();
    private RestaurantDAO restaurantDAO;
    private MenuItemDAO menuItemDAO;
    private CartDAO cartDAO;
    private CardLayout cardLayout;
    private JPanel parentPanel;
    private final User user;
    private CartService cartService; 

    public PilihMakananPanel(JPanel parentPanel, CardLayout cardLayout, User user) {
        this.parentPanel = parentPanel;
        this.cardLayout = cardLayout;
        this.user = user;
        try {
            this.restaurantDAO = new RestaurantDAOImpl(DatabaseConnection.getConnection());
            this.menuItemDAO = new MenuItemDAOImpl(DatabaseConnection.getConnection());
            this.cartDAO = new CartDAOImpl();
            this.cartService = new CartService(); // Inisialisasi CartService
            initComponents();
            loadRestaurants();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void loadRestaurants() {
        try {
            restaurantList = restaurantDAO.getAllRestaurants();
            btnPilihRestaurant.removeAllItems();
            for (Restaurant r : restaurantList) {
                btnPilihRestaurant.addItem(r.getName());
            }
            if (!restaurantList.isEmpty()) {
                btnPilihRestaurant.setSelectedIndex(0);
                loadMenuForSelectedRestaurant();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Gagal memuat restoran: " + ex.getMessage());
        }
    }

    private void loadMenuForSelectedRestaurant() {
        int idx = btnPilihRestaurant.getSelectedIndex();
        if (idx < 0 || restaurantList == null || restaurantList.isEmpty()) return;
        Restaurant selected = restaurantList.get(idx);
        try {
            currentMenuList = menuItemDAO.getMenuItemsByRestaurant(selected.getId());
            DefaultTableModel model = (DefaultTableModel) TableMenu.getModel();
            model.setRowCount(0);
            for (MenuItem m : currentMenuList) {
                model.addRow(new Object[]{m.getName(), m.getPrice()});
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Gagal memuat menu: " + ex.getMessage());
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnPilihRestaurant = new JComboBox<>();
        TableMenu = new JTable();
        btnTambahkeKeranjang = new javax.swing.JButton();
        btnKembali = new javax.swing.JButton();

        btnPilihRestaurant.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        TableMenu.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));

        btnTambahkeKeranjang.setText("Tambah ke Keranjang");
        btnTambahkeKeranjang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahkeKeranjangActionPerformed(evt);
            }
        });

        btnKembali.setText("Kembali");
        btnKembali.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKembaliActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(71, 71, 71)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnKembali)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 154, Short.MAX_VALUE)
                        .addComponent(btnTambahkeKeranjang))
                    .addComponent(TableMenu, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(btnPilihRestaurant, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(69, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(btnPilihRestaurant, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(TableMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTambahkeKeranjang)
                    .addComponent(btnKembali))
                .addContainerGap(24, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnKembaliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKembaliActionPerformed
        LOGGER.info("Kembali ke Halaman Dashboard Customer");
        cardLayout.show(parentPanel, "CustomerDashboard");
    }//GEN-LAST:event_btnKembaliActionPerformed

    private void btnTambahkeKeranjangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahkeKeranjangActionPerformed
        int selectedRow = TableMenu.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih menu terlebih dahulu!");
            return;
        }
        if (currentMenuList == null || selectedRow >= currentMenuList.size()) {
            JOptionPane.showMessageDialog(this, "Data menu tidak valid!");
            return;
        }
        MenuItem selectedMenu = currentMenuList.get(selectedRow);

        try {
            boolean success = cartService.addItemToCart(user.getId(), selectedMenu.getId(), 1);
            if (success) {
                JOptionPane.showMessageDialog(this, "Menu '" + selectedMenu.getName() + "' berhasil ditambahkan ke keranjang!");
                // Update cart badge in CustomerDashboard
                for (java.awt.Component comp : parentPanel.getComponents()) {
                    if (comp instanceof CustomerDashboard) {
                        ((CustomerDashboard) comp).updateCartBadge();
                        break;
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Gagal menambah ke keranjang!");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Gagal menambah ke keranjang: " + ex.getMessage());
        }
    }//GEN-LAST:event_btnTambahkeKeranjangActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnKembali;
    private javax.swing.JButton btnTambahkeKeranjang;
    private javax.swing.JComboBox<String> btnPilihRestaurant;
    private javax.swing.JTable TableMenu;
    // End of variables declaration//GEN-END:variables
}
