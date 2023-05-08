package spring.aop.conf;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect //��������
@Component
public class UserProxy {

    //���ù��������
    @Pointcut("execution(* spring.aop.user.*.*(..))")
    public void userAllPoint(){}

    //��������֪ͨ
    @Before(value = "userAllPoint()")
    public void userTest(){
        System.out.println("֪ͨ����........");
    }
}

