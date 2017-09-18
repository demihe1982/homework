package cn.dyan.datasource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;

/**
 * Created by demi on 2017/9/17.
 */
public class JdbcTemplate {
    private DataSource dataSource;
    public JdbcTemplate(DataSource dataSource){
        this.dataSource = dataSource;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public boolean execute(String sql) throws Exception{
        Connection con = null;
        Statement stmt = null;
        try {
            con = DataSourceUtils.getConnection(getDataSource());
            System.out.println("autoCommit is : "+con.getAutoCommit());
            stmt = con.createStatement();
            return stmt.execute(sql);
        }catch (Exception e){
            throw e;
        }finally {
            JdbcUtils.closeStatement(stmt);
            DataSourceUtils.releaseConnection(con,getDataSource());
        }
    }
}
