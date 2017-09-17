package cn.dyan.interceptor;

import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by demi on 2017
 * Created by demi on 2017/9/5.
 */
@Component
public class MyTransactionAdvisor extends AbstractPointcutAdvisor {

    @Autowired

    private MyTransactionAttributeSource myTransactionAtrributeSource;
    private final MyTransactionAttributeSourcePointcut pointcut = new MyTransactionAttributeSourcePointcut() {
        @Override
        protected MyTransactionAttributeSource getTransactionAttributeSource() {
            return myTransactionAtrributeSource;
        }
    };
    @Autowired
    private MyTransactionInterceptor myTransactionInterceptor;

    @Override
    public Pointcut getPointcut() {
        return this.pointcut;
    }

    @Override
    public Advice getAdvice() {
        return this.myTransactionInterceptor;
    }
}

