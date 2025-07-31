package dev.notyouraverage.payment.service.enums;

import java.util.Random;

public enum PaymentError {
    INSUFFICIENT_FUNDS("INSUFFICIENT_FUNDS", "Insufficient funds in account"),
    CARD_DECLINED("CARD_DECLINED", "Card declined by issuer"),
    EXPIRED_CARD("EXPIRED_CARD", "Card has expired"),
    INVALID_CVV("INVALID_CVV", "Invalid CVV code"),
    PROCESSING_ERROR("PROCESSING_ERROR", "Payment processing error occurred");

    private static final Random RANDOM = new Random();

    private static final PaymentError[] VALUES = values();

    private final String code;

    private final String message;

    PaymentError(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public static PaymentError getRandomPaymentError() {
        return VALUES[RANDOM.nextInt(VALUES.length)];
    }
}
