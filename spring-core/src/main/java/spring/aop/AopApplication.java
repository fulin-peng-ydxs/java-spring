package spring.aop;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.aop.conf.AopConfig;
import spring.aop.user.UserService;


public class AopApplication {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AopConfig.class);
        UserService bean = context.getBean(UserService.class);
        bean.userTest("1111");
    }
}
