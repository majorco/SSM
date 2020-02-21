package atcrowdfundingtest;

import com.atguigu.crowd.funding.entity.Admin;
import com.atguigu.crowd.funding.mapper.AdminMapper;
import com.atguigu.crowd.funding.service.api.AdminService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @program: atcrowdfundingadmin0parent
 * @description
 * @author: li
 * @create: 2020-02-18 21:15
 **/
@RunWith(SpringRunner.class)
@ContextConfiguration(locations = {"classpath:spring-persist-mybatis.xml",
        "classpath:spring-persist-tx.xml","classpath:spring-web-mvc.xml"})
public class test {
    @Autowired
    private DataSource dataSource;
    @Autowired
    private AdminService adminService;
    @Autowired
    private AdminMapper adminMapper;
    @Test
    public void testConnection() throws SQLException {
        Connection connection = dataSource.getConnection();
        System.out.println(connection);
    }
    @Test
    public void testMybatis(){
        List<Admin> all = adminService.getAll();
        for(Admin admin:all){
            System.out.println(admin);
        }

    }
    @Test
    public void testTx(){
        List<Admin> admins = adminMapper.selectAdminListByKeyWord("");
        for (Admin admin:admins){
            System.out.println(admin);
        }
    }
    @Test
    public void insert(){
        for(int i=0;i<20;i++){

            adminMapper.insert(new Admin("accout"+i, "accout"+i,"accout"+i,i+"@qq","null"));
        }
    }



}
