package cn.syc.servlet;

import cn.syc.service.TestService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class TestServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        TestService service = new TestService();
        String str = "TestServlet.get + " + service.getCustomStr();

        PrintWriter out = response.getWriter();
        out.print(str);
        out.flush();
        out.close();
    }
}
