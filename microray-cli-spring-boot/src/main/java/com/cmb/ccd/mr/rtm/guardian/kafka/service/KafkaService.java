package com.cmb.ccd.mr.rtm.guardian.kafka.service;

import com.cmb.ccd.mr.rtm.guardian.archive.entity.ArchiveDetail;
import com.cmb.ccd.mr.rtm.guardian.archive.vo.SendMessageResultVO;
import com.cmb.ccd.mr.rtm.guardian.kafka.message.JsonMessage;
import com.cmb.ccd.mr.rtm.guardian.kafka.message.MessageBuilder;
import com.google.gson.Gson;
import lombok.extern.log4j.Log4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.clients.producer.internals.FutureRecordMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Component
@Log4j
public class KafkaService {

    @Autowired
    private KafkaProducerFactory kafkaProducerFactory;
    @Autowired
    private MessageBuilder messageBuilder;
    @Value("${guardian.produce.kafka.topic}")
    private String topic;

    @Async
    public Future<SendMessageResultVO> sendRecord(List<ArchiveDetail> archiveDetailList) {
        Gson gson = new Gson();
        long start = System.currentTimeMillis();
        List<ArchiveDetail> failedArchiveDetailList = new ArrayList<>();
        Future[] futures = new FutureRecordMetadata[archiveDetailList.size()];
        for (int i = 0; i < archiveDetailList.size(); i++) {
            ArchiveDetail archiveDetail = archiveDetailList.get(i);
            try {
                JsonMessage jsonMessage = messageBuilder.buildJsonMessage(archiveDetail);
                ProducerRecord<String, String> producerRecord = new ProducerRecord<>(topic, gson.toJson(jsonMessage));
                futures[i] = kafkaProducerFactory.getInstance().send(producerRecord);
            } catch (ParseException e) {
                log.error(e);
                log.error("archiveDetail fail: " + archiveDetail);
                failedArchiveDetailList.add(archiveDetail);
            }
        }
        kafkaProducerFactory.getInstance().flush();

        long totalRecords = archiveDetailList.size();
        long failedRecords = failedArchiveDetailList.size();
        long successRecords = 0;

        for (Future future : futures) {
            if (future == null)
                continue;
            try {
                RecordMetadata recordMetadata = (RecordMetadata) future.get();
                successRecords++;
                log.debug(recordMetadata);
            } catch (InterruptedException | ExecutionException e) {
                log.error(e);
                log.error("send to kafka error");
                throw new RuntimeException(e);
            }
        }
        long end = System.currentTimeMillis();
        log.info("cost " + (end - start) + "ms to send " + totalRecords + " records");
        log.info("build json message fail " + failedRecords);
        return new AsyncResult<>(new SendMessageResultVO(failedRecords, successRecords, totalRecords));
    }

}
