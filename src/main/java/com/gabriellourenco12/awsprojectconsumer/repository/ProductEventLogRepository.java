package com.gabriellourenco12.awsprojectconsumer.repository;

import com.gabriellourenco12.awsprojectconsumer.model.ProductEventKey;
import com.gabriellourenco12.awsprojectconsumer.model.ProductEventLog;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

@EnableScan
public interface ProductEventLogRepository extends CrudRepository<ProductEventLog, ProductEventKey> {

    List<ProductEventLog> findAllByPk(String code);

    List<ProductEventLog> findAllByPkAndSkStartsWith(String code, String eventType);
}
