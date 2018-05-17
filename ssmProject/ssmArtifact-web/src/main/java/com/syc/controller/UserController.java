package com.syc.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.syc.model.User;
import com.syc.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.annotation.Resource;
import javax.json.Json;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;


@Controller
@RequestMapping("/user")

//这里用了@SessionAttributes，可以直接把model中的user(也就key)放入其中
//这样保证了session中存在user这个对象
@SessionAttributes("user")

public class UserController {

    @Resource
    private UserService userService;

    @RequestMapping("/haha")
    @ResponseBody
    public String haha(){
        String result = userService.getHaha();
        return "usercon.selectu" + result;
    }

    @RequestMapping("loginOut")
    public String loginOut(HttpSession session){
        //通过session.invalidata()方法来注销当前的session
        session.invalidate();
        return "login";
    }

    @RequestMapping(value="/get",method= RequestMethod.GET)
    public void selectUser(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        response.setCharacterEncoding("UTF-8");
        int userId = Integer.parseInt(request.getParameter("id"));
        User user = this.userService.getUserById(userId);
        ObjectMapper mapper = new ObjectMapper();
        try {
            response.getWriter().write(mapper.writeValueAsString(user));
            response.getWriter().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
