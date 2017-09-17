package cn.dyan.tx;

/**
 * Created by demi on 2017/9/16.
 */
public interface PlatformMyTransactionManager {

    /**
     * 提交事务
     * @param myTransactionSource
     */
    void commit(MyTransactionStatus myTransactionSource);

    /**
     * 回滚事务
     * @param myTransactionSource
     */
    void rollback(MyTransactionStatus myTransactionSource);


    /**
     * 获取事务状态
     * @param transactionAttribute
     * @return
     */
    MyTransactionStatus getTransaction(MyTransactionAttribute transactionAttribute);



}
