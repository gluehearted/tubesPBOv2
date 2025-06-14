package util;

import exception.ValidationException;

public class ValidationUtil {
    public static void validateNotEmpty(String value, String fieldName) throws ValidationException {
        if (value == null || value.trim().isEmpty()) {
            throw new ValidationException(fieldName + " tidak boleh kosong");
        }
    }

    public static void validatePositive(double value, String fieldName) throws ValidationException {
        if (value <= 0) {
            throw new ValidationException(fieldName + " harus lebih besar dari 0");
        }
    }
}