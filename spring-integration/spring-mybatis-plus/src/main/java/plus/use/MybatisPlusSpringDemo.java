package plus.use;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import plus.conf.MybatisSpringConfig;
import plus.dao.UserMapper;
import plus.entity.User;
import java.util.List;


public class MybatisPlusSpringDemo {


    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MybatisSpringConfig.class);
         //1.工厂获取
        SqlSessionFactory bean = context.getBean(SqlSessionFactory.class);
        UserMapper userMapper = bean.openSession().getMapper(UserMapper.class);
        List<User> users = userMapper.selectList(null);
        System.out.println(users);
         //2.容器获取
        UserMapper userMapper1 = context.getBean(UserMapper.class);
        List<User> users1 = userMapper1.selectList(null);
        System.out.println(users1);
        //3.条件构造测试
        testLambdaWrapper(userMapper);
    }

    private static void testLambdaWrapper(UserMapper mapper){
        User user = new User();
        user.setName("12");
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>(user);
        userLambdaQueryWrapper.like(User::getAge,1);
        List<User> users = mapper.selectList(userLambdaQueryWrapper);
        System.out.println(users);
    }
}
