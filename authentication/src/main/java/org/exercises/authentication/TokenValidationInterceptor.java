package org.exercises.authentication;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class TokenValidationInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getRequestURI().equals("/requires-token")) {
            String requestToken = request.getHeader("Authorization");

            if (requestToken == null) {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.setContentType("application/json");
                response.getWriter().write("{ \"error\": \"no token provided\" }");
                return false;
            }

            if (!requestToken.equals("Bearer rightToken")) {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.setContentType("application/json");
                response.getWriter().write("{ \"error\": \"invalid token: " + requestToken + "\" }");
                return false;
            }

            return true;
        }

        if (request.getRequestURI().equals("/always-allowed")) {
            return true;
        }

        if (request.getRequestURI().equals("/always-blocked")) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json");
            response.getWriter().write("{ \"error\": \"blocked endpoint\" }");
            return false;
        }

        response.setStatus(HttpStatus.NOT_FOUND.value());
        return false;
    }
}
