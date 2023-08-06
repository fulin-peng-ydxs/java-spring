package web.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import web.mvc.model.Start;
/**
 * @author PengFuLin
 * @version 1.0
 * @description:
 * @date 2021/11/27 23:34
 */

@Controller
public class Demo {

    @RequestMapping(value = "/requestBodyTest",method = RequestMethod.POST)
    public @ResponseBody
    Start requestBodyTest(@RequestBody  String requestBodyParams){
        System.out.println("fafa");
        return new Start();
    }

    @RequestMapping(value = "/demo",method = RequestMethod.POST)
    public  String demo( String demo){
        System.out.println("fafa");
        return "index";
    }
}
