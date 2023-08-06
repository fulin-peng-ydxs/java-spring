package spring.aop.conf;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect //声明切面
@Component
public class ProxyConfig {

    //配置公共切入点
    @Pointcut("execution(* spring.aop.user.*.*(..))")
    public void userAllPoint(){}

    //配置切入通知
    @Before(value = "userAllPoint()")
    public void userTest(){
        System.out.println("通知代理........");
    }

    @Before(value = "@within(spring.aop.annotation.ProxyTarget)")
    public void withinTest(){
        System.out.println("within代理通知........");
    }

}

