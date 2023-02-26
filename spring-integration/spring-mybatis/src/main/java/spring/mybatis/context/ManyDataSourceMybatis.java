package spring.mybatis.context;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.context.ApplicationContext;
import spring.mybatis.dao.dao1.UserDao;
import spring.mybatis.dao.dao2.DbDao;

public class ManyDataSourceMybatis {


    public static void main(String[] args) {
        ApplicationContext applicationContext = ApplicationContextMybatis.getAnnotationApplicationContext();
        //从spring容器中取
        DbDao dbDao = applicationContext.getBean(DbDao.class);
        UserDao userDao = applicationContext.getBean(UserDao.class);
        System.out.println("容器访问："+dbDao.findDb().toString()+userDao.findUser().toString());
        SqlSessionTemplate db1SqlSessionTemplate = applicationContext.getBean("db1SqlSessionTemplate", SqlSessionTemplate.class);
        userDao= db1SqlSessionTemplate.getMapper(UserDao.class);
//        DbDao mapper = db1SqlSessionTemplate.getMapper(DbDao.class);  没有设置映射
        SqlSessionTemplate db2SqlSessionTemplate = applicationContext.getBean("db2SqlSessionTemplate", SqlSessionTemplate.class);
        dbDao=db2SqlSessionTemplate.getMapper(DbDao.class);
        System.out.println("模板访问："+dbDao.findDb().toString()+userDao.findUser().toString());
    }
}
