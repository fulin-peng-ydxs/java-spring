package plus.conf;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.Properties;
/**spring-ioc集成mybatis-plus方式
 * 2023/3/8-21:49
 * @author pengfulin
*/
@Configuration
/*配置映射器包路径，使映射器放入spring容器中管理，可以明确指定映射器的会话的工厂，如在多数据源的场景下* */
@MapperScan(basePackages = "plus.dao", sqlSessionFactoryRef = "db1SqlSessionFactory")
public class MybatisSpringConfig {
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
        MybatisSqlSessionFactoryBean bean = new MybatisSqlSessionFactoryBean();
        bean.setDataSource(dataSource); //设置数据源
        bean.setTypeAliasesPackage("mybatis.bean"); //设置类型别名
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:/mapper/*.xml"));  //设置mapper映射文件位置
        //全局配置
        GlobalConfig globalConfig = new GlobalConfig();
        bean.setGlobalConfig(globalConfig);
            //数据库配置
        GlobalConfig.DbConfig dbConfig = new GlobalConfig.DbConfig();
        globalConfig.setDbConfig(dbConfig);
        configDb(dbConfig);
            //自定义主键配置
//        globalConfig.setIdentifierGenerator(value->{
//            //可以将当前传入的class全类名来作为bizKey,或者提取参数来生成bizKey进行分布式Id调用生成.
//            String bizKey = value.getClass().getName();
//            //根据bizKey调用分布式ID生成
//            return Long.parseLong(bizKey)+123;
//        });
        //设置通用枚举注册:
//        //1.设置通用枚举扫描包:支持通配符 * 或者 ; 分割
//        bean.setTypeEnumsPackage("plus.enum");
//        //2.指定mybatis的默认枚举类型处理器为MybatisEnumTypeHandler
//        bean.setConfiguration(new MybatisConfiguration());
//        bean.getConfiguration().setDefaultEnumTypeHandler(MybatisEnumTypeHandler.class);
        //插件配置
        bean.setPlugins(configPlugins());
        return bean.getObject();
    }

    private void configDb(GlobalConfig.DbConfig dbConfig){
//        dbConfig.setTablePrefix("t_"); //设置表映射前缀
        dbConfig.setIdType(IdType.AUTO); //设置主键字段策略
        dbConfig.setLogicDeleteField("version");  //设置逻辑删除配置
        dbConfig.setLogicDeleteValue("1");
        dbConfig.setLogicNotDeleteValue("0");
    }

    private MybatisPlusInterceptor configPlugins(){
        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
        //分页插件
        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor();
        mybatisPlusInterceptor.addInnerInterceptor(paginationInnerInterceptor);
        //乐观锁插件
        OptimisticLockerInnerInterceptor optimisticLockerInnerInterceptor = new OptimisticLockerInnerInterceptor();
        mybatisPlusInterceptor.addInnerInterceptor(optimisticLockerInnerInterceptor);
        //全表更新插件
        BlockAttackInnerInterceptor blockAttackInnerInterceptor = new BlockAttackInnerInterceptor();
        mybatisPlusInterceptor.addInnerInterceptor(blockAttackInnerInterceptor);
        return mybatisPlusInterceptor;
    }



    /*如果需要手动会话方式，不用mapper映射器，可以注入这个会话模型*/
    @Bean("db1SqlSessionTemplate")
    public SqlSessionTemplate db1SqlSessionTemplate(@Qualifier("db1SqlSessionFactory") SqlSessionFactory sqlSessionFactory){
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    private  DataSource getDataSource(){
        Properties properties = new Properties();
        try {
            properties.load(MybatisSpringConfig.class.getResourceAsStream("/druid.properties"));
            return DruidDataSourceFactory.createDataSource(properties);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
