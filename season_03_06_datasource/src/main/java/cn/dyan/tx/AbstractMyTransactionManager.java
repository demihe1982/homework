package cn.dyan.tx;

/**
 * Created by demi on 2017/9/16.
 */
public abstract class AbstractMyTransactionManager implements PlatformMyTransactionManager {
    @Override
    public MyTransactionStatus getTransaction(MyTransactionAttribute transactionAttribute) {
        Object transaction = doGetTransaction();
        this.doBegin(transaction,transactionAttribute);
        return new DefaultTransactionStatus(transaction);
    }

    protected abstract Object doGetTransaction();

   protected abstract void doBegin(Object transaction,MyTransactionAttribute attribute);
}
