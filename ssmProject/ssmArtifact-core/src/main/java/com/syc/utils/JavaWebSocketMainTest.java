package com.syc.utils;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.Collection;

public class JavaWebSocketMainTest extends WebSocketServer{

    public static void main(String[] args) throws Exception{

        JavaWebSocketMainTest server = new JavaWebSocketMainTest(2018);
        server.start();

        String ip = InetAddress.getLocalHost().getHostAddress();
        int port = server.getPort();
        System.out.println(String.format("服务启动 %s:%d", ip, port));

        InputStreamReader in = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(in);

        while (true){
            String msg = reader.readLine();
            server.sendToAll(msg);
        }
    }

    public JavaWebSocketMainTest(int port) throws UnknownHostException{
        super(new InetSocketAddress(port));
    }

    private void sendToAll(String message){
        Collection<WebSocket> connections = connections();
        for(WebSocket socket : connections){
            socket.send(message);
        }
    }

    @Override
    public void onOpen(WebSocket socket, ClientHandshake clientHandshake){
        String address = socket.getRemoteSocketAddress().getAddress().getHostAddress();
        String message = String.format("%s 进入", address);
        sendToAll(message);
        System.out.println(message);
    }

    @Override
    public void onClose(WebSocket socket, int num, String str, boolean var4){
        String address = socket.getRemoteSocketAddress().getAddress().getHostAddress();
        String message = String.format("%s 退出 --- %d %s", address, num, str);
        sendToAll(message);
        System.out.println(message);
    }

    @Override
    public void onMessage(WebSocket socket, String str){
        String address = socket.getRemoteSocketAddress().getAddress().getHostAddress();
        String message = String.format("%s --- %s", address, str);
        sendToAll(message);
        System.out.println(message);
    }

    @Override
    public void onError(WebSocket socket, Exception ex){
        if (socket != null){
            socket.close(0);
        }
        ex.printStackTrace();
    }
}
