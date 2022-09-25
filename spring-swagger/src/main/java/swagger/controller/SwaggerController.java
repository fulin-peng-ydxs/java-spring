package swagger.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * swagger≤‚ ‘
 *
 * @author PengFuLin
 * 2022/9/24 23:09
 */
@Controller
@Api(tags = "swagger≤‚ ‘")
public class SwaggerController {

    @Autowired
    private ApplicationContext applicationContext;

    @ApiOperation("≤‚ ‘Ω”ø⁄")
    @ResponseBody
    @RequestMapping(value = "test",method = RequestMethod.GET)
    public String test(){
        return "≤‚ ‘";
    }

    @ApiOperation("IOC≤‚ ‘Ω”ø⁄")
    @RequestMapping(value = "ioc",method = RequestMethod.GET)
    public void ioc(){
        System.out.println(applicationContext);
    }
}
