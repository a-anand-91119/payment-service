package dev.notyouraverage.payment.service.helpers;

import dev.notyouraverage.base.annotations.ServiceHelper;
import dev.notyouraverage.commons.utils.CompletableFutureUtils;
import dev.notyouraverage.messages.avro.OrderCreatedEvent;
import dev.notyouraverage.messages.avro.PaymentFailedEvent;
import dev.notyouraverage.messages.avro.PaymentProcessedEvent;
import dev.notyouraverage.payment.service.constants.KafkaConstants;
import dev.notyouraverage.payment.service.enums.PaymentError;
import dev.notyouraverage.payment.service.enums.PaymentGateway;
import dev.notyouraverage.payment.service.enums.PaymentMethod;

import java.time.Instant;
import java.util.Random;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecord;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@ServiceHelper
@RequiredArgsConstructor
public class OrderEventHelper {

    private final Random random = new Random();

    @Qualifier(KafkaConstants.AVRO_KAFKA_TEMPLATE)
    private final KafkaTemplate<String, SpecificRecord> avroKafkaTemplate;

    @Qualifier(KafkaConstants.AVRO_MULTI_SCHEMA_KAFKA_TEMPLATE)
    private final KafkaTemplate<String, SpecificRecord> avroMultiSchemaKafkaTemplate;

    @Value("${app.kafka.topics.payment_processed_events}")
    private String paymentProcessedEventsTopic;

    @Value("${app.kafka.topics.payment_failed_events}")
    private String paymentFailedEventsTopic;

    @Value("${app.kafka.topics.payment_events}")
    private String paymentEventsTopic;

    @Transactional
    public void processOrderCreatedEvent(OrderCreatedEvent orderCreatedEvent) {
        log.info("Processing OrderCreatedEvent for orderId: {}", orderCreatedEvent.getOrderId());

        try {
            boolean isPaymentSuccessful = random.nextDouble() < 0.7;

            if (isPaymentSuccessful) {
                PaymentProcessedEvent paymentEvent = createPaymentProcessedEvent(orderCreatedEvent);
                CompletableFutureUtils.unchekedGet(
                        avroKafkaTemplate.send(paymentProcessedEventsTopic, orderCreatedEvent.getOrderId(), paymentEvent)
                );
                CompletableFutureUtils.unchekedGet(
                        avroMultiSchemaKafkaTemplate.send(paymentEventsTopic, orderCreatedEvent.getOrderId(), paymentEvent)
                );
                log.info("Generated PaymentProcessedEvent for orderId: {}", orderCreatedEvent.getOrderId());
            } else {
                PaymentFailedEvent paymentEvent = createPaymentFailedEvent(orderCreatedEvent);
                CompletableFutureUtils.unchekedGet(
                        avroKafkaTemplate.send(paymentFailedEventsTopic, orderCreatedEvent.getOrderId(), paymentEvent)
                );
                CompletableFutureUtils.unchekedGet(
                        avroMultiSchemaKafkaTemplate.send(paymentEventsTopic, orderCreatedEvent.getOrderId(), paymentEvent)
                );
                log.info("Generated PaymentFailedEvent for orderId: {}", orderCreatedEvent.getOrderId());
            }

        } catch (Exception e) {
            log.error(
                    "Failed to process OrderCreatedEvent for orderId: {}",
                    orderCreatedEvent.getOrderId(),
                    e
            );
            throw e;
        }
    }

    private PaymentProcessedEvent createPaymentProcessedEvent(OrderCreatedEvent orderCreatedEvent) {
        Instant now = Instant.now();
        String paymentId = UUID.randomUUID().toString();

        return PaymentProcessedEvent.newBuilder()
                .setPaymentId(paymentId)
                .setOrderId(orderCreatedEvent.getOrderId())
                .setCustomerId(orderCreatedEvent.getCustomerId())
                .setAmount(orderCreatedEvent.getTotalAmount())
                .setCurrency(orderCreatedEvent.getCurrency())
                .setPaymentMethod(PaymentMethod.getRandomPaymentMethod().name())
                .setPaymentStatus("PROCESSED")
                .setTransactionId("TXN_" + UUID.randomUUID().toString().substring(0, 8))
                .setPaymentGateway(PaymentGateway.getRandomPaymentGateway().name())
                .setProcessedAt(now)
                .setEventTimestamp(now)
                .setEventSource("payment-service")
                .setEventVersion(1)
                .build();
    }

    private PaymentFailedEvent createPaymentFailedEvent(OrderCreatedEvent orderCreatedEvent) {
        Instant now = Instant.now();
        String paymentId = UUID.randomUUID().toString();
        PaymentError paymentError = PaymentError.getRandomPaymentError();
        boolean isRetryable = random.nextBoolean();

        return PaymentFailedEvent.newBuilder()
                .setPaymentId(paymentId)
                .setOrderId(orderCreatedEvent.getOrderId())
                .setCustomerId(orderCreatedEvent.getCustomerId())
                .setAmount(orderCreatedEvent.getTotalAmount())
                .setCurrency(orderCreatedEvent.getCurrency())
                .setPaymentMethod(PaymentMethod.getRandomPaymentMethod().name())
                .setPaymentStatus("FAILED")
                .setErrorCode(paymentError.getCode())
                .setErrorMessage(paymentError.getMessage())
                .setTransactionId("TXN_" + UUID.randomUUID().toString().substring(0, 8))
                .setPaymentGateway(PaymentGateway.getRandomPaymentGateway().name())
                .setFailedAt(now)
                .setRetryable(isRetryable)
                .setEventTimestamp(now)
                .setEventSource("payment-service")
                .setEventVersion(1)
                .build();
    }
}
