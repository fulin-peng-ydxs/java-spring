package spring.ioc.context;


import org.springframework.context.annotation.AnnotatedBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import spring.ioc.bean.Cat;
import spring.ioc.bean.Person;
import spring.ioc.bean.conf.ComponentTest;
import spring.ioc.bean.conf.ConfigureTest;
import spring.ioc.bean.factory.FactoryBeanDemo;
import spring.ioc.bean.imports.ConfigureImportBean;

/**
 * @author PengFuLin
 * @version 1.0
 * @description: 注解启动类
 * @date 2021/11/4 22:25
 */
//@Import(value ={Person.class} )
public class AnnotationContext {

    public static void main(String[] args) {
        argContext();
//        notArgCodeContext();
//        notArgPathContext();
    }

    public static void argContext(){
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(ConfigureTest.class, FactoryBeanDemo.class, ConfigureImportBean.class);
        ConfigureTest configureTest = context.getBean(ConfigureTest.class);
        System.out.println(configureTest);
        System.out.println(context.getBean(FactoryBeanDemo.class));
        System.out.println(context.getBean("factoryBeanDemo"));

    }

    public static void notArgCodeContext(){
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext();
        AnnotatedBeanDefinitionReader reader = new AnnotatedBeanDefinitionReader(context);
        reader.register(Person.class);
        reader.register(Cat.class);
        context.refresh();
        System.out.println(context.getBean(Person.class).getCat());
    }

    public static void notArgPathContext(){
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext();
        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(context);
        scanner.scan("spring.ioc");
        context.refresh();
        System.out.println(context.getBean(ComponentTest.class));
    }
}
