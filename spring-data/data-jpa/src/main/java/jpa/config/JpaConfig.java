package jpa.config;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableJpaRepositories(basePackages = {"jpa.repository"})  //开启Jpa集成
@EnableTransactionManagement  //开始spring事务
public class JpaConfig {


    /**配置数据源
     * 2023/5/4-21:26
     * @author pengfulin
     */
    @Bean
    public DataSource getDataSource(){
        Properties properties = new Properties();
        try {
            properties.load(JpaConfig.class.getResourceAsStream("/druid.properties"));
            return DruidDataSourceFactory.createDataSource(properties);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**配置Jpa工厂：LocalContainerEntityManagerFactoryBean生成EntityManagerFactory
     * 2023/5/4-21:26
     * @author pengfulin
     */
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource){
        LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        localContainerEntityManagerFactoryBean.setDataSource(dataSource); //配置数据源
        localContainerEntityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter()); //配置Jpa适配器（hibernate实现）
        localContainerEntityManagerFactoryBean.setPackagesToScan("jpa.entity");  //配置实体扫描
        //配置jpa相关属性
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect","org.hibernate.dialect.MySQL57Dialect");
        properties.setProperty("hibernate.hbm2ddl.auto","update");
        properties.setProperty("hibernate.format_sql","true");
        properties.setProperty("hibernate.show_sql","true");
        localContainerEntityManagerFactoryBean.setJpaProperties(properties);
        return localContainerEntityManagerFactoryBean;
    }

    /**配置事务管理器 ： JpaTransactionManager继承了AbstractPlatformTransactionManager
     * 2023/5/4-21:45
     * @author pengfulin
     */
    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory){
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(entityManagerFactory);
        return jpaTransactionManager;
    }
}

