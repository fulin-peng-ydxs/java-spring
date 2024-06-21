package web.mvc.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.Filter;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@WebFilter(filterName = "CORSFilter",urlPatterns = "/*")
public class CorsCustomFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        //安全做法:手动指定允许的请求
        response.setHeader("Access-Control-Allow-Origin", "http://example-client.com"); // 指定允许的域名
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE"); // 允许的HTTP方法
        response.setHeader("Access-Control-Max-Age", "3600"); // 缓存预检请求的时间
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization"); // 指定允许的请求头
        response.setHeader("Access-Control-Allow-Credentials", "true"); // 允许凭证：按需指定

        //调试做法：直接允许请求端发来的请求
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, request.getHeader(HttpHeaders.ORIGIN)); // 允许访问的域名
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, request.getHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS)); // 允许访问的HTTP方法
        response.setHeader(HttpHeaders.ACCESS_CONTROL_MAX_AGE, "3600"); // 缓存预检请求的时间
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS,  request.getHeader(HttpHeaders.ACCESS_CONTROL_REQUEST_HEADERS)); // 指定访问允许的请求头
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true"); // 允许凭证：按需指定
        if (RequestMethod.OPTIONS.name().equalsIgnoreCase(request.getMethod())) {  //预请求方法，则直接返回结果
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            chain.doFilter(req, res); //继续访问
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.debug("CorsCustomFilter:跨域过滤器已装载");
    }

    @Override
    public void destroy() {}
}
