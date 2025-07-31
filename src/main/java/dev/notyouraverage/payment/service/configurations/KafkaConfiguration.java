package dev.notyouraverage.payment.service.configurations;

import dev.notyouraverage.payment.service.configurations.properties.KafkaCustomProperties;
import dev.notyouraverage.payment.service.constants.KafkaConstants;
import dev.notyouraverage.payment.service.utils.KafkaConfigurationUtils;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import java.util.Map;

import io.confluent.kafka.serializers.subject.RecordNameStrategy;
import lombok.RequiredArgsConstructor;
import org.apache.avro.specific.SpecificRecord;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.micrometer.KafkaTemplateObservation;

@Configuration
@RequiredArgsConstructor
public class KafkaConfiguration {

    private final KafkaCustomProperties kafkaCustomProperties;

    @Bean(KafkaConstants.AVRO_PRODUCER_FACTORY)
    public ProducerFactory<String, SpecificRecord> avroProducerFactory() {
        Map<String, Object> props = KafkaConfigurationUtils.buildCommonProducerConfigs(kafkaCustomProperties);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class.getName());
        return new DefaultKafkaProducerFactory<>(props);
    }

    @Bean(KafkaConstants.AVRO_MULTI_SCHEMA_PRODUCER_FACTORY)
    public ProducerFactory<String, SpecificRecord> avroMultiSchemaProducerFactory() {
        Map<String, Object> props = KafkaConfigurationUtils.buildCommonProducerConfigs(kafkaCustomProperties);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class.getName());
        props.put("value.subject.name.strategy", RecordNameStrategy.class.getName());
        return new DefaultKafkaProducerFactory<>(props);
    }

    @Bean(KafkaConstants.AVRO_KAFKA_TEMPLATE)
    public KafkaTemplate<String, SpecificRecord> avroKafkaTemplate(
            @Qualifier(KafkaConstants.AVRO_PRODUCER_FACTORY) ProducerFactory<String, SpecificRecord> avroProducerFactory
    ) {
        KafkaTemplate<String, SpecificRecord> kafkaTemplate = new KafkaTemplate<>(avroProducerFactory);
        kafkaTemplate.setObservationEnabled(true);
        kafkaTemplate
                .setObservationConvention(new KafkaTemplateObservation.DefaultKafkaTemplateObservationConvention());
        return kafkaTemplate;
    }

    @Bean(KafkaConstants.AVRO_MULTI_SCHEMA_KAFKA_TEMPLATE)
    public KafkaTemplate<String, SpecificRecord> avroMultiSchemaKafkaTemplate(
            @Qualifier(KafkaConstants.AVRO_MULTI_SCHEMA_PRODUCER_FACTORY) ProducerFactory<String, SpecificRecord> avroProducerFactory
    ) {
        KafkaTemplate<String, SpecificRecord> kafkaTemplate = new KafkaTemplate<>(avroProducerFactory);
        kafkaTemplate.setObservationEnabled(true);
        kafkaTemplate
                .setObservationConvention(new KafkaTemplateObservation.DefaultKafkaTemplateObservationConvention());
        return kafkaTemplate;
    }

    @Bean(KafkaConstants.AVRO_CONSUMER_FACTORY)
    public ConsumerFactory<String, SpecificRecord> avroConsumerFactory() {
        Map<String, Object> props = KafkaConfigurationUtils.buildCommonConsumerConfigs(kafkaCustomProperties);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class.getName());
        props.put(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG, true);
        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean(KafkaConstants.AVRO_CONCURRENT_LISTENER_CONTAINER_FACTORY)
    public ConcurrentKafkaListenerContainerFactory<String, SpecificRecord> avroConcurrentKafkaListenerContainerFactory(
            @Qualifier(KafkaConstants.AVRO_CONSUMER_FACTORY) ConsumerFactory<String, SpecificRecord> consumerFactory
    ) {
        ConcurrentKafkaListenerContainerFactory<String, SpecificRecord> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.getContainerProperties().setObservationEnabled(true);
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }

}
