package servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class MessagesServlet extends HttpServlet {
//    private Map<String, Integer> choicesCount = new HashMap<>();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String fileName = getClass()
                .getClassLoader()
                .getResource("templates/chat.html")
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

//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//            String choice = req.getParameter("choice");
//
//            if (choice != null && (choice.equals("yes") || choice.equals("no"))) {
//                choicesCount.put(choice, choicesCount.getOrDefault(choice, 0) + 1);
//                resp.setStatus(HttpServletResponse.SC_OK);
//            } else {
//                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//        }
//    }
}
