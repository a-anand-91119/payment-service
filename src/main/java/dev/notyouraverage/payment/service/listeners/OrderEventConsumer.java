package dev.notyouraverage.payment.service.listeners;

import dev.notyouraverage.messages.avro.OrderCreatedEvent;
import dev.notyouraverage.payment.service.constants.KafkaConstants;
import dev.notyouraverage.payment.service.helpers.OrderEventHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderEventConsumer {

    private final OrderEventHelper orderEventHelper;

    @KafkaListener(id = KafkaConstants.ORDER_AVRO_LISTENER_PAYMENT_ID, groupId = KafkaConstants.ORDER_AVRO_LISTENER_PAYMENT_GROUP, topics = "${app.kafka.topics.order_avro_events}", containerFactory = KafkaConstants.AVRO_CONCURRENT_LISTENER_CONTAINER_FACTORY)
    public void handleOrderCreatedEvent(OrderCreatedEvent orderCreatedEvent) {
        log.info("Received OrderCreatedEvent for orderId: {}", orderCreatedEvent.getOrderId());
        orderEventHelper.processOrderCreatedEvent(orderCreatedEvent);
    }

}
