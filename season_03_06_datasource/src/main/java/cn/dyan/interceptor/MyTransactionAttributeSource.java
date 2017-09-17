package cn.dyan.interceptor;

import cn.dyan.tx.MyTransactionAttribute;

import java.lang.reflect.Method;

/**
 * 定义所有方法资源
 * Created by demi on 2017/9/16.
 */
public interface MyTransactionAttributeSource {
    /**
     * 根据方法及目的类，获取事务属性
     * @param method
     * @param targetClass
     * @return
     */
    MyTransactionAttribute getTransactionAttribute(Method method, Class<?> targetClass);
}
