package cn.dyan.interceptor;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.support.AopUtils;

/**
 * Created by demi on 2017/9/16.
 */
public class MyTransactionInterceptor extends MyTransactionAspectSupport implements MethodInterceptor {
    @Override
    public Object invoke(final MethodInvocation invocation) throws Throwable {
        Class<?> targetClass = invocation.getThis()!=null?AopUtils.getTargetClass(invocation.getThis()):null;
        System.out.println("Method name :"+invocation.getMethod().getName()+" , targetClass:"+targetClass.getName());
        return invokeWithinTransaction(invocation.getMethod(),targetClass,new MyTransactionAspectSupport.InvocationCallback(){
            @Override
            public Object proceedWithInvocation() throws Throwable {
                return invocation.proceed();
            }
        });
    }
}
