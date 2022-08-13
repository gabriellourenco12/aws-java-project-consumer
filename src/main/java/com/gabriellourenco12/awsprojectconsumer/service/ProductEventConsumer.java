package com.gabriellourenco12.awsprojectconsumer.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gabriellourenco12.awsprojectconsumer.model.Envelope;
import com.gabriellourenco12.awsprojectconsumer.model.ProductEvent;
import com.gabriellourenco12.awsprojectconsumer.model.ProductEventLog;
import com.gabriellourenco12.awsprojectconsumer.model.SnsMessage;
import com.gabriellourenco12.awsprojectconsumer.repository.ProductEventLogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.TextMessage;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;

@Service
public class ProductEventConsumer {

    private static final Logger log = LoggerFactory.getLogger(ProductEventConsumer.class);
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final ProductEventLogRepository productEventLogRepository;

    @Autowired
    public ProductEventConsumer(ProductEventLogRepository productEventLogRepository) {
        this.productEventLogRepository = productEventLogRepository;
    }

    @JmsListener(destination = "${aws.sqs.queue.product.events.name}")
    public void receiveProductEvent(TextMessage textMessage) throws IOException, JMSException {
        SnsMessage snsMessage = objectMapper.readValue(textMessage.getText(), SnsMessage.class);

        log.info("Message received - Id: {} - ", snsMessage.getMessageId());
        Envelope envelope = objectMapper.readValue(snsMessage.getMessage(), Envelope.class);
        ProductEvent productEvent = objectMapper.readValue(envelope.getData(), ProductEvent.class);
        log.info("Product event received - Event: {} - ProductId: {} - ", envelope.getEventType(), productEvent.getProductId());

        ProductEventLog productEventLog = buildProductEventLog(envelope, productEvent, snsMessage);
        productEventLogRepository.save(productEventLog);
    }

    private ProductEventLog buildProductEventLog(Envelope envelope, ProductEvent productEvent, SnsMessage snsMessage) {
        long timestamp = Instant.now().toEpochMilli();

        ProductEventLog productEventLog = new ProductEventLog();
        productEventLog.setPk(productEvent.getCode());
        productEventLog.setSk(envelope.getEventType() + "_" + timestamp);
        productEventLog.setEventType(envelope.getEventType());
        productEventLog.setProductId(productEvent.getProductId());
        productEventLog.setUsername(productEvent.getUsername());
        productEventLog.setTimestamp(timestamp);
        productEventLog.setTtl(Instant.now().plus(Duration.ofMinutes(10)).getEpochSecond());
        productEventLog.setMessageId(snsMessage.getMessageId());

        return productEventLog;
    }
}
