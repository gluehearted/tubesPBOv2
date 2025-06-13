package view;

import java.awt.CardLayout;
import javax.swing.JPanel;
import db.DatabaseConnection;
import exception.DatabaseException;
import javax.swing.JOptionPane;
import java.util.logging.Logger;
import model.User;
import view.CustomerDashboard;
import view.AdminDashboard;
import view.AddRestaurantMenu;
import view.EditUserPanel;

/**
 * MainFrame: JFrame utama yang menampung semua panel dengan CardLayout
 * Author: Gabriel Prakosa A
 */
public class MainFrame extends javax.swing.JFrame {
    private static final Logger LOGGER = Logger.getLogger(MainFrame.class.getName());
    private CardLayout cardLayout;
    private JPanel cardpanel;

    public MainFrame() {
        initComponents();
        cardpanel = jPanel2;
        cardLayout = (CardLayout) cardpanel.getLayout();
        initializeApplication();
    }

    private void initializeApplication() {
        try {
            DatabaseConnection.getConnection();
            LOGGER.info("Database connection initialized.");

            cardpanel.add(new LoginPanel(cardpanel, cardLayout, this), "Login");
            cardpanel.add(new RegisterPanel(cardpanel, cardLayout), "Register");
            LOGGER.info("Login dan Register panel berhasil ditambahkan.");

            cardLayout.show(cardpanel, "Login");

        } catch (DatabaseException e) {
            LOGGER.severe("Failed to connect to database: " + e.getMessage());
            JOptionPane.showMessageDialog(this, "Failed to connect to database: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    public void showLoginPanel() {
        cardLayout.show(cardpanel, "Login");
        LOGGER.info("Switched to Login panel.");
    }

    public void showDashboard(User user) {
        String role = user.getRole();
        LOGGER.info("Showing dashboard for role: " + role);

        cardpanel.removeAll();
        cardpanel.add(new LoginPanel(cardpanel, cardLayout, this), "Login");
        cardpanel.add(new RegisterPanel(cardpanel, cardLayout), "Register");

        if ("admin".equalsIgnoreCase(role)) {
            cardpanel.add(new AdminDashboard(this, cardLayout), "AdminDashboard");
            cardLayout.show(cardpanel, "AdminDashboard");
            LOGGER.info("Switched to AdminDashboard.");
        } else if ("customer".equalsIgnoreCase(role)) {
            cardpanel.add(new CustomerDashboard(this, user), "CustomerDashboard");
            cardLayout.show(cardpanel, "CustomerDashboard");
            LOGGER.info("Switched to CustomerDashboard.");
        } else {
            LOGGER.severe("Unknown role: " + role);
            JOptionPane.showMessageDialog(this, "Role tidak dikenali: " + role, "Error", JOptionPane.ERROR_MESSAGE);
        }

        cardpanel.revalidate();
        cardpanel.repaint();
    }

    public void showRestaurantMenuPanel() {
        cardpanel.removeAll();
        cardpanel.add(new LoginPanel(cardpanel, cardLayout, this), "Login");
        cardpanel.add(new RegisterPanel(cardpanel, cardLayout), "Register");
        cardpanel.add(new AdminDashboard(this, cardLayout), "AdminDashboard");
        cardpanel.add(new AddRestaurantMenu(cardpanel, cardLayout), "AddRestaurantMenu");
        cardLayout.show(cardpanel, "AddRestaurantMenu");
        cardpanel.revalidate();
        cardpanel.repaint();
        LOGGER.info("Switched to AddRestaurantMenu panel.");
    }

    public void showEditUserPanel() {
        cardpanel.removeAll();
        cardpanel.add(new LoginPanel(cardpanel, cardLayout, this), "Login");
        cardpanel.add(new RegisterPanel(cardpanel, cardLayout), "Register");
        cardpanel.add(new AdminDashboard(this, cardLayout), "AdminDashboard");
        cardpanel.add(new EditUserPanel(cardpanel, cardLayout), "EditUserPanel");
        cardLayout.show(cardpanel, "EditUserPanel");
        cardpanel.revalidate();
        cardpanel.repaint();
        LOGGER.info("Switched to EditUserPanel.");
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel(); // Ini adalah cardpanel
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Aplikasi Pemesanan Makanan", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));
        jPanel1.setPreferredSize(new java.awt.Dimension(370, 330));

        jPanel2.setLayout(new java.awt.CardLayout()); // ✅ Penting

        jLabel1.setText("Selamat datang di Aplikasi Pemesanan Makanan");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(57, 57, 57)
                    .addComponent(jLabel1)
                    .addContainerGap(48, Short.MAX_VALUE))
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLabel1)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            LOGGER.severe("Look and Feel Error: " + ex.getMessage());
        }

        java.awt.EventQueue.invokeLater(() -> new MainFrame().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    // End of variables declaration//GEN-END:variables
}
