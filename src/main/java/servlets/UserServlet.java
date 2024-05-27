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
import java.util.stream.Collectors;

public class UserServlet extends HttpServlet {

    private UserDAOImpl userDAO;
    private Configuration cfg;

    public UserServlet(UserDAOImpl userDAO, Configuration cfg) {
        this.userDAO = userDAO;
        this.cfg = cfg;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String username = (String) session.getAttribute("username");

        if (username == null) {
            resp.sendRedirect("/login");
            return;
        }

        Integer currentUserId = (Integer) session.getAttribute("userId");

        List<User> likedProfiles;
        try {
            likedProfiles = userDAO.getLikedProfiles(currentUserId);
        } catch (SQLException e) {
            throw new ServletException("Unable to retrieve liked profiles", e);
        }

        List<User> allProfiles;
        try {
            allProfiles = userDAO.getAll();
        } catch (SQLException e) {
            throw new ServletException("Unable to retrieve profiles", e);
        }

        // Фильтрация профилей: исключаем лайкнутых пользователей и самого себя
        List<User> filteredProfiles = allProfiles.stream()
                .filter(user -> likedProfiles.stream().noneMatch(liked -> liked.getId() == user.getId()) && user.getId() != currentUserId)
                .collect(Collectors.toList());

        Integer profileIndex = (Integer) session.getAttribute("profileIndex");
        if (profileIndex == null) {
            profileIndex = 0;
        }

        if (profileIndex >= filteredProfiles.size()) {
            resp.sendRedirect("/liked");
            return;
        }

        User profile = filteredProfiles.get(profileIndex);

        Map<String, Object> data = new HashMap<>();
        data.put("user", profile);
        data.put("currentUser", new User(currentUserId, username, ""));

        resp.setContentType("text/html;charset=UTF-8");

        try {
            Template template = cfg.getTemplate("like-page.ftl");
            try (Writer out = new OutputStreamWriter(resp.getOutputStream())) {
                template.process(data, out);
            }
        } catch (TemplateException e) {
            throw new ServletException("Error while processing Freemarker template", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Integer currentUserId = (Integer) session.getAttribute("userId");

        List<User> likedProfiles;
        try {
            likedProfiles = userDAO.getLikedProfiles(currentUserId);
        } catch (SQLException e) {
            throw new ServletException("Unable to retrieve liked profiles", e);
        }

        List<User> allProfiles;
        try {
            allProfiles = userDAO.getAll();
        } catch (SQLException e) {
            throw new ServletException("Unable to retrieve profiles", e);
        }

        // Фильтрация профилей: исключаем лайкнутых пользователей и самого себя
        List<User> filteredProfiles = allProfiles.stream()
                .filter(user -> likedProfiles.stream().noneMatch(liked -> liked.getId() == user.getId()) && user.getId() != currentUserId)
                .collect(Collectors.toList());

        Integer profileIndex = (Integer) session.getAttribute("profileIndex");
        if (profileIndex == null) {
            profileIndex = 0;
        }

        if (profileIndex >= filteredProfiles.size()) {
            resp.sendRedirect("/liked");
            return;
        }

        User currentProfile = filteredProfiles.get(profileIndex);

        String choice = req.getParameter("action");
        if ("like".equalsIgnoreCase(choice)) {
            try {
                userDAO.likeProfile(currentUserId, currentProfile.getId());
            } catch (SQLException e) {
                throw new ServletException("Unable to like profile", e);
            }
        }

        profileIndex++;
        session.setAttribute("profileIndex", profileIndex);

        if (profileIndex >= filteredProfiles.size()) {
            resp.sendRedirect("/liked");
        } else {
            resp.sendRedirect("/users");
        }
    }
}
