package cn.dyan.datasource;

import cn.dyan.tx.MyTransactionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by demi on 2017/9/16.
 */
public abstract class DataSourceUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataSourceUtils.class);

    public static Connection getConnection(DataSource dataSource) throws SQLException {
        try {
            return doGetConnection(dataSource);
        } catch (SQLException e) {
            throw e;
        }
    }

    public static Connection doGetConnection(DataSource dataSource) throws SQLException {
        Assert.notNull(dataSource, "No DataSource specified");

        ConnectionHoldor conHolder = (ConnectionHoldor) MyTransactionManager.getResource(dataSource);
        if (conHolder != null && (conHolder.hasConnection() || conHolder.isSynchronizedWithTransaction())) {
            conHolder.requested();
            if (!conHolder.hasConnection()) {
                LOGGER.debug("Fetching resumed JDBC Connection from DataSource");
                conHolder.setConnection(dataSource.getConnection());
            }
            return conHolder.getConnection();
        }
        // Else we either got no holder or an empty thread-bound holder here.

        LOGGER.debug("Fetching JDBC Connection from DataSource");
        Connection con = dataSource.getConnection();
        MyTransactionManager.bindResource(dataSource, con);
        return con;
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
