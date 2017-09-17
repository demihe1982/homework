package cn.dyan.datasource;

import org.springframework.util.ClassUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by demi on 2017/9/13.
 */
public class DriverManagerDataSource extends AbstractDriverManagerDataSource{

    public DriverManagerDataSource(){

    }

    public void setDriverClassName(String driverClassName) {
        String driverClassNameToUse = driverClassName.trim();
        try {
            Class.forName(driverClassNameToUse,true, ClassUtils.getDefaultClassLoader());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Connection getConnectionFromDriver(Properties properties) throws SQLException   {
        String url = getUrl();
        return DriverManager.getConnection(url,properties);
    }
}
