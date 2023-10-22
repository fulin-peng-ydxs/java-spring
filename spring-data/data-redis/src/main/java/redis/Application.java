package redis;


import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import redis.conf.SimpleRedisTemplateConfig;

/**
 * 启动类
 * author: pengshuaifeng
 * 2023/10/21
 */
public class Application {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(SimpleRedisTemplateConfig.class);
    }
}
