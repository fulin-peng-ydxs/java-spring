package spring.ioc.applicationcontext;


import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.ioc.bean.Person;
import spring.ioc.bean.conf.Configuration;

/**
 * @author PengFuLin
 * @version 1.0
 * @description: 注解启动类
 * @date 2021/11/4 22:25
 */
//@Import(value ={Person.class} )
public class AnnotationContext {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(Configuration.class);
        Person bean = context.getBean(Person.class);
        System.out.println(bean);
    }
}
