package spring.ioc.bean.conf;

import org.springframework.context.annotation.*;
import spring.ioc.bean.Person;
import spring.ioc.bean.conditions.ConditionBean;
import spring.ioc.bean.scan.ScanBean;

/**
 * ≈‰÷√¿‡≤‚ ‘
 *
 * @author PengFuLin
 * 2022/9/7 21:09
 */
@ComponentScans({@ComponentScan(value = "spring.ioc.bean.scan",excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,classes = ScanBean.class)
}),@ComponentScan("spring.ioc.beanfactory")})
@Configuration
@Import({ConditionBean.class})
public class ConfigureTest {

    @Bean
    public Person getPerson(){
        return new Person();
    }
}
