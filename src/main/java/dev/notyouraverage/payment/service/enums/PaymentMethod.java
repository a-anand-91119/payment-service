package dev.notyouraverage.payment.service.enums;

import java.util.Random;

public enum PaymentMethod {
    CARD,
    BANK_TRANSFER,
    DIGITAL_WALLET,
    PAYPAL;

    private static final Random RANDOM = new Random();

    private static final PaymentMethod[] VALUES = values();

    public static PaymentMethod getRandomPaymentMethod() {
        return VALUES[RANDOM.nextInt(VALUES.length)];
    }
}
