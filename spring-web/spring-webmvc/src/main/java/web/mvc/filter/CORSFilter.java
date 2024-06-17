package web.mvc.filter;

import lombok.extern.slf4j.Slf4j;
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
public class CORSFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        
        //全部放开
        response.setHeader("Access-Control-Allow-Origin", "*"); //允许所有域名
        response.setHeader("Access-Control-Allow-Methods", "*"); //允许所有请求头
        response.setHeader("Access-Control-Max-Age", "3600"); //设置预检请求结果的缓存时间，以减少预检请求的数量。
        response.setHeader("Access-Control-Allow-Headers", "*"); // 允许所有请求头
        response.setHeader("Access-Control-Allow-Credentials", "true");  //允许携带认证信息（cookie信息）

        //安全做法
        response.setHeader("Access-Control-Allow-Origin", "http://example-client.com"); // 指定允许的域名
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE"); // 允许的HTTP方法
        response.setHeader("Access-Control-Max-Age", "3600"); // 缓存预检请求的时间
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization"); // 指定允许的请求头
        response.setHeader("Access-Control-Allow-Credentials", "true"); // 允许凭证

        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {  //预请求方法，则直接返回结果
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            chain.doFilter(req, res); //继续访问
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.debug("CORSFilter:跨域过滤器已装载");
    }

    @Override
    public void destroy() {}
}
