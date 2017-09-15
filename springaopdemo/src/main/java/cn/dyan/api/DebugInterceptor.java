package cn.dyan.api;


import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * Created by demi on 2017/9/15.
 */
public class DebugInterceptor implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        System.out.println("Before: invocation=[" + methodInvocation + "]");
        Object rval = methodInvocation.proceed();
        System.out.println("Invocation returned");
        return rval;
    }
}
