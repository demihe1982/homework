package cn.dyan.tx;

/**
 * Created by demi on 2017/9/16.
 */
public abstract class AbstractMyTransactionManager implements PlatformMyTransactionManager {
    @Override
    public MyTransactionStatus getTransaction(MyTransactionAttribute transactionAttribute) {
        Object transaction = doGetTransaction();
        return new DefaultTransactionStatus(transaction);
    }

    protected abstract Object doGetTransaction();
}
