package ua.homework.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

@WebServlet(value = "/time")
public class TimeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=utf-8");
        String query = req.getParameter("timezone");

        TimeZone timeZone;
        if (query != null && !query.trim().isEmpty()) {
            if (query.contains("UTC ")) {
                int offsetHours = Integer.parseInt(query.replace("UTC ", "").trim());
                timeZone = TimeZone.getTimeZone("GMT+" + offsetHours);
            } else if (query.contains("UTC-")) {
                int offsetHours = Integer.parseInt(query.replace("UTC-", "").trim());
                timeZone = TimeZone.getTimeZone("GMT-" + offsetHours);
            } else {
                timeZone = TimeZone.getTimeZone(query);
            }
        } else {
            timeZone = TimeZone.getTimeZone("UTC");
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
        dateFormat.setTimeZone(timeZone);

        Date currentDate = new Date();

        String htmlResponse = "<html><body>";
        htmlResponse += "<h1> " + dateFormat.format(currentDate) + "</h1>";
        htmlResponse += "</body></html>";

        resp.getWriter().println(htmlResponse);
    }


}
