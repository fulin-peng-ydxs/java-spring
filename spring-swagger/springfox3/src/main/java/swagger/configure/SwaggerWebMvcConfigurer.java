package swagger.configure;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebMvc
@Configuration
public class SwaggerWebMvcConfigurer implements WebMvcConfigurer {

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.
        addResourceHandler(  "/swagger-ui/**")
        .addResourceLocations("classpath:/META-INF/resources/webjars/springfox-swagger-ui/")
        .resourceChain(false);
  }

  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    registry.addViewController(  "/swagger-ui/")
        .setViewName("forward:" +  "/swagger-ui/index.html");
  }

}