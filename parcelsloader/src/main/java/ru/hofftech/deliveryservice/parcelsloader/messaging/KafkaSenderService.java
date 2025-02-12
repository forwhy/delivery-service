package ru.hofftech.deliveryservice.parcelsloader.messaging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import ru.hofftech.deliveryservice.parcelsloader.model.dto.OutboxDto;

@Slf4j
@RequiredArgsConstructor
public class KafkaSenderService {

    private final String topic;
    private final KafkaTemplate<String, OutboxDto> kafkaTemplate;

    /**
     * Метод для отправки данных в Kafka
     *
     * @param outboxDto Сообщение
     */
    public void sendMessage(OutboxDto outboxDto) {
        send(topic, outboxDto);
    }

    private void send(String topic, OutboxDto message) {
        try {
            kafkaTemplate.send(topic, message);
        } catch (Exception e) {
            log.error("Ошибка при попытке отправить сообщение в биллинг {}. Заказ не был сохранён.", message, e);
            throw e;
        }
    }
}
