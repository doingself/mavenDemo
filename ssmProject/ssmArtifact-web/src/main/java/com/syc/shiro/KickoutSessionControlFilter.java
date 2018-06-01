/**
 * 
 */
package com.syc.shiro;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.syc.redis.JedisUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

public class KickoutSessionControlFilter extends AccessControlFilter {

	protected final static Logger logger = LogManager.getLogger(KickoutSessionControlFilter.class);
	//踢出状态，true标示踢出
	final static String KICKOUT_STATUS = KickoutSessionControlFilter.class.getCanonicalName()+ "_kickout_status";	
	
	@Override
	protected boolean isAccessAllowed(ServletRequest request,
			ServletResponse response, Object mappedValue) throws Exception {
		
		HttpServletRequest httpRequest = ((HttpServletRequest)request);
		String url = httpRequest.getRequestURI();
		Subject subject = getSubject(request, response);
		//如果是相关目录 or 如果没有登录 就直接return true
		if(url.startsWith("/open/") || (!subject.isAuthenticated() && !subject.isRemembered())){
			return Boolean.TRUE;
		}
		Session session = subject.getSession();
		Serializable sessionId = session.getId();
		/**
		 * 判断是否已经踢出
		 * 1.如果是Ajax 访问，那么给予json返回值提示。
		 * 2.如果是普通请求，直接跳转到登录页
		 */
		Boolean marker = (Boolean)session.getAttribute(KICKOUT_STATUS);
		if (null != marker && marker ) {
			Map<String, String> resultMap = new HashMap<String, String>();
			//判断是不是Ajax请求
			if (ShiroUtils.isAjax(request) ) {
				logger.debug("当前用户已经在其他地方登录，并且是Ajax请求！");
				resultMap.put("user_status", "300");
				resultMap.put("message", "您已经在其他地方登录，请重新登录！");
				ShiroUtils.out(response, resultMap);
			}
			return  Boolean.FALSE;
		}
		
		
		
		//获取用户账号
		String userName = (String)subject.getPrincipal();
		
		//从缓存获取用户-Session信息 <userName,SessionId>
		String jedisSessionId = JedisUtils.get(userName);
		
		//如果已经包含当前Session，并且是同一个用户，跳过。
		if(null!=jedisSessionId && jedisSessionId.equals((String)sessionId)){
			//更新存储到缓存1个小时（这个时间最好和session的有效期一致或者大于session的有效期）
			JedisUtils.setex(userName,3600, (String)sessionId);
			return Boolean.TRUE;
		}
		//如果用户相同，Session不相同，那么就要处理了
		/**
		 * 如果用户Id相同,Session不相同
		 * 1.获取到原来的session，并且标记为踢出。
		 * 2.继续走
		 */
		if(null!=jedisSessionId && !jedisSessionId.equals((String)sessionId)){
			//标记session已经踢出
			session.setAttribute(KICKOUT_STATUS, Boolean.TRUE);
			//存储到缓存1个小时（这个时间最好和session的有效期一致或者大于session的有效期）
			JedisUtils.setex(userName, 3600, (String)sessionId);
			return  Boolean.TRUE;
		}
		
		if(null==jedisSessionId){
			//存储到缓存1个小时（这个时间最好和session的有效期一致或者大于session的有效期）
			JedisUtils.setex(userName, 3600, (String)sessionId);
		}
		return Boolean.TRUE;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request,
			ServletResponse response) throws Exception {
		
		//先退出,这一步才是正常清除Session
		Subject subject = getSubject(request, response);
		subject.logout();
		WebUtils.getSavedRequest(request);
		//再重定向
		WebUtils.issueRedirect(request, response,"/login");
		return false;
	}	
	
	
	
}
