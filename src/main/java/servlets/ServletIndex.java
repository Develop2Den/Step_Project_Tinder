package src.main.java.servlets;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import src.main.java.utils.ResourcesOps;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public class ServletIndex extends HttpServlet {
    private String folderName;
    private String templateName;
    Map<String, String> data;

    public ServletIndex(String folderName, String templateName, Map<String, String> data) {
        this.folderName = folderName;
        this.templateName = templateName;
        this.data = data;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_32);
        File file = new File(ResourcesOps.dirUnsafe(folderName));
        configuration.setDirectoryForTemplateLoading(file);

        try (PrintWriter pw = resp.getWriter()) {
            Template template = configuration.getTemplate(templateName);
            template.process(data, pw);
        } catch (TemplateException exception) {
            throw new RuntimeException(exception);
        }
    }
}
