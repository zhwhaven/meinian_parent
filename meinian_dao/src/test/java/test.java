import com.haven.dao.RoleDao;
import com.haven.dao.UserDao;
import com.haven.pojo.Permission;
import com.haven.pojo.Role;
import com.haven.pojo.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-dao.xml")
public class test {
    @Autowired
    UserDao userDao;
    @Autowired
    RoleDao roleDao;
    @Test
    public void test(){
        User user = userDao.selectByUsername("admin");
        System.out.println(user.getPassword());
        Set<Role> roles = user.getRoles();
        for (Role role : roles) {
            Set<Permission> permissions = role.getPermissions();
            for (Permission permission : permissions) {
                System.out.println(permission.getKeyword());
            }
        }
//        Set<Role> roles1 = roleDao.selectRoleByUserID(4);
//        for (Role role : roles1) {
//            System.out.println(role.getId());
//        }
    }
}
