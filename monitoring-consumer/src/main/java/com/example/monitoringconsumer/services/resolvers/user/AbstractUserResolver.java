package com.example.monitoringconsumer.services.resolvers.user;

import com.example.monitoringconsumer.entities.User;
import com.example.monitoringconsumer.messages.Request;

public abstract class AbstractUserResolver implements UserResolver {
    void setRequestProperties(Request request, User user) {
        request.setUserId(user.getUserId());
        request.setUsername(user.getUsername());
        if (user.getCompany() != null) {
            request.setCompanyId(user.getCompany().getCompanyId());
        }
    }
}
