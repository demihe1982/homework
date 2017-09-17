package cn.dyan.tx;

import cn.dyan.tx.MyTransactionStatus;

/**
 * Created by demi on 2017/9/17.
 */
public class DefaultTransactionStatus implements MyTransactionStatus{
    private Object transaction;

    public DefaultTransactionStatus(Object transaction){
        this.transaction = transaction;
    }

    @Override
    public Object getTransaction() {
        return transaction;
    }
}
