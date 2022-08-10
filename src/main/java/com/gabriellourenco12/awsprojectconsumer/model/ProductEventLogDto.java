package com.gabriellourenco12.awsprojectconsumer.model;

import com.gabriellourenco12.awsprojectconsumer.enums.EventType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductEventLogDto {

    private final String code;
    private final EventType eventType;
    private final long productId;
    private final String username;
    private final long timestamp;

    public ProductEventLogDto(ProductEventLog productEventLog) {
        this.code = productEventLog.getPk();
        this.eventType = productEventLog.getEventType();
        this.productId = productEventLog.getProductId();
        this.username = productEventLog.getUsername();
        this.timestamp = productEventLog.getTimestamp();
    }
}
