package cn.dyan.interceptor;

import cn.dyan.tx.DelegatingMyTransactionAttribute;
import cn.dyan.tx.MyTransactionAttribute;
import cn.dyan.tx.MyTransactional;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * Created by demi on 2017/9/14.
 */

public class AnnotationMyTransactionAttributeSource extends AbstractMyTransactionAtrributeSource {
    @Override
    public MyTransactionAttribute findTransactionAttribute(Method method) {
        if(method.isAnnotationPresent(MyTransactional.class)){
            return new DelegatingMyTransactionAttribute();
        }
        return null;
    }
    @Override
    public MyTransactionAttribute findTransactionAttribute(Class<?> targetClass) {
        if(targetClass.isAnnotationPresent(MyTransactional.class)){
            return new DelegatingMyTransactionAttribute();
        }
        return null;
    }
}
