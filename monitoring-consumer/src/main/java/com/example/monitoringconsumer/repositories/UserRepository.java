package com.example.monitoringconsumer.repositories;

import com.example.monitoringconsumer.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByUserId(String userId);

    Optional<User> findBySynapseId(String synapseId);
}