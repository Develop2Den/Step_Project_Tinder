package servlets;

import DAO.DAOinterfaceImpl.UserDAOImpl;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

public class LoginServlet extends HttpServlet {

    private static final String HARDCODED_PASSWORD = "12345";
    private Configuration cfg;
    private UserDAOImpl userDAO;

    public LoginServlet(UserDAOImpl userDAO, Configuration cfg) {
        this.cfg = cfg;
        this.userDAO = userDAO;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("username") != null) {
            response.sendRedirect(request.getContextPath() + "/users");
        } else {
            Template template = cfg.getTemplate("login.html");
            try {
                template.process(null, response.getWriter());
            } catch (TemplateException e) {
                throw new ServletException("Error while processing Freemarker template", e);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (password.equals(HARDCODED_PASSWORD)) {
            HttpSession session = request.getSession();
            session.setAttribute("username", username);
            try {
                int userId = userDAO.getUserIdByUsername(username);
                session.setAttribute("userId", userId);
                response.sendRedirect(request.getContextPath() + "/users");
            } catch (SQLException e) {
                throw new ServletException("Unable to retrieve user ID", e);
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/login?error=1");
        }
    }
}
