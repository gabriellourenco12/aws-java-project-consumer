package com.gabriellourenco12.awsprojectconsumer.model;

import com.gabriellourenco12.awsprojectconsumer.enums.EventType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Envelope {
    private EventType eventType;
    private String data;
}
