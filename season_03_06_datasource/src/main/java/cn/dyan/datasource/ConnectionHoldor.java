package cn.dyan.datasource;

import java.sql.Connection;

/**
 * Created by demi on 2017/9/16.
 */
public class ConnectionHoldor {
    private Connection connection;
    private boolean isNewTransaction;
    private boolean isWithTransaction;
    private boolean isTransactionActive = false;

    //记录引用连接的次数
    private int referenceCount=0;

    public ConnectionHoldor(Connection connection,boolean isNewTransaction){
        this.connection = connection;
        this.isNewTransaction = isNewTransaction;
    }
    public ConnectionHoldor(Connection cnn){
        this.connection = cnn;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean isNewTransaction() {
        return isNewTransaction;
    }

    public void setNewTransaction(boolean newTransaction) {
        isNewTransaction = newTransaction;
    }
    public boolean isSynchronizedWithTransaction(){
        return isWithTransaction;
    }
    public void setSynchronizedWithTransaction(boolean isWithTransaction){
        this.isWithTransaction = isWithTransaction;
    }
    public boolean hasConnection(){
        return  this.connection !=null;
    }
    public void setTransactionActive(boolean active){
        this.isTransactionActive = active;
    }
    public void requested() {
        this.referenceCount++;
    }

    /**
     * Decrease the reference count by one because the holder has been released
     * (i.e. someone released the resource held by it).
     */
    public void released() {
        this.referenceCount--;
    }
}
