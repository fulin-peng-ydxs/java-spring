package spring.ioc.bean.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import spring.ioc.bean.Person;

/**
 * @author PengFuLin
 * @version 1.0
 * @description: 配置测试类
 * @date 2022/4/5 13:08
 */
@Component
public class Configuration {

    @Bean(value = "person")
    public Person getPerson(){
      return new Person();
    }
}
