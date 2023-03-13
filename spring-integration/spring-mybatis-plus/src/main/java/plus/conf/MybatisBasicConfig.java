package plus.conf;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.MybatisSqlSessionFactoryBuilder;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import javax.sql.DataSource;
import java.util.Properties;

/**非spring-ioc集成mybatis-plus方式
 * 2023/3/8-21:48
 * @author pengfulin
*/
public class MybatisBasicConfig {

    private static SqlSessionFactory sqlSessionFactory=null;

    static {
        //事务工厂
        JdbcTransactionFactory jdbcTransactionFactory = new JdbcTransactionFactory();
        //数据源
        DataSource dataSource = getDataSource();
        //mybatis基础环境
        Environment environment = new Environment("mybatis-plus", jdbcTransactionFactory, dataSource);
        //1.mybatis运行配置：用MybatisConfiguration代替
        MybatisConfiguration configuration = new MybatisConfiguration(environment);
        //添加mapper映射器：此时由MybatisSqlSessionFactoryBean生成mapper则会实现mybatis-plus的mapper增强功能
        configuration.addMappers("plus.dao");
        //2.创建会话工厂构建:用MybatisSqlSessionFactoryBuilder构建器代替SqlSessionFactoryBuilder
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new MybatisSqlSessionFactoryBuilder();
        sqlSessionFactory=sqlSessionFactoryBuilder.build(configuration);
    }

    public static SqlSessionFactory getMybatisSqlSessionFactory() {
        return sqlSessionFactory;
    }

    private static DataSource getDataSource(){
        Properties properties = new Properties();
        try {
            properties.load(MybatisBasicConfig.class.getResourceAsStream("/druid.properties"));
            return DruidDataSourceFactory.createDataSource(properties);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
