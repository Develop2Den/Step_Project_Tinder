package servlets;

import DAO.DAOinterfaceImpl.LikedDAOImpl;
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
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LikedProfilesServlet extends HttpServlet {

    private LikedDAOImpl likedDAO;
    private Configuration cfg;

    public LikedProfilesServlet(LikedDAOImpl likedDAO, Configuration cfg) {
        this.likedDAO = likedDAO;
        this.cfg = cfg;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        int currentUserId = (int) session.getAttribute("userId");

        List<User> likedProfiles;
        try {
            likedProfiles = likedDAO.getLikedProfiles(currentUserId);
        } catch (SQLException e) {
            throw new ServletException("Unable to retrieve liked profiles", e);
        }

        Map<String, Object> model = new HashMap<>();
        model.put("likedProfiles", likedProfiles);

        Template template = cfg.getTemplate("liked.ftl");

        StringWriter writer = new StringWriter();
        try {
            template.process(model, writer);
        } catch (TemplateException e) {
            throw new ServletException(e);
        }

        resp.setContentType("text/html");
        resp.getWriter().write(writer.toString());
    }
}