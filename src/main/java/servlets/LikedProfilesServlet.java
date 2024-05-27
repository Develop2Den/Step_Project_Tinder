package servlets;

import DAO.DAOinterfaceImpl.UserDAOImpl;
import classes.User;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LikedProfilesServlet extends HttpServlet {

    private UserDAOImpl userDAO;
    private Configuration cfg;

    public LikedProfilesServlet(UserDAOImpl userDAO, Configuration cfg) {
        this.userDAO = userDAO;
        this.cfg = cfg;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String username = (String) session.getAttribute("username");
        int currentUserId;
        try {
            currentUserId = userDAO.getUserIdByUsername(username);
            if (currentUserId == -1) {
                throw new ServletException("User not found");
            }
        } catch (SQLException e) {
            throw new ServletException("Database error", e);
        }

        List<User> likedProfiles;
        try {
            likedProfiles = userDAO.getLikedProfiles(currentUserId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Map<String, Object> data = new HashMap<>();
        data.put("likedProfiles", likedProfiles);

        resp.setContentType("text/html;charset=UTF-8");

        try {
            Template template = cfg.getTemplate("liked.ftl");
            try (Writer out = new OutputStreamWriter(resp.getOutputStream())) {
                template.process(data, out);
            }
        } catch (TemplateException e) {
            throw new ServletException("Error while processing Freemarker template", e);
        }
    }
}
