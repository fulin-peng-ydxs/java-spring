package spring.aop;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.aop.conf.AopConfig;
import spring.aop.user.OrganService;
import spring.aop.user.UserService;


public class AopApplication {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AopConfig.class);
        UserService userService = context.getBean(UserService.class);
        OrganService organService = context.getBean(OrganService.class);
        //ด๚ภํทรฮส
        userService.userTest("1111");
        organService.getOrgan();
    }
}
