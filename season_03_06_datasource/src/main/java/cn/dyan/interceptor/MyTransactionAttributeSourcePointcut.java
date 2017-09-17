package cn.dyan.interceptor;

import org.springframework.aop.support.StaticMethodMatcherPointcut;

import java.lang.reflect.Method;

/**
 * Created by demi on 2017/9/14.
 */
public abstract class MyTransactionAttributeSourcePointcut extends StaticMethodMatcherPointcut {
    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        MyTransactionAttributeSource tas = getTransactionAttributeSource();
        return tas !=null && tas.getTransactionAttribute(method,targetClass) !=null;
    }
    protected abstract MyTransactionAttributeSource getTransactionAttributeSource();
}
