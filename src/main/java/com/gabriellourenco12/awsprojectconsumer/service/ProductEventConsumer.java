package com.gabriellourenco12.awsprojectconsumer.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gabriellourenco12.awsprojectconsumer.model.Envelope;
import com.gabriellourenco12.awsprojectconsumer.model.ProductEvent;
import com.gabriellourenco12.awsprojectconsumer.model.SnsMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.TextMessage;
import java.io.IOException;

@Service
@Slf4j
public class ProductEventConsumer {

    private final ObjectMapper objectMapper;

    @Autowired
    public ProductEventConsumer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @JmsListener(destination = "${aws.sqs.queue.product.events.name}")
    public void receiveProductEvent(TextMessage textMessage) throws IOException, JMSException {
        log.info("Received message: {}", textMessage.getText());

        SnsMessage snsMessage = objectMapper.readValue(textMessage.getText(), SnsMessage.class);

        Envelope envelope = objectMapper.readValue(snsMessage.getMessage(), Envelope.class);

        ProductEvent productEvent = objectMapper.readValue(envelope.getData(), ProductEvent.class);

        log.info("Product event received - Event: {} - ProductId: {} - ", envelope.getEventType(), productEvent.getProductId());
    }
}
