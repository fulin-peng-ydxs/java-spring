package spring.web.client.template;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class RestTemplateTest {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(RestTemplateConf.class);
        RestTemplate restTemplate = context.getBean(RestTemplate.class);
        ResponseEntity<String> response = restTemplate.getForEntity("https://www.baidu.com", String.class);
        System.out.println(response.getBody());
    }

}
