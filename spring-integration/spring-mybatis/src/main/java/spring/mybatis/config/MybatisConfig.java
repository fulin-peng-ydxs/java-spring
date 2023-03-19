package spring.mybatis.config;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * @author PengFuLin
 * 2023/2/25 0:12
 */
@Configuration
/*配置映射器包路径，使映射器放入spring容器中管理，可以明确指定映射器的会话的工厂，如在多数据源的场景下* */
@MapperScan(basePackages = "spring.mybatis.dao.dao1", sqlSessionFactoryRef = "db1SqlSessionFactory")
@MapperScan(basePackages = "spring.mybatis.dao.dao2", sqlSessionFactoryRef = "db2SqlSessionFactory")
public class MybatisConfig {

    /*=====第一个数据源*/

    /*数据源*/
    @Bean("db1DataSource")
    public DataSource getDb1DataSource(){
        return  getDataSource();
    }

    /*事务管理器*/
    @Bean(name = "db1TransactionManager")
    public DataSourceTransactionManager master1TransactionManager(@Qualifier("db1DataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    /*sqlSession工厂*/
    @Bean("db1SqlSessionFactory")
    public SqlSessionFactory db1SqlSessionFactory(@Qualifier("db1DataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource); //设置数据源
        bean.setTypeAliasesPackage("spring.mybatis.bean"); //设置类型别名
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:/mapper/*.xml"));  //设置mapper映射文件位置
        //设置插件
//        bean.setPlugins();
        return bean.getObject();
    }


    /*如果需要手动会话方式，不用mapper映射器，可以注入这个会话模型*/
    @Bean("db1SqlSessionTemplate")
    public SqlSessionTemplate db1SqlSessionTemplate(@Qualifier("db1SqlSessionFactory") SqlSessionFactory sqlSessionFactory){
        return new SqlSessionTemplate(sqlSessionFactory);
    }


    /*=====第二个数据源*/
    /*数据源*/
    @Bean("db2DataSource")
    public DataSource getDb2DataSource(){
        return  getDataSource();
    }

    /*事务管理器*/
    @Bean(name = "db2TransactionManager")
    public DataSourceTransactionManager master2TransactionManager(@Qualifier("db2DataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    /*sqlSession工厂*/
    @Bean("db2SqlSessionFactory")
    public SqlSessionFactory db2SqlSessionFactory(@Qualifier("db2DataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource); //设置数据源
        bean.setTypeAliasesPackage("spring.mybatis.bean"); //设置类型别名
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:/mapper/mapper2/*.xml"));  //设置mapper映射文件位置
        return bean.getObject();
    }


    /*如果需要手动会话方式，不用mapper映射器，可以注入这个会话模型*/
    @Bean("db2SqlSessionTemplate")
    public SqlSessionTemplate db2SqlSessionTemplate(@Qualifier("db2SqlSessionFactory") SqlSessionFactory sqlSessionFactory){
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    private  DataSource getDataSource(){
        Properties properties = new Properties();
        try {
            properties.load(MybatisConfig.class.getResourceAsStream("/druid.properties"));
            return DruidDataSourceFactory.createDataSource(properties);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
