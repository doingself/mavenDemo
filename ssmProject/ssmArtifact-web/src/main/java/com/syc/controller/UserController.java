package com.syc.controller;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.syc.model.User;
import com.syc.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/user")

//这里用了@SessionAttributes，可以直接把model中的user(也就key)放入其中
//这样保证了session中存在user这个对象
@SessionAttributes("user")

public class UserController {


    protected final static Logger logger = LogManager.getLogger(UserController.class);
    protected Map<String, Object> resultMap = new LinkedHashMap<String, Object>();

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

        Long userId = Long.parseLong(request.getParameter("id"));
        User user = this.userService.getUserById(userId);

        session.setAttribute("user",user);
        session.setAttribute("login","suc");

        // fastjson
        String jsonString = JSONObject.toJSONString(user);
        JSONObject jsonObject = JSONObject.parseObject(jsonString);
        System.out.println(jsonString);
        System.out.println(jsonObject);

        // jackson
        /**
         * ObjectMapper是JSON操作的核心，Jackson的所有JSON操作都是在ObjectMapper中实现。
         * ObjectMapper有多个JSON序列化的方法，可以把JSON字符串保存File、OutputStream等不同的介质中。
         * writeValue(File arg0, Object arg1)把arg1转成json序列，并保存到arg0文件中。
         * writeValue(OutputStream arg0, Object arg1)把arg1转成json序列，并保存到arg0输出流中。
         * writeValueAsBytes(Object arg0)把arg0转成json序列，并把结果输出成字节数组。
         * writeValueAsString(Object arg0)把arg0转成json序列，并把结果输出成字符串。
         */
        ObjectMapper mapper = new ObjectMapper();
        try {
            response.getWriter().write(mapper.writeValueAsString(user));
            response.getWriter().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @RequestMapping("list.do")
    public String toList(HttpServletRequest request,
                         @RequestParam(value="currPage",required=false) Long curr,
                         @RequestParam(value="pageSize",required=false) Long pageSize){
        if(curr==null){
            curr=1L;
        }
        if(pageSize==null){
            pageSize=10L;
        }
        Long currPage = (curr-1)*pageSize;
        Long count = 20l;//userService.selectCount();
        Long totalPage = 0L;
        if(count>0){
            totalPage = count%pageSize==0?count/pageSize:(count/pageSize)+1;
        }
        List<User> adminUserList = userService.findByPage(currPage, pageSize);
        request.setAttribute("adminUserList", adminUserList);
        request.setAttribute("count", count);
        request.setAttribute("totalPage", totalPage);
        request.setAttribute("currPage", curr);
        request.setAttribute("pageSize", pageSize);
        //return "redirect:list.do";
        return "adminUser/list";
    }

    @RequestMapping(value="checkUserName.do",method=RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> checkUserName(HttpServletRequest request,RedirectAttributes attr,
                                             @RequestParam(value="account",required=false) String account){
        User user = new User();
        user.setUsername(account);
        User u = userService.getUserByModel(user);
        if(null==u){
            resultMap.put("status", 200);
            resultMap.put("message", true);
        }else{
            resultMap.put("status", 204);
            resultMap.put("message", false);
        }
        return resultMap;
    }
}
