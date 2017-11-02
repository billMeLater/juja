package ua.com.juja.vadim.sqlcmd.controller.web;

import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import ua.com.juja.vadim.sqlcmd.controller.command.CommandOutput;
import ua.com.juja.vadim.sqlcmd.model.DatabaseManager;
import ua.com.juja.vadim.sqlcmd.model.MySQLDatabaseManager;
import ua.com.juja.vadim.sqlcmd.service.Service;
import ua.com.juja.vadim.sqlcmd.service.ServiceImpl;
import ua.com.juja.vadim.sqlcmd.view.Console;
import ua.com.juja.vadim.sqlcmd.view.View;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class MainServlet extends HttpServlet {
    DatabaseManager databaseManager = new MySQLDatabaseManager();

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = getAction(request);

        if (action.equalsIgnoreCase("/")) {
            request.getRequestDispatcher("main.jsp").forward(request, response);
        } else if (action.equalsIgnoreCase("/connect")) {
            request.getRequestDispatcher("connect.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        View view = new Console();
        Service service = new ServiceImpl();
        String action = getAction(request);
        List<String> params = new ArrayList<>();

        if (action.equalsIgnoreCase("/connect")) {
            params.add(request.getParameter("dbName"));
            params.add(request.getParameter("dbUser"));
            params.add(request.getParameter("dbPass"));

            try {
                view.write(service.connect(databaseManager, params));
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
