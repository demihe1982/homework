package cn.dyan.datasource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by demi on 2017/9/13.
 */
public abstract class AbstractDriverManagerDataSource  extends AbstractDataSource {

    private String url;
    private String username;
    private String password;
    private Properties connectionProperties;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Properties getConnectionProperties() {
        return connectionProperties;
    }

    public void setConnectionProperties(Properties connectionProperties) {
        this.connectionProperties = connectionProperties;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return getConnection(this.getUsername(),this.getPassword());
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        Properties mergedProps = new Properties();
        Properties cnnProps = this.getConnectionProperties();
        if(cnnProps !=null){
            mergedProps.putAll(cnnProps);
        }
        if(username !=null){
            mergedProps.put("user",username);
        }
        if(password !=null){
            mergedProps.put("password",password);
        }
        return getConnectionFromDriver(mergedProps);
    }

    public abstract Connection getConnectionFromDriver(Properties properties) throws SQLException ;
}
