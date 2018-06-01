/**
 * 
 */
package com.syc.shiro;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.syc.model.User;
import com.syc.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

public class RememberMeFilter extends FormAuthenticationFilter {
	protected final static Logger logger = LogManager.getLogger(RememberMeFilter.class);
	
	@Resource
    private UserService userService;

	@Override
	protected boolean isAccessAllowed(ServletRequest request,ServletResponse response,Object mappedValue){
		Subject subject = getSubject(request,response);
		Session session = subject.getSession();
		/**满足三个条件：选择记住我,非密码登录,session为空*/
		if(!subject.isAuthenticated() && subject.isRemembered() && session.getAttribute("user") == null){
			Object principal = subject.getPrincipal();
			if(principal!=null){
				User user = new User();
				user.setPassword(principal.toString());
				User u = userService.getUserByModel(user);
				session.setAttribute("user", u.getUsername());
			}
		}
		return subject.isAuthenticated()||subject.isRemembered();
	}
	
	
}
