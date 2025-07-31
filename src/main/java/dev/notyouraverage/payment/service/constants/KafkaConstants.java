package dev.notyouraverage.payment.service.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public class KafkaConstants {

    public static final String TRUSTED_PACKAGES = "dev.notyouraverage.messages.*";

    public static final String ORDER_CREATED_LISTENER_ID = "OrderCreatedListenerId";

    public static final String ORDER_CREATED_LISTENER_GROUP = "OrderCreatedListenerGroup";

    public static final String KAFKA_CUSTOM_PROPERTIES = "kafkaAvroFormatProperties";

    public static final String AVRO_PRODUCER_FACTORY = "avroProducerFactory";
    public static final String AVRO_MULTI_SCHEMA_PRODUCER_FACTORY = "avroMultiSchemaProducerFactory";

    public static final String AVRO_KAFKA_TEMPLATE = "avroKafkaTemplate";
    public static final String AVRO_MULTI_SCHEMA_KAFKA_TEMPLATE = "avroMultiSchemaKafkaTemplate";

    public static final String AVRO_CONSUMER_FACTORY = "avroConsumerFactory";

    public static final String AVRO_CONCURRENT_LISTENER_CONTAINER_FACTORY = "avroConcurrentKafkaListenerContainerFactory";

    public static final String USER_AVRO_LISTENER_ID = "userAvroListenerId";

    public static final String USER_AVRO_LISTENER_GROUP = "userAvroListenerGroup";

    public static final String ORDER_AVRO_LISTENER_PAYMENT_ID = "orderAvroListenerPaymentId";

    public static final String ORDER_AVRO_LISTENER_PAYMENT_GROUP = "orderAvroListenerPaymentGroup";
}
