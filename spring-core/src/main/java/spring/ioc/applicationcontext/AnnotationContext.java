package spring.ioc.applicationcontext;


import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author PengFuLin
 * @version 1.0
 * @description: 注解启动类
 * @date 2021/11/4 22:25
 */
public class AnnotationContext {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext annotationConfigApplicationContext =
                new AnnotationConfigApplicationContext(AnnotationContext.class);
    }

}
