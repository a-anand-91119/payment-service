package dev.notyouraverage.payment.service.enums;

import java.util.Random;

public enum PaymentGateway {
    STRIPE,
    PAYPAL,
    SQUARE,
    ADYEN;

    private static final Random RANDOM = new Random();

    private static final PaymentGateway[] VALUES = values();

    public static PaymentGateway getRandomPaymentGateway() {
        return VALUES[RANDOM.nextInt(VALUES.length)];
    }
}
