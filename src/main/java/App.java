import DAO.DAOinterfaceImpl.UserDAOImpl;
import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import servlets.*;

public class App {

    public static void main(String[] args) throws Exception {

        Server server = new Server(8080);

        Configuration cfg = new Configuration(Configuration.VERSION_2_3_30);
        cfg.setClassForTemplateLoading(App.class, "/templates");
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setLogTemplateExceptions(false);
        cfg.setWrapUncheckedExceptions(true);

        ServletContextHandler handler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        handler.setContextPath("/");

        UserDAOImpl userDAO = new UserDAOImpl();

        handler.addServlet(new ServletHolder(new LoginServlet(userDAO, cfg)), "/login");
        handler.addServlet(new ServletHolder(new UserServlet(userDAO, cfg)), "/users");
        handler.addServlet(new ServletHolder(new LogoutServlet()), "/logout");
        handler.addServlet(new ServletHolder(new LikedProfilesServlet(userDAO, cfg)), "/liked");
        handler.addServlet(new ServletHolder(new MessagesServlet(userDAO, cfg)), "/messages/*");

        handler.addServlet(new ServletHolder(new ContentServlet("static")), "/static/*");

        server.setHandler(handler);
        server.start();
        server.join();
    }
}
