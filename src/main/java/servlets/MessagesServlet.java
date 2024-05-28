package servlets;

<<<<<<< HEAD
import DAO.DAOinterfaceImpl.MessageDAOImpl;
=======
>>>>>>> 70f05fe2cb6f9502a01e94e9429cffd043315f07
import DAO.DAOinterfaceImpl.UserDAOImpl;
import classes.Message;
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

public class MessagesServlet extends HttpServlet {

<<<<<<< HEAD
    private MessageDAOImpl messageDAO;
    private UserDAOImpl userDAO;
    private Configuration cfg;

    public MessagesServlet(MessageDAOImpl messageDAO, UserDAOImpl userDAO, Configuration cfg) {
        this.messageDAO = messageDAO;
=======
    private UserDAOImpl userDAO;
    private Configuration cfg;

    public MessagesServlet(UserDAOImpl userDAO, Configuration cfg) {
>>>>>>> 70f05fe2cb6f9502a01e94e9429cffd043315f07
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

        int currentUserId = userDAO.getCurrentUserIdFromSession(session);
        if (currentUserId == -1) {
            throw new ServletException("User not found");
        }

        String userIdPath = req.getPathInfo();
        if (userIdPath == null || userIdPath.length() <= 1) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "User ID is required");
            return;
        }

        int userId;
        try {
            userId = Integer.parseInt(userIdPath.substring(1)); // Получаем ID пользователя из URL
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid user ID format");
            return;
        }

        List<Message> messages;
        User user;
        try {
<<<<<<< HEAD
            messages = messageDAO.getMessagesBetweenUsers(currentUserId, userId);
=======
            messages = userDAO.getMessages(currentUserId, userId);
>>>>>>> 70f05fe2cb6f9502a01e94e9429cffd043315f07
            user = userDAO.getById(userId);
        } catch (SQLException e) {
            throw new ServletException(e);
        }

        // Генерация HTML-страницы
        Map<String, Object> model = new HashMap<>();
        model.put("currentUserId", currentUserId);
        model.put("messages", messages);
        model.put("userProfile", user);

        Template template = cfg.getTemplate("chat.ftl");

        StringWriter writer = new StringWriter();
        try {
            template.process(model, writer);
        } catch (TemplateException e) {
            throw new ServletException(e);
        }

        resp.setContentType("text/html");
        resp.getWriter().write(writer.toString());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        int currentUserId = userDAO.getCurrentUserIdFromSession(session);
        if (currentUserId == -1) {
            throw new ServletException("User not found");
        }

        String userIdPath = req.getPathInfo();
        if (userIdPath == null || userIdPath.length() <= 1) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "User ID is required");
            return;
        }

        int userId;
        try {
            userId = Integer.parseInt(userIdPath.substring(1)); // Получаем ID пользователя из URL
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid user ID format");
            return;
        }

        String content = req.getParameter("content");
        if (content == null || content.isEmpty()) {
            content = "Empty message!";
        }

        try {
<<<<<<< HEAD
            messageDAO.saveMessage(currentUserId, userId, content);
=======
            userDAO.saveMessage(currentUserId, userId, content);
>>>>>>> 70f05fe2cb6f9502a01e94e9429cffd043315f07
            resp.sendRedirect(req.getRequestURI());
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
