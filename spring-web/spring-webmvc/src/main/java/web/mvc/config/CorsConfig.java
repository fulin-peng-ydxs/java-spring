package web.mvc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * ����ӳ������
 * @author fulin-peng
 * 2024-06-17  17:59
 */
@Configuration
public class CorsConfig {

    /**
     * ʹ��Mappingӳ��ķ�ʽ
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")
                        .allowedOrigins("http://example.com")
                        .allowedMethods("GET", "POST", "PUT", "DELETE")
                        .allowedHeaders("*")
                        .allowCredentials(true); //����ָ��
            }
        };
    }


    /**
     * ʹ��cors-filter�������ķ�ʽ
     */
    @Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration corsConfiguration = new CorsConfiguration();
        //�Ƿ��������������֤��Ϣ
        corsConfiguration.setAllowCredentials(true);
        // ������ʵĿͻ�������
        corsConfiguration.addAllowedOriginPattern("*");
        // �������˷��ʵĿͻ�������ͷ
        corsConfiguration.addAllowedHeader("*");
        // ������ʵķ�����,GET POST��
        corsConfiguration.addAllowedMethod("*");
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(urlBasedCorsConfigurationSource);
    }
}

