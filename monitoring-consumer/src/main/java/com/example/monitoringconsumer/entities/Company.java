package com.example.monitoringconsumer.entities;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "companies")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "company_id", unique = true)
    private String companyId;

    public Company(String companyId) {
        this.companyId = companyId;
    }

    public Company() {

    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

}