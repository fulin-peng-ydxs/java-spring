package schedule;


import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;

/**
 * 应用程序类
 *
 * @author pengshuaifeng
 * 2024/4/19
 */
@EnableScheduling
public class Application {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext annotationConfigApplicationContext =
                new AnnotationConfigApplicationContext(Application.class);
        ScheduledAnnotationBeanPostProcessor bean = annotationConfigApplicationContext.getBean(ScheduledAnnotationBeanPostProcessor.class);
        System.out.println(bean);
    }
}
