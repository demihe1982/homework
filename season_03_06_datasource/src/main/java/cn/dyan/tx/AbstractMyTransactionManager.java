package cn.dyan.tx;

import cn.dyan.datasource.DataSourceMyTransactionManager;

/**
 * Created by demi on 2017/9/16.
 */
public abstract class AbstractMyTransactionManager implements PlatformMyTransactionManager {
    @Override
    public MyTransactionStatus getTransaction(MyTransactionAttribute transactionAttribute) {
        Object transaction = doGetTransaction();

        DataSourceMyTransactionManager.DataSourceTransactionObject dataSourceObject = (DataSourceMyTransactionManager.DataSourceTransactionObject)transaction;
        if(dataSourceObject.getConnectionHolder() !=null){
            return new DefaultTransactionStatus(dataSourceObject);
        }
        this.doBegin(transaction,transactionAttribute);
        return new DefaultTransactionStatus(transaction);
    }

    protected abstract Object doGetTransaction();

   protected abstract void doBegin(Object transaction,MyTransactionAttribute attribute);
}
