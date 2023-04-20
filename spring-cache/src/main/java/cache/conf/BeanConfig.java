package cache.conf;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheFactoryBean;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

//开启缓存
@EnableCaching
@Configuration
@ComponentScan("cache")
public class BeanConfig {


    //配置缓存管理器
    @Bean
    public CacheManager cacheManager(Cache cache){
        SimpleCacheManager simpleCacheManager = new SimpleCacheManager();
        simpleCacheManager.setCaches(Collections.singleton(cache));
        return simpleCacheManager;
    }

    //配置缓存工厂
    @Bean
    public ConcurrentMapCacheFactoryBean cacheFactoryBean(){
        ConcurrentMapCacheFactoryBean concurrentMapCacheFactoryBean = new ConcurrentMapCacheFactoryBean();
        concurrentMapCacheFactoryBean.setName("user");
        return concurrentMapCacheFactoryBean;
    }
}
