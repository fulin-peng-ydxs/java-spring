package spring.ioc.context;


import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.ioc.bean.conf.ConfigureTest;
import spring.ioc.bean.factory.FactoryBeanDemo;

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
                new AnnotationConfigApplicationContext(ConfigureTest.class, FactoryBeanDemo.class);
//        Person bean = context.getBean(ConfigureTest.class);
//        ConfigureTest bean = context.getBean(ConfigureTest.class);
        System.out.println(context.getBean(FactoryBeanDemo.class));
        System.out.println(context.getBean("factoryBeanDemo"));
    }
}
