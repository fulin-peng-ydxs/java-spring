package web.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collections;
import java.util.Map;

/**
 * @author PengFuLin
 * @version 1.0
 * @description:
 * @date 2021/11/27 23:34
 */
@Controller
public class DemoController {

    @RequestMapping(value = "/requestBodyTest",method = RequestMethod.POST)
    public @ResponseBody
    Map<String,Object> requestBodyTest(@RequestBody  Object requestBodyParams){
        return Collections.singletonMap("test",requestBodyParams);
    }

    @RequestMapping(value = "/demo",method = RequestMethod.POST)
    public  String demo( String demo){
        System.out.println(demo);
        return "index";
    }
}
