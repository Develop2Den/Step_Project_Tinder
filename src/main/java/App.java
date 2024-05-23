import DAO.DAOinterface.MessageDAO;
import DAO.DAOinterfaceImpl.CollectionMessageDAO;
import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import services.MessagesService;
import servlets.ContentServlet;
import servlets.TestServlet;
import servlets.MessagesServlet;

import javax.servlet.http.HttpServlet;

public class App 
{
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

        MessageDAO messageDAO = new CollectionMessageDAO();
        MessagesService messagesService = new MessagesService(messageDAO);

        handler.addServlet(TestServlet.class,"/users");
        handler.addServlet(new ServletHolder(new MessagesServlet(messagesService,cfg)), "/messages/*");

        HttpServlet scc = new ContentServlet("static");
        handler.addServlet(new ServletHolder(scc), "/static/*");

        server.setHandler(handler);
        server.start();
        server.join();
    }
}
