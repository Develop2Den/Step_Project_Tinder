package servlets;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

public class ContentServlet extends HttpServlet {

    private final String prefix;

    public ContentServlet(String root) {
        this.prefix = root;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fileName = req.getPathInfo();
        String fullName = prefix + fileName;

        // Ensure content type is correctly set based on file type
        if (fileName.endsWith(".css")) {
            resp.setContentType("text/css");
        } else if (fileName.endsWith(".js")) {
            resp.setContentType("application/javascript");
        } else if (fileName.endsWith(".html")) {
            resp.setContentType("text/html");
        } else if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) {
            resp.setContentType("image/jpeg");
        } else if (fileName.endsWith(".png")) {
            resp.setContentType("image/png");
        }

        try (InputStream resourceStream = getClass().getClassLoader().getResourceAsStream(fullName);
             ServletOutputStream os = resp.getOutputStream()) {
            if (resourceStream == null) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = resourceStream.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
