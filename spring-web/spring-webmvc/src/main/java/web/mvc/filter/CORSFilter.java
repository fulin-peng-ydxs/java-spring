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
        
        //ȫ���ſ�
        response.setHeader("Access-Control-Allow-Origin", "*"); //������������
        response.setHeader("Access-Control-Allow-Methods", "*"); //������������ͷ
        response.setHeader("Access-Control-Max-Age", "3600"); //����Ԥ���������Ļ���ʱ�䣬�Լ���Ԥ�������������
        response.setHeader("Access-Control-Allow-Headers", "*"); // ������������ͷ
        response.setHeader("Access-Control-Allow-Credentials", "true");  //����Я����֤��Ϣ��cookie��Ϣ��

        //��ȫ����
        response.setHeader("Access-Control-Allow-Origin", "http://example-client.com"); // ָ�����������
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE"); // �����HTTP����
        response.setHeader("Access-Control-Max-Age", "3600"); // ����Ԥ�������ʱ��
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization"); // ָ�����������ͷ
        response.setHeader("Access-Control-Allow-Credentials", "true"); // ����ƾ֤

        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {  //Ԥ���󷽷�����ֱ�ӷ��ؽ��
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            chain.doFilter(req, res); //��������
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.debug("CORSFilter:�����������װ��");
    }

    @Override
    public void destroy() {}
}
