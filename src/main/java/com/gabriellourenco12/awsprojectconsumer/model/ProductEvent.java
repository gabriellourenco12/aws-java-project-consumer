package com.gabriellourenco12.awsprojectconsumer.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductEvent {
    private long productId;
    private String code;
    private String username;
}
