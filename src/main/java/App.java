import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import servlets.ContentServlet;
import servlets.TestServlet;
import servlets.MessagesServlet;

import javax.servlet.http.HttpServlet;

public class App 
{
    public static void main(String[] args) throws Exception {
        Server server = new Server(5432);
        ServletContextHandler handler = new ServletContextHandler();

        handler.addServlet(TestServlet.class,"/users");
        handler.addServlet(MessagesServlet.class,"/messages");
        HttpServlet scc = new ContentServlet("static");
        handler.addServlet(new ServletHolder(scc), "/static/*");

        server.setHandler(handler);
        server.start();
        server.join();
    }
}
