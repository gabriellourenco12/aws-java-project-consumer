package com.gabriellourenco12.awsprojectconsumer.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductEventKey {

    @DynamoDBHashKey(attributeName = "pk")
    private  String pk;

    @DynamoDBRangeKey(attributeName = "sk")
    private String sk;
}
