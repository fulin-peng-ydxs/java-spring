package template.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.Map;

/**
 *  RestTemplate远程调用工具类
 * @author peng_fu_lin
 * 2023-06-02 09:48
 */
@Slf4j
public class RestTemplateCallUtil {

    /**
     * post通用请求
     * 2023/10/16 22:06
     * @author pengshuaifeng
     */
    public static <T> T executePost(RestTemplate restTemplate,String url, Map<String,Object> params,Map<String,String> headers,
                                Class<T> responseType){
        return execute(restTemplate, HttpMethod.POST, url, params, headers, responseType);
    }

    /**
     * get通用请求
     * 2023/10/16 22:06
     * @author pengshuaifeng
     */
    public static <T> T executeGet(RestTemplate restTemplate,String url, Map<String,Object> params,Map<String,String> headers,
                                    Class<T> responseType){
        return execute(restTemplate, HttpMethod.GET, url, params, headers, responseType);
    }

    /**
     * 通用请求
     * 2023/10/17 23:36
     * @param restTemplate 请求模版对象
     * @param method 请求方法
     * @param url 请求地址
     * @param params 请求参数
     * @param headers 请求头
     * @param responseType 响应类型
     * @author pengshuaifeng
     */
    public static <T> T execute(RestTemplate restTemplate, HttpMethod method, String url, Map<String,Object> params,Map<String,String> headers,
                                             Class<T> responseType) {
        if(restTemplate==null)
            restTemplate=createDefaultRestTemplate();
        ResponseEntity<T> responseEntity;
        //设置请求头
        HttpHeaders httpHeaders=null;
        if (headers!=null){
            httpHeaders =  setRestFulHeaders(headers);
            log.debug("HttpRestTemplate服务调用-请求头：{}",httpHeaders);
        }
        //请求处理
        if(method==HttpMethod.GET){  //GET请求处理
            log.debug("");
            //请求实体：仅设置请求头头
            HttpEntity<?> entity = new HttpEntity<>(httpHeaders);
            log.debug("HttpRestTemplate服务调用-GET请求：url：{}/params：{}",url,params);
            if(params!=null){   //有参数调用
                //TODO 不单是Get可处理，且建议使用web自带工具类
                boolean pathVariables = url.matches(".*\\{.+}.*");  //url是否包含可变参数
                if(pathVariables){  //可变参数处理：参数名与路径参数名匹配
                    responseEntity=restTemplate.exchange(url,method,entity,responseType,params);
                }else{ //固定参数处理：url编码
                    StringBuilder urlBuilder=new StringBuilder(url);
                    if (url.lastIndexOf("?")<0) {
                        urlBuilder.append("?");
                    }
                    params.forEach((key,value)->{
                        urlBuilder.append(key).append("=").append(value).append("&");
                    });
                    url=urlBuilder.substring(0,urlBuilder.length()-1);
                    responseEntity=restTemplate.exchange(url,method,entity,responseType);
                }
            }else{  //无参数调用
                responseEntity=restTemplate.exchange(url,method,entity,responseType);
            }
        }else{ //Post或其他请求处理
            //请求实体：请求参数&请求头
            log.debug("HttpRestTemplate服务调用-POST请求：url：{}/params：{}",url,params);
            HttpEntity<?> entity = new HttpEntity<>(params, httpHeaders);
            responseEntity = restTemplate.exchange(url,method,entity,responseType);
        }
        return resolveResponseEntity(responseEntity);
    }

    /**
     * 创建默认模版对象
     * 2023/10/18 01:25
     * @author pengshuaifeng
     */
    public static RestTemplate createDefaultRestTemplate(){
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setReadTimeout(5000);//单位为ms
        factory.setConnectTimeout(5000);//单位为ms
        return new RestTemplate(factory);
    }

    /**
     * 设置请求头
     * 2023/10/18 00:49
     * @author pengshuaifeng
     */
    public static HttpHeaders setHeaders(Map<String,String> headers){
        HttpHeaders httpHeaders = new HttpHeaders();
        headers.forEach(httpHeaders::add);
        return httpHeaders;
    }

    /**
     * 设置请求头：RestFul类型
     * 2023/10/18 00:49
     * @author pengshuaifeng
     */
    public static HttpHeaders setRestFulHeaders(Map<String,String> headers){
        HttpHeaders httpHeaders = setHeaders(headers);
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return httpHeaders;
    }

    /**
     * 响应解析
     * 2023/10/17 23:47
     * @throws RuntimeException 如果响应状态不为200，则抛出响应信息、响应结果为空，则抛出响应为空异常
     * @author pengshuaifeng
     */
    public static <T> T resolveResponseEntity(ResponseEntity<T> responseEntity){
        if (responseEntity.getStatusCode()== HttpStatus.OK) {   //正常响应
            T body = responseEntity.getBody();
            if(body==null)
                throw new RuntimeException("HttpRestTemplate服务调用异常：没有响应数据");
            log.debug("HttpRestTemplate服务调用-响应结果：{}",body);
            return body;
        }
        //异常响应
        throw new RuntimeException("HttpRestTemplate服务调用失败："+responseEntity.getStatusCode());
    }

}