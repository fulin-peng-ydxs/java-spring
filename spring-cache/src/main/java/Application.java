import cache.conf.BeanConfig;
import cache.entity.User;
import cache.service.UserService;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {


    public static void main(String[] args) {
        ConfigurableApplicationContext configurableApplicationContext =
                new AnnotationConfigApplicationContext(BeanConfig.class);
        simpleDemo(configurableApplicationContext);
    }

    /**缓存使用案例
     * 2023/4/19-0:34
     * @author pengfulin
    */
    private static  void simpleDemo(ConfigurableApplicationContext configurableApplicationContext){
        UserService userService = configurableApplicationContext.getBean(UserService.class);
        User user = userService.getUser(1);
        System.out.println(user);
        user = userService.getUser(1);
        System.out.println(user);
        user = userService.getUser(2);
        System.out.println(user);
        user = userService.getUser(2);
        System.out.println(user);
    }
}
