package com.example.monitoringconsumer.messages;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtUserCreatedUpdated {
    private String id;
    private String username;
    @JsonProperty("company_id")
    private String companyId;
}
