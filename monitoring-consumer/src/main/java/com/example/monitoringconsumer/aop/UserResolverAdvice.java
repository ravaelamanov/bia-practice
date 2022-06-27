package com.example.monitoringconsumer.aop;

import com.example.monitoringconsumer.services.resolvers.user.UserResolutionException;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class UserResolverAdvice {
    @Pointcut("within(com.example.monitoringconsumer.services.resolvers.user.UserResolver+) && execution(* resolve(..))")
    public void resolveMethod() {}

    @AfterThrowing(value = "resolveMethod()", throwing = "ex")
    public void translateExceptions(Exception ex) {
        throw new UserResolutionException(ex);
    }
}
