package cn.dyan.service;

import cn.dyan.dao.OrgDao;
import cn.dyan.dao.UserDao;
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
    private OrgDao orgDao;
    @Autowired
    private UserDao userDao;

    @MyTransactional
    public void insertInfo(String uname,String orgName) throws Exception {
        orgDao.createOrg(orgName);
        userDao.createUser(uname);
    }

}
