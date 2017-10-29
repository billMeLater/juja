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

        String action = getAction(request);

        if (action.equalsIgnoreCase("/menu")) {
            request.getRequestDispatcher("menu.jsp").forward(request, response);
        } else if (action.equalsIgnoreCase("/connect")) {
            request.getRequestDispatcher("connect.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        Service service = new ServiceImpl();
        String action = getAction(request);

        if (action.equalsIgnoreCase("/connect")) {
            String dbName = request.getParameter("dbName");
            String dbUser = request.getParameter("dbUser");
            String dbPass = request.getParameter("dbPass");

            try {
                service.connect(dbName, dbUser, dbPass);
            } catch (Exception e) {
                throw e;
            }
        } else {
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    private String getAction(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        return requestURI.substring(request.getContextPath().length(), requestURI.length());
    }

}
