package cn.dyan.datasource;

import cn.dyan.tx.MyTransactionManager;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by demi on 2017/9/16.
 */
public abstract class DataSourceUtils {

    public static Connection getConnection(DataSource dataSource) throws SQLException {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw e;
        }
    }

    public static void releaseConnection(Connection cnn, DataSource dataSource){
        if (cnn == null) {
            return;
        }
        if (dataSource != null) {
            ConnectionHoldor conHolder = (ConnectionHoldor) MyTransactionManager.getResource(dataSource);
            if (conHolder != null && connectionEquals(conHolder, cnn)) {
                // It's the transactional Connection: Don't close it.
                conHolder.released();
                return;
            }
        }
        try {
            doCloseConnection(cnn, dataSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private static boolean connectionEquals(ConnectionHoldor conHolder, Connection passedInCon) {
        if (!conHolder.hasConnection()) {
            return false;
        }
        Connection heldCon = conHolder.getConnection();
        return (heldCon == passedInCon || heldCon.equals(passedInCon));
    }

    public static void doCloseConnection(Connection con, DataSource dataSource) throws SQLException {
            con.close();
    }

}
