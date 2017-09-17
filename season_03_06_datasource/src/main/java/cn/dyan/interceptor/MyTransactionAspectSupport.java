package cn.dyan.interceptor;

import cn.dyan.tx.MyTransactionAttribute;
import cn.dyan.tx.MyTransactionStatus;
import cn.dyan.tx.PlatformMyTransactionManager;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.core.NamedThreadLocal;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Method;

/**
 * Created by demi on 2017/9/16.
 */
public class MyTransactionAspectSupport{
    private MyTransactionAttributeSource transactionAttributeSource;
    private PlatformMyTransactionManager txManager;
    private static final ThreadLocal<MyTransactionInfo> transactionInfoHolder = new NamedThreadLocal<MyTransactionInfo>("Current aspect-driven transaction");

    public Object invokeWithinTransaction(Method method, Class<?> targetClass,InvocationCallback invocationCallback) throws Throwable {
        MyTransactionAttribute tAttr = this.transactionAttributeSource.getTransactionAttribute(method,targetClass);
        String joinpointMethodInfo = ClassUtils.getQualifiedMethodName(method, targetClass);
        MyTransactionInfo txInfo = createTransactionIfNecessary(this.txManager,tAttr,joinpointMethodInfo);
        Object retVal = null;
        try{
            retVal = invocationCallback.proceedWithInvocation();
            commitTransactionAfterReturning(txInfo);
        }catch (Throwable ex){
            completeTransactionAfterThrowing(txInfo,ex);
        }finally {
            cleanupTransactionInfo(txInfo);
        }
        return retVal;
    }

    protected void commitTransactionAfterReturning(MyTransactionInfo txInfo) {
        if (txInfo != null && txInfo.hasTransaction()) {
            txInfo.getTransactionManager().commit(txInfo.getTransactionStatus());
        }
    }


    protected void completeTransactionAfterThrowing(MyTransactionInfo txInfo, Throwable ex) {
        if (txInfo != null && txInfo.hasTransaction()) {
            txInfo.getTransactionManager().rollback(txInfo.getTransactionStatus());
        }
    }

    protected void cleanupTransactionInfo(MyTransactionInfo txInfo) {
        if (txInfo != null) {
            txInfo.restoreThreadLocalStatus();
        }
    }


    public void setTransactionAttributeSource(MyTransactionAttributeSource transactionAttributeSource) {
        this.transactionAttributeSource = transactionAttributeSource;
    }

    public void setTxManager(PlatformMyTransactionManager txManager) {
        this.txManager = txManager;
    }

    protected MyTransactionInfo createTransactionIfNecessary(
            PlatformMyTransactionManager tm, MyTransactionAttribute txAttr, final String joinpointIdentification) {

        MyTransactionStatus status = null;
        if (txAttr != null) {
            if (tm != null) {
                status = tm.getTransaction(txAttr);
            }
        }
        MyTransactionInfo transactionInfo =  new MyTransactionInfo(tm, txAttr, joinpointIdentification);
        transactionInfo.newTransactionStatus(status);
        transactionInfo.bindToThread();
        return transactionInfo;
    }
    protected final class MyTransactionInfo {

        private final PlatformMyTransactionManager transactionManager;

        private final MyTransactionAttribute transactionAttribute;

        private final String joinpointIdentification;

        private MyTransactionStatus transactionStatus;

        private MyTransactionInfo oldTransactionInfo;

        public MyTransactionInfo(PlatformMyTransactionManager transactionManager,
                                 MyTransactionAttribute transactionAttribute, String joinpointIdentification) {

            this.transactionManager = transactionManager;
            this.transactionAttribute = transactionAttribute;
            this.joinpointIdentification = joinpointIdentification;
        }

        public PlatformMyTransactionManager getTransactionManager() {
            return this.transactionManager;
        }

        public MyTransactionAttribute getTransactionAttribute() {
            return this.transactionAttribute;
        }

        /**
         * Return a String representation of this joinpoint (usually a Method call)
         * for use in logging.
         */
        public String getJoinpointIdentification() {
            return this.joinpointIdentification;
        }

        public void newTransactionStatus(MyTransactionStatus status) {
            this.transactionStatus = status;
        }

        public MyTransactionStatus getTransactionStatus() {
            return this.transactionStatus;
        }

        /**
         * Return whether a transaction was created by this aspect,
         * or whether we just have a placeholder to keep ThreadLocal stack integrity.
         */
        public boolean hasTransaction() {
            return (this.transactionStatus != null);
        }

        private void bindToThread() {
            // Expose current TransactionStatus, preserving any existing TransactionStatus
            // for restoration after this transaction is complete.
            this.oldTransactionInfo = transactionInfoHolder.get();
            transactionInfoHolder.set(this);
        }

        private void restoreThreadLocalStatus() {
            // Use stack to restore old transaction TransactionInfo.
            // Will be null if none was set.
            transactionInfoHolder.set(this.oldTransactionInfo);
        }
    }

    /**
     * 执行回调
     */
    protected interface InvocationCallback {
        Object proceedWithInvocation() throws Throwable;
    }


}
