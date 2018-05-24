package com.syc.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.syc.model.User;
import com.syc.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
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

    @Resource(name="userService")
    private UserService userService;

    @RequestMapping("/haha")
    @ResponseBody
    public String haha(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(true);
        String attribute = (String) session.getAttribute("login");


        String path = request.getContextPath();
        String basePath = request.getScheme() + "://"
                + request.getServerName() + ":" + request.getServerPort()
                + path + "/";

        StringBuilder result = new StringBuilder();

        if (StringUtils.isEmpty(attribute) == false){
            User user = (User) session.getAttribute("user");
            result.append(user.getName());
            result.append(" --- ");
            result.append(attribute);
        } else {
            result.append("usercontroller haha() no login");
            result.append(userService.getHaha());
        }
        result.append(" --- ");
        result.append(path);
        result.append(" --- ");
        result.append(basePath);
        return result.toString();
    }

    @RequestMapping("login")
    public String login(){
        return "login";
    }

    @RequestMapping("loginOut")
    public String loginOut(HttpSession session){
        session.removeAttribute("login");
        session.removeAttribute("user");
        //通过session.invalidata()方法来注销当前的session
        session.invalidate();
        return "loginOut";
    }

    @RequestMapping(value="/get",method= RequestMethod.GET)
    public void selectUser(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        response.setCharacterEncoding("UTF-8");

        //true是表示如果没有则新建一个session
        HttpSession session = request.getSession(true);

        int userId = Integer.parseInt(request.getParameter("id"));
        User user = this.userService.getUserById(userId);

        session.setAttribute("user",user);
        session.setAttribute("login","suc");

        ObjectMapper mapper = new ObjectMapper();
        try {
            response.getWriter().write(mapper.writeValueAsString(user));
            response.getWriter().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
