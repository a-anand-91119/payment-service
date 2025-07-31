package dev.notyouraverage.payment.service.configurations.properties;

import dev.notyouraverage.payment.service.constants.KafkaConstants;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Data
@Primary
@Component
@ConfigurationProperties("app.kafka")
@EqualsAndHashCode(callSuper = true)
@Qualifier(KafkaConstants.KAFKA_CUSTOM_PROPERTIES) public class KafkaCustomProperties extends KafkaProperties {
}
