package com.example.monitoringconsumer.repositories;

import com.example.monitoringconsumer.messages.Request;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestRepository extends ElasticsearchRepository<Request, String> {
}
