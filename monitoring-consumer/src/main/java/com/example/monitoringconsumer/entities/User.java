package com.example.monitoringconsumer.entities;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "users")
@Builder
@Getter
@Setter
public class User {
    public User() {
    }

    public User(UUID id, String userId, String username, String synapseId, Company company) {
        this.id = id;
        this.userId = userId;
        this.username = username;
        this.synapseId = synapseId;
        this.company = company;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "user_id", unique = true)
    private String userId;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "synapse_id", unique = true)
    private String synapseId;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    public User updateSelf(User newUser) {
        if (newUser.getUserId() != null) {
            this.setUserId(newUser.getUserId());
        }
        if (newUser.getUsername() != null) {
            this.setUsername(newUser.getUsername());
        }
        if (newUser.getSynapseId() != null) {
            this.setSynapseId(newUser.getSynapseId());
        }
        if (newUser.getCompany() != null && newUser.getCompany().getCompanyId() != null) {
            if (this.getCompany() == null) {
                this.setCompany(new Company());
            }
            this.getCompany().setCompanyId(newUser.getCompany().getCompanyId());
        }

        return this;
    }
}