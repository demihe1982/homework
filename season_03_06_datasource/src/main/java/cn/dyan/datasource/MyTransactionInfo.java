package cn.dyan.datasource;

import cn.dyan.tx.MyTransactionAttribute;
import cn.dyan.tx.MyTransactionStatus;

/**
 *
 * Created by demi on 2017/9/16.
 */
public interface MyTransactionInfo {

    /**
     * 获取事务状态
     * @param mtAttr
     * @return
     */
    MyTransactionStatus getTransaction(MyTransactionAttribute mtAttr);
}
