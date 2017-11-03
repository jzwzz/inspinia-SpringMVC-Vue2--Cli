package com.cmb.ccd.mr.rtm.guardian.kafka.service;

import lombok.Data;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@ConfigurationProperties(prefix = "spring.kafka.producer")
@Data
@Component(value = "customKafkaProducerFactory")
public class KafkaProducerFactory {

    private String bootstrapServers;
    private String keySerializer;
    private String valueSerializer;
    private String compressionType;
    private int retries;
    private long bufferMemory;
    private int batchSize;

    private KafkaProducer<String, String> kafkaProducer;

    @PostConstruct
    public void init() {
        kafkaProducer = getInstance();
    }

    public KafkaProducer<String, String> getInstance() {
        if (kafkaProducer == null) {
            synchronized (this) {
                if (kafkaProducer == null) {
                    Map<String, Object> map = new HashMap<>();
                    map.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, keySerializer);
                    map.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, valueSerializer);
                    map.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, compressionType);
                    map.put(ProducerConfig.RETRIES_CONFIG, retries);
                    map.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
                    map.put(ProducerConfig.BATCH_SIZE_CONFIG, batchSize);
                    map.put(ProducerConfig.BUFFER_MEMORY_CONFIG, bufferMemory);
                    kafkaProducer = new KafkaProducer<>(map);
                }
            }
        }
        return kafkaProducer;
    }
}
