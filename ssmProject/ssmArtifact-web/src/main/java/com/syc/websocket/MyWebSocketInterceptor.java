package com.syc.websocket;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

//public class HttpSessionHandshakeInterceptor implements HandshakeInterceptor {

public class MyWebSocketInterceptor extends HttpSessionHandshakeInterceptor{

    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {

        // 将ServerHttpRequest转换成request请求相关的类，用来获取request域中的用户信息
        if (request instanceof ServletServerHttpRequest){
            ServletServerHttpRequest servletServerHttpRequest = (ServletServerHttpRequest) request;
            HttpSession httpSession = servletServerHttpRequest.getServletRequest().getSession(false);
            if (httpSession != null){
                //使用userName区分WebSocketHandler，以便定向发送消息
                String userName = (String) httpSession.getAttribute("SESSION_USERNAME");
                if (userName == null) {
                    userName="default-system";
                }
                attributes.put("WEBSOCKET_USERNAME",userName);
            }
        }
        System.out.println("连接成功");
        return super.beforeHandshake(request, response, wsHandler, attributes);
    }

    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception ex) {
        super.afterHandshake(request, response, wsHandler, ex);
    }
}

