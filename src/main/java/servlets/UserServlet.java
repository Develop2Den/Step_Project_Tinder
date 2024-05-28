package servlets;

import DAO.DAOinterfaceImpl.LikedDAOImpl;
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
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class UserServlet extends HttpServlet {

    private UserDAOImpl userDAO;
    private LikedDAOImpl likedDAO;
    private Configuration cfg;

    public UserServlet(UserDAOImpl userDAO, LikedDAOImpl likedDAO, Configuration cfg) {
        this.userDAO = userDAO;
        this.likedDAO = likedDAO;
        this.cfg = cfg;
    }

    private List<User> getFilteredProfiles(Integer currentUserId, List<User> likedProfiles, List<User> dislikedProfiles, List<User> allProfiles) {
        return allProfiles.stream()
                .filter(user -> likedProfiles.stream().noneMatch(liked -> liked.getId() == user.getId()) &&
                        dislikedProfiles.stream().noneMatch(disliked -> disliked.getId() == user.getId()) &&
                        user.getId() != currentUserId)
                .collect(Collectors.toList());
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
        List<User> dislikedProfiles;
        try {
            likedProfiles = likedDAO.getLikedProfiles(currentUserId);
            dislikedProfiles = likedDAO.getDislikedProfiles(currentUserId);
        } catch (SQLException e) {
            throw new ServletException("Unable to retrieve liked or disliked profiles", e);
        }

        List<User> allProfiles;
        try {
            allProfiles = userDAO.getAll();
        } catch (SQLException e) {
            throw new ServletException("Unable to retrieve profiles", e);
        }

        List<User> filteredProfiles = getFilteredProfiles(currentUserId, likedProfiles, dislikedProfiles, allProfiles);

        if (filteredProfiles.isEmpty()) {
            resp.sendRedirect("/liked");
            return;
        }

        User profile = filteredProfiles.get(0);

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
        List<User> dislikedProfiles;
        try {
            likedProfiles = likedDAO.getLikedProfiles(currentUserId);
            dislikedProfiles = likedDAO.getDislikedProfiles(currentUserId);
        } catch (SQLException e) {
            throw new ServletException("Unable to retrieve liked or disliked profiles", e);
        }

        List<User> allProfiles;
        try {
            allProfiles = userDAO.getAll();
        } catch (SQLException e) {
            throw new ServletException("Unable to retrieve profiles", e);
        }

        List<User> filteredProfiles = getFilteredProfiles(currentUserId, likedProfiles, dislikedProfiles, allProfiles);

        if (filteredProfiles.isEmpty()) {
            resp.sendRedirect("/liked");
            return;
        }

        User currentProfile = filteredProfiles.get(0);

        String choice = req.getParameter("action");
        try {
            if ("like".equalsIgnoreCase(choice)) {
                likedDAO.likeProfile(currentUserId, currentProfile.getId());
            } else if ("dislike".equalsIgnoreCase(choice)) {
                likedDAO.dislikeProfile(currentUserId, currentProfile.getId());
            }
        } catch (SQLException e) {
            throw new ServletException("Unable to update profile", e);
        }

        try {
            likedProfiles = likedDAO.getLikedProfiles(currentUserId);
            dislikedProfiles = likedDAO.getDislikedProfiles(currentUserId);
        } catch (SQLException e) {
            throw new ServletException("Unable to retrieve liked or disliked profiles", e);
        }

        filteredProfiles = getFilteredProfiles(currentUserId, likedProfiles, dislikedProfiles, allProfiles);

        if (filteredProfiles.isEmpty()) {
            resp.sendRedirect("/liked");
        } else {
            resp.sendRedirect("/users");
        }
    }
}