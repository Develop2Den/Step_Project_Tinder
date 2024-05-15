package src.main.java.servlets;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

public class LikedServlet extends HttpServlet {
    private int activeUserId;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//        LikedService likedService = new LikedServiceImpl();
//        activeUserId = 1; //getCookies of active user here;
//        List<UserChoices> data = likedService.loadData(activeUserId);

        String fileName = getClass()
                .getClassLoader()
                .getResource("templates/like-page.html")
                .getFile();

        File file = new File(fileName);

        try (
                PrintWriter w = resp.getWriter();
                BufferedReader br = new BufferedReader(new FileReader(file))
        ) {
            br.lines()
                    .forEach(w::write);
        }
    }
}
