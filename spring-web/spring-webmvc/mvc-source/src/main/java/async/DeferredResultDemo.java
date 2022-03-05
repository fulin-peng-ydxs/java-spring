package async;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.UUID;

/**
 * @author PengFuLin
 * @version 1.0
 * @description: 异步请求-DeferredResult返回
 * @date 2021/11/22 11:44
 */
@Controller
public class DeferredResultDemo {

    @ResponseBody
    @RequestMapping("/createOrder")
    public DeferredResult<Object> createOrder(){
        //设置异步请求
        DeferredResult<Object> deferredResult = new DeferredResult<>((long)3000, "create fail...");
        DeferredResultQueue.save(deferredResult);
        return deferredResult;
    }

    @ResponseBody
    @RequestMapping("/create")
    public String create(){
        //创建订单
        String order = UUID.randomUUID().toString();
        DeferredResult<Object> deferredResult = DeferredResultQueue.get();
        //当deferredResult被调用setResult()写入结果时，完成异步请求，
        // 重新恢复 /createOrder请求完成响应。
        deferredResult.setResult(order);
        return "success===>"+order;
    }
}
