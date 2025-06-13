/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import view.MainFrame;
import service.AuthService;
import model.User;
import exception.AuthenticationException;
import exception.DatabaseException;
import java.util.logging.Logger;

/**
 * Controller untuk menangani logika login.
 */
public class LoginController {
    private static final Logger LOGGER = Logger.getLogger(LoginController.class.getName());
    private final AuthService authService;
    private final MainFrame mainFrame;

    public LoginController(AuthService authService, MainFrame mainFrame) {
        this.authService = authService;
        this.mainFrame = mainFrame;
    }

    public void login(String username, String password) throws AuthenticationException, DatabaseException {
        User user = authService.login(username, password);
        LOGGER.info("Login successful for user: " + username + " with role: " + user.getRole());
        mainFrame.showDashboard(user);
    }
}
