package service;

import org.mindrot.jbcrypt.BCrypt;
import model.User;
import DAO.UserDAO;

public class UserService {
    private UserDAO userDAO;

    public UserService() {
        this.userDAO = new UserDAO();
    }

    public User login(String username, String inputPassword) {
        User user = userDAO.findByUsername(username);
        if (user != null) {
            String hashedPassword = user.getPassword();
            if (BCrypt.checkpw(inputPassword, hashedPassword)) {
                return user; // login sukses
            }
        }
        return null; // login gagal
    }

    public void register(String username, String plainPassword, String role) {
        String hashedPassword = BCrypt.hashpw(plainPassword, BCrypt.gensalt());
        User user = new User(username, hashedPassword, role); // pakai konstruktor minimal
        userDAO.save(user);
    }
}