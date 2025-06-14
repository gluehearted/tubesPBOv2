package util;

import exception.ValidationException;

public class Validator {
    public static void validateUsername(String username) throws ValidationException {
        if (username == null || username.trim().isEmpty()) {
            throw new ValidationException("Username tidak boleh kosong");
        }
    }

    public static void validatePassword(String password) throws ValidationException {
        if (password == null || password.length() < 6) {
            throw new ValidationException("Password minimal 6 karakter");
        }
    }

    public static void validateAmount(double amount) throws ValidationException {
        if (amount <= 0) {
            throw new ValidationException("Jumlah harus lebih dari 0");
        }
    }
}
