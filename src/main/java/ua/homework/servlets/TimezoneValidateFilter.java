package ua.homework.servlets;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.TimeZone;

@WebFilter(value = "/time")
public class TimezoneValidateFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String timezone = request.getParameter("timezone");
        if (timezone != null) {
            timezone = timezone.replace(" ", "+");
        } else {
            timezone = "UTC"; // за замовчуванням
        }
        System.out.println("timezone = " + timezone);
        if (timezone != null && !isValidTimezone(timezone)) {
            response.getWriter().write("Invalid timezone. HTTP Status-"+ HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        chain.doFilter(request, response);
    }

    private boolean isValidTimezone(String timezone) {

        if (timezone.startsWith("UTC+") || timezone.startsWith("UTC-")) {
            try {
                int offset = Integer.parseInt(timezone.substring(4));

                if (offset >= -12 && offset <= 12) {
                    return true;
                }
            } catch (NumberFormatException e) {
                return false;
            }
        } else if (timezone.equalsIgnoreCase("UTC") || timezone.equalsIgnoreCase("")) {
            return true;
        }

        return false;
    }
}
