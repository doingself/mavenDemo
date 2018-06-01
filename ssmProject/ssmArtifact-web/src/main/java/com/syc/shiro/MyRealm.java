/**
 * 
 */
package com.syc.shiro;


import javax.annotation.Resource;

import com.syc.model.User;
import com.syc.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.ByteSource;

public class MyRealm extends AuthorizingRealm {

	@Resource
	UserService userService;

	/* (non-Javadoc)
	 * @see org.apache.shiro.realm.AuthorizingRealm#doGetAuthorizationInfo(org.apache.shiro.subject.PrincipalCollection)
	 */
	/**
	 * 授权Realm
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		String account = (String)principals.getPrimaryPrincipal();
		User user = new User();
		user.setUsername(account);
		Long userId = userService.getUserByModel(user).getId();
		SimpleAuthorizationInfo info =  new SimpleAuthorizationInfo();
		/**根据用户ID查询角色（role），放入到Authorization里.*/
		info.setRoles(userService.findRoleByUserId(userId));
		/**根据用户ID查询权限（permission），放入到Authorization里.*/
		info.setStringPermissions(userService.findPermissionByUserId(userId));
        return info; 
	}

	/* (non-Javadoc)
	 * @see org.apache.shiro.realm.AuthenticatingRealm#doGetAuthenticationInfo(org.apache.shiro.authc.AuthenticationToken)
	 */
	/**
	 * 登录认证Realm
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) {
		String username = (String)token.getPrincipal();
		String password = new String((char[])token.getCredentials());
		User user = userService.login(username, password);
		if(null==user){
			throw new AccountException("帐号或密码不正确！");
		}
		if(user.getIsDisabled()){
			throw new DisabledAccountException("帐号已经禁止登录！");
		}
		//**密码加盐**交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配
		return new SimpleAuthenticationInfo(user.getUsername(),user.getPassword(),ByteSource.Util.bytes("3.14159"), getName());
	}

	
    /**
     * 清空当前用户权限信息
     */
	public  void clearCachedAuthorizationInfo() {
		PrincipalCollection principalCollection = SecurityUtils.getSubject().getPrincipals();
		SimplePrincipalCollection principals = new SimplePrincipalCollection(
				principalCollection, getName());
		super.clearCachedAuthorizationInfo(principals);
	}
	/**
	 * 指定principalCollection 清除
	 */
	public void clearCachedAuthorizationInfo(PrincipalCollection principalCollection) {
		SimplePrincipalCollection principals = new SimplePrincipalCollection(
				principalCollection, getName());
		super.clearCachedAuthorizationInfo(principals);
	}
}
