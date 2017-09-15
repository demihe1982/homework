package cn.dyan.api;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;

/**
 * Created by demi on 2017/9/15.
 */
public class SimpleAdvice implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        Object retVal = null;
       System.out.println("Before");
            methodInvocation.proceed();
       System.out.println("End");
       return retVal;
    }
}
