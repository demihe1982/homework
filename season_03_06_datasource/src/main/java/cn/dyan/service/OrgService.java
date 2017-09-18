package cn.dyan.service;

import cn.dyan.datasource.JdbcTemplate;
import cn.dyan.tx.MyTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrgService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @MyTransactional
    public void insertOrg(String orgName) throws Exception {
        String sql = "INSERT INTO tb_org(org_name) VALUES('"+orgName+"')";
        jdbcTemplate.execute(sql);
    }
}
