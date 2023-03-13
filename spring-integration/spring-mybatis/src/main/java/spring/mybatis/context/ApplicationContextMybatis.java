package spring.mybatis.context;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import spring.mybatis.config.MybatisConfig;
import spring.mybatis.dao.dao1.UserDao;
import java.util.List;
import java.util.Map;
/**
 * @author PengFuLin
 * 2023/2/25 0:05
 */
public class ApplicationContextMybatis {

    public static void main(String[] args) {
//        ApplicationContext context =getAnnotationApplicationContext();  //注解配置方式
        ApplicationContext context =getFileConfigApplicationContext(); //容器配置方式
        demoMapper(context);
        demoTemplate(context);
    }

    /*获取注解容器*/
    public static ApplicationContext getAnnotationApplicationContext(){
        return new AnnotationConfigApplicationContext(MybatisConfig.class);
    }

    /*获取配置文件容器*/
    public static ApplicationContext getFileConfigApplicationContext(){
        return new ClassPathXmlApplicationContext("/spring-context.xml");
    }

    /*mapper操作*/
    private static void demoMapper(ApplicationContext context){
        //从spring容器中获取mapper
        UserDao userDao = context.getBean(UserDao.class);
        //使用映射器访问数据库
        List<Map<?, ?>> user = userDao.findUser();
        List<Map<?, ?>> userMapper = userDao.findUserMapper();
        System.out.println(user.toString()+userMapper.toString());
        System.out.println("mapper结束");
    }

    /*模板操作*/
    private static void demoTemplate(ApplicationContext context){
        //从spring容器中获取会话模板
        SqlSessionTemplate db1SqlSessionTemplate = context.getBean("db1SqlSessionTemplate", SqlSessionTemplate.class);
        //会话模板中如果配置的mapper映射文件中的命名空间和对应的mapper映射器全限定名一样，则会在mybatis环境中一样会存储一个
        //对应的映射器对象，不过这个对象和存放在spring容器中的不是同一个，但功能是一样的
        UserDao userDao = db1SqlSessionTemplate.getMapper(UserDao.class);
        //使用映射器访问数据库
        List<Map<?, ?>> user = userDao.findUser();
        List<Map<?, ?>> userMapper = userDao.findUserMapper();
        System.out.println(user.toString()+userMapper.toString());
        System.out.println("template结束");
    }
}
