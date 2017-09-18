package cn.dyan.dao;

import cn.dyan.datasource.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrgDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public boolean createOrg(String orgName) throws Exception {
        String sql = "INSERT INTO tb_org(org_name) VALUES('"+orgName+"')";
        jdbcTemplate.execute(sql);
        return true;
    }
}
