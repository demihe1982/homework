package cn.dyan.dao;

import cn.dyan.datasource.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UserDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public boolean createUser(String uname) throws Exception {
        String sql = "INSERT INTO tb_user(uuid,uname) VALUES('"+ UUID.randomUUID()+"','"+uname+"')";
        jdbcTemplate.execute(sql);
        return true;
    }

}
