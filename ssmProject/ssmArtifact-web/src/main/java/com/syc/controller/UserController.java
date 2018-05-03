package com.syc.controller;

import com.syc.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;


@Controller
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @RequestMapping("/select")
    @ResponseBody
    public String selectUser(){
        String result = userService.getHaha();
        return "usercon.selectu" + result;
    }

}
