package com.syc.controller;


import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.syc.model.User;
import com.syc.redis.JedisUtils;
import com.syc.service.UserService;
import com.syc.shiro.ShiroUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * @ClassName: LoginController
 * @Description: 登录
 */
@Controller
public class LoginController{
    protected final static Logger logger = LogManager.getLogger(LoginController.class);
    protected Map<String, Object> resultMap = new LinkedHashMap<String, Object>();

    @Resource
    UserService userService;


    /**
     * @Description: 登录认证
     */
    @RequestMapping(value="/submitLogin",method=RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> submitLogin(String um,String pw,boolean rememberMe,HttpServletRequest request){
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(um,ShiroUtils.getStrByMD5(pw));
        try{
            token.setRememberMe(rememberMe);
            subject.login(token);
            JedisUtils.del(um);
            logger.info("------------------身份认证成功-------------------");
            resultMap.put("status", 200);
            resultMap.put("message", "登录成功！");
        } catch (DisabledAccountException dax) {
            logger.info("用户名为:" + um + " 用户已经被禁用！");
            resultMap.put("status", 500);
            resultMap.put("message", "帐号已被禁用！");
        } catch (ExcessiveAttemptsException eae) {
            logger.info("用户名为:" + um + " 用户登录次数过多，有暴力破解的嫌疑！");
            resultMap.put("status", 500);
            resultMap.put("message", "登录次数过多！");
        } catch (AccountException ae) {
            logger.info("用户名为:" + token.getPrincipal() + " 帐号或密码错误！");
            String excessiveInfo = ExcessiveAttemptsInfo(um);
            if(null!=excessiveInfo){
                resultMap.put("status", 500);
                resultMap.put("message", excessiveInfo);
            }else{
                resultMap.put("status", 500);
                resultMap.put("message", "帐号或密码错误！");
            }
        } catch (AuthenticationException ae) {
            logger.error(ae);
            logger.info("------------------身份认证失败-------------------");
            resultMap.put("status", 500);
            resultMap.put("message", "身份认证失败！");
        } catch (Exception e) {
            logger.error(e);
            logger.info("未知异常信息。。。。");
            resultMap.put("status", 500);
            resultMap.put("message", "登录认证错误！");
        }
        return resultMap;
    }


    /**
     * @Description: 退出
     */
    @RequestMapping(value="logout",method=RequestMethod.GET)
    public String logout(){
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.logout();
        } catch (Exception e) {
            logger.error("errorMessage:" + e.getMessage());
        }
        return "redirect:login";
    }


    /**
     * @Description: 验证器，增加了登录次数校验功能
     */
    public String ExcessiveAttemptsInfo(String account){
        String excessiveInfo = null;
        StringBuffer userName = new StringBuffer(account);
        userName.append("ExcessiveCount");
        String accountKey = userName.toString();

        if(null == JedisUtils.get(accountKey)){
            JedisUtils.setex(accountKey, 1800, "1");
        }else{
            int count = Integer.parseInt(JedisUtils.get(accountKey))+1;
            JedisUtils.setex(accountKey, 1800-(120*count), Integer.toString(count));
        }
        /**登录错误5次,发出警告*/
        if(Integer.parseInt(JedisUtils.get(accountKey))==5){
            excessiveInfo = "账号密码错误5次,再错5次账号将被禁用！";
        }
        /**登录错误10次,该账号将被禁用*/
        if(Integer.parseInt(JedisUtils.get(accountKey))==10){
            User user = new User();
            user.setUsername(account);
            User u = userService.getUserByModel(user);
            if(null!=u){
                // FIXME: 禁用
            }
            JedisUtils.del(account);
            excessiveInfo = "账号密码错误过多,账号已被禁用！";
        }
        return excessiveInfo;
    }
}
