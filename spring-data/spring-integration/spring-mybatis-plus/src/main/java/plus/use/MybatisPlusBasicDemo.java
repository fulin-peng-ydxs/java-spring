package plus.use;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import plus.conf.MybatisBasicConfig;
import plus.dao.OrganMapper;
import plus.entity.Organ;
import plus.entity.User;

import java.util.List;

public class MybatisPlusBasicDemo {


    public static void main(String[] args) {
        SqlSessionFactory mybatisSqlSessionFactory = MybatisBasicConfig.getMybatisSqlSessionFactory();
        SqlSession sqlSession = mybatisSqlSessionFactory.openSession();
        //直接访问：
        // mybatis中，如果id属性在环境中是唯一的，则可以用简便方式直接访问，否则需要前缀拼凑命名空间访问
        List<User> users = sqlSession.selectList("plus.dao.UserMapper.selectList");
        users.forEach(System.out::println);
        //映射器访问：直接访问mybatis-plus以自动生成的MapperStatement以实现访问
        OrganMapper organMapper = sqlSession.getMapper(OrganMapper.class);
        List<Organ> organs = organMapper.selectList(null);
        organs.forEach(System.out::println);
    }
}
