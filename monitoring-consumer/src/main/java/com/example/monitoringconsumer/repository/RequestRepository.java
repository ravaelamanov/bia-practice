package com.example.monitoringconsumer.repository;

import com.example.monitoringconsumer.domain.Request;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestRepository extends ElasticsearchRepository<Request, String> {
}
