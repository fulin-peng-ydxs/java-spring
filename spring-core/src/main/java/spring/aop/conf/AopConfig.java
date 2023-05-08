package spring.aop.conf;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;


@Configuration
@ComponentScan("spring.aop")
@EnableAspectJAutoProxy //����@AspectJ�Ĵ���
public class AopConfig {
}
