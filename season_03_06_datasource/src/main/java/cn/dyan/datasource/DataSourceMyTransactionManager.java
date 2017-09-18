package cn.dyan.datasource;

import cn.dyan.tx.*;
import com.sun.media.jfxmedia.locator.ConnectionHolder;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by demi on 2017/9/16.
 */
public class DataSourceMyTransactionManager extends AbstractMyTransactionManager {
    private DataSource dataSource;
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public DataSource getDataSource(){
        return  this.dataSource;
    }

    @Override
    public void commit(MyTransactionStatus myTransactionSource) {
        DataSourceTransactionObject txObject = (DataSourceTransactionObject) myTransactionSource.getTransaction();
        Connection con = txObject.getConnectionHolder().getConnection();
        try {
            con.commit();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void rollback(MyTransactionStatus myTransactionSource) {
        DataSourceTransactionObject txObject = (DataSourceTransactionObject) myTransactionSource.getTransaction();
        Connection con = txObject.getConnectionHolder().getConnection();
        try {
            con.rollback();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    protected void doBegin(Object transaction,MyTransactionAttribute attribute) {
        DataSourceTransactionObject txObject = (DataSourceTransactionObject) transaction;
        Connection con = null;
        try {
            if (txObject.getConnectionHolder() == null ||
                    txObject.getConnectionHolder().isSynchronizedWithTransaction()) {
                Connection newCon = this.dataSource.getConnection();
                txObject.setConnectionHolder(new ConnectionHoldor(newCon),true);
            }
            txObject.getConnectionHolder().setSynchronizedWithTransaction(true);
            con = txObject.getConnectionHolder().getConnection();

            //如果Connection 为自动提交，则设置为手动提交
            if (con.getAutoCommit()) {
                txObject.setMustRestoreAutoCommit(true);
                //关闭自动提交
                con.setAutoCommit(false);
            }
            txObject.getConnectionHolder().setTransactionActive(true);
            // Bind the connection holder to the thread.
            if (txObject.isNewConnectionHolder()) {
                MyTransactionManager.bindResource(getDataSource(), txObject.getConnectionHolder());
            }
        }

        catch (Throwable ex) {
            if (txObject.isNewConnectionHolder()) {
                DataSourceUtils.releaseConnection(con, this.dataSource);
                txObject.setConnectionHolder(null, false);
            }
        }
    }



    @Override
    public Object doGetTransaction() {
        DataSourceTransactionObject txObject = new DataSourceTransactionObject();
        ConnectionHoldor conHolder =(ConnectionHoldor) MyTransactionManager.getResource(this.dataSource);
        txObject.setConnectionHolder(conHolder, false);
        return txObject;
    }

    public class DataSourceTransactionObject{
        private boolean newConnectionHolder;
        private ConnectionHoldor connectionHolder;
        private boolean mustRestoreAutoCommit =false;


        public void setConnectionHolder(ConnectionHoldor connectionHolder, boolean newConnectionHolder) {
            this.connectionHolder = connectionHolder;
            this.newConnectionHolder = newConnectionHolder;
        }

        public ConnectionHoldor getConnectionHolder() {
            return connectionHolder;
        }

        public void setMustRestoreAutoCommit(boolean mustRestoreAutoCommit) {
            this.mustRestoreAutoCommit = mustRestoreAutoCommit;
        }

        public boolean isMustRestoreAutoCommit() {
            return mustRestoreAutoCommit;
        }

        public boolean isNewConnectionHolder() {
            return newConnectionHolder;
        }

    }
}
