package ua.homework.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.time.ZoneId;
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

        TimeZone timeZone;
        if (query != null && !query.trim().isEmpty()) {
            if (query.contains("UTC+")) {
                int offsetHours = Integer.parseInt(query.replace("UTC+", "").trim());
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
        ZoneId zoneId = timeZone.toZoneId();
        ZonedDateTime zonedDateTime = ZonedDateTime.now(zoneId);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss 'UTC'xxx");

        String formattedTime = zonedDateTime.format(formatter);

        String htmlResponse = "<html><body>";
        htmlResponse += "<h1> " + formattedTime + "</h1>";
        htmlResponse += "</body></html>";

        resp.getWriter().println(htmlResponse);
    }


}
