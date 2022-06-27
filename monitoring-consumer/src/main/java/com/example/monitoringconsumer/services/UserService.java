package com.example.monitoringconsumer.services;

import com.example.monitoringconsumer.entities.User;
import com.example.monitoringconsumer.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Optional<User> findById(String userId) {
        return repository.findByUserId(userId);
    }

    @Transactional
    public Optional<User> findBySynapseId(String synapseId) {
        return repository.findBySynapseId(synapseId);
    }

    @Transactional
    public void saveOrUpdate(User user) {
        User userToSave = findById(user.getUserId())
                .map(savedUser -> savedUser.updateSelf(user))
                .orElse(user);
        repository.save(userToSave);
    }

}
