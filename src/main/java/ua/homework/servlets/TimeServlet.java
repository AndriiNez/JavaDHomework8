package ua.homework.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import java.util.TimeZone;

@WebServlet(value = "/time")
public class TimeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=utf-8");
        String query = req.getParameter("timezone");
        if (query != null) {
            query = query.replace(" ", "+");
        } else {
            query = "UTC"; 
        }
        ZoneId zoneId = ZoneId.of("UTC");
        if (query != null && !query.trim().isEmpty()) {
            if (query.startsWith("UTC+") || query.startsWith("UTC-")) {
                int offsetHours = Integer.parseInt(query.substring(3));
                zoneId = ZoneId.ofOffset("UTC", ZoneOffset.ofHours(offsetHours));
            } else {
                zoneId = ZoneId.of(query);
            }
        }

        ZonedDateTime zonedDateTime = ZonedDateTime.now(zoneId);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss 'UTC'xxx");
        String formattedTime = zonedDateTime.format(formatter);

        String htmlResponse = "<html><body>";
        htmlResponse += "<h1> " + formattedTime + "</h1>";
        htmlResponse += "</body></html>";

        resp.getWriter().println(htmlResponse);
    }


}
