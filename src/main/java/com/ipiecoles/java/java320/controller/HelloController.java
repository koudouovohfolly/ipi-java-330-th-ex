package com.ipiecoles.java.java320.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
@Controller
public class HelloController {

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/sayHello"
    )
    public String hello(final ModelMap model){
        model.put("nom", "IPI");
        return "hello";
    }

}