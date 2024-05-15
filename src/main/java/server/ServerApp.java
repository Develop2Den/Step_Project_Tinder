package src.main.java.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import src.main.java.servlets.ServletIndex;
import src.main.java.servlets.ServletStaticContent;

import javax.servlet.http.HttpServlet;
import java.util.HashMap;
import java.util.Map;

public class ServerApp {
    private static int portNumber = 8081;
    private static String folderName = "templates";
    private static String templateIndex = "index.html";
    private static String templateLikePage = "like-page.html";
    private static Map<String, String> dataIndex = new HashMap<>();
    public static void main(String[] args) throws Exception {
        Server server = new Server(portNumber);
        ServletContextHandler handler = new ServletContextHandler();

        HttpServlet servletLiked = new ServletIndex(folderName, templateLikePage, dataIndex);
        handler.addServlet(new ServletHolder(servletLiked), "/liked");

        HttpServlet scc = new ServletStaticContent("css");
        handler.addServlet(new ServletHolder(scc), "/css/*");

        server.setHandler(handler);
        server.start();
        server.join();
    }
}
