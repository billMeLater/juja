package ua.com.juja.vadim.sqlcmd.controller.web;

import ua.com.juja.vadim.sqlcmd.service.Service;
import ua.com.juja.vadim.sqlcmd.service.ServiceImpl;

import javax.servlet.http.HttpServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MainServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Service service = new ServiceImpl();

        String requestURI = request.getRequestURI();
        String action = requestURI.substring(request.getContextPath().length(), requestURI.length());

        if (action.equalsIgnoreCase("/menu")) {
            request.getRequestDispatcher("menu.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

}
