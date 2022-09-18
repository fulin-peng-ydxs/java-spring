package spring.ioc.bean.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import spring.ioc.bean.Person;

/**
 * @author PengFuLin
 * @version 1.0
 */
@Component
public class ComponentTest {

    @Bean(value = "person")
    public Person getPerson(){
      return new Person();
    }
}
