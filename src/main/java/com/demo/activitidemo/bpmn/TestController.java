package com.demo.activitidemo.bpmn;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Lee
 * date:2019-06-12
 */
@Controller
public class TestController {

    @ResponseBody
    @RequestMapping("/sayHello")
    public String sayHello(){
        return "hello world";
    }

}
