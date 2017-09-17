package cn.dyan.datasource;

import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by demi on 2017/9/17.
 */
public class JdbcUtils {
    public static void closeStatement(Statement stmt){
        if(stmt !=null){
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
