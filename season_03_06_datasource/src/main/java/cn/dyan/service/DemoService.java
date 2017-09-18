package cn.dyan.service;

import cn.dyan.datasource.JdbcTemplate;
import cn.dyan.tx.MyTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.UUID;

/**
 * Created by demi on 2017/9/5.
 */
@Component
public class DemoService {

    // TODO 从DataSource上getConnection不在事务上下文中，不被事务管理
    @Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @MyTransactional
    public void sayHello(String uname) throws Exception {
        String sql = "INSERT INTO tb_user(uuid,uname) VALUES('"+ UUID.randomUUID()+"','"+uname+"')";
        jdbcTemplate.execute(sql);
    }

}
