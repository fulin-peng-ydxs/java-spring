package spring.ioc.bean.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import spring.ioc.bean.Person;

/**
 * ≈‰÷√¿‡≤‚ ‘
 *
 * @author PengFuLin
 * 2022/9/7 21:09
 */
@Configuration
public class ConfigureTest {

    @Bean
    public Person getPerson(){
        return new Person();
    }
}
