package spring.aop.conf;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;


@Configuration
@ComponentScan("spring.aop")
@EnableAspectJAutoProxy //开启@AspectJ的代理
public class AopConfig {
}
