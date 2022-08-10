package com.gabriellourenco12.awsprojectconsumer.model;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.gabriellourenco12.awsprojectconsumer.enums.EventType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@DynamoDBTable(tableName = "product-events")
@NoArgsConstructor
public class ProductEventLog {

    @Id
    private ProductEventKey productEventKey;

    @DynamoDBTypeConvertedEnum
    @DynamoDBAttribute(attributeName = "eventType")
    private EventType eventType;

    @DynamoDBAttribute(attributeName = "productId")
    private long productId;

    @DynamoDBAttribute(attributeName = "username")
    private String username;

    @DynamoDBAttribute(attributeName = "timestamp")
    private long timestamp;

    @DynamoDBAttribute(attributeName = "ttl")
    private long ttl;

    @DynamoDBHashKey(attributeName = "pk")
    public String getPk() {
        return this.productEventKey != null ? this.productEventKey.getPk() : null;
    }

    public void setPk(String pk) {
        if (this.productEventKey == null) {
            this.productEventKey = new ProductEventKey();
        }
        this.productEventKey.setPk(pk);
    }

    @DynamoDBRangeKey(attributeName = "sk")
    public String getSk() {
        return this.productEventKey != null ? this.productEventKey.getSk() : null;
    }

    public void setSk(String sk) {
        if (this.productEventKey == null) {
            this.productEventKey = new ProductEventKey();
        }
        this.productEventKey.setSk(sk);
    }
}
