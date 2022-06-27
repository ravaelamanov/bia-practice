package com.example.monitoringconsumer.services.resolvers.user;

import com.example.monitoringconsumer.messages.Request;

public interface UserResolver {
    /**
     * Method resolves the user by setting the corresponding properties of <code>request</code> parameter (e.g. companyId, userId, username).
     * @param request Request object based on which the user will be resolved. Has to be mutable.
     * @throws UserResolutionException if any exception occurred during user resolution.
     * This is a wrapper exception with <code>getCause()<code/> returning the original exception.
     * In current implementation original exceptions are translated by
     * {@link com.example.monitoringconsumer.aop.UserResolverAdvice#translateExceptions(Exception) UserResolverAdvice}.
     * @see Request
     * @see com.example.monitoringconsumer.aop.UserResolverAdvice UserResolverAdvice
     */
    void resolve(Request request);

    AuthType authType();
}
