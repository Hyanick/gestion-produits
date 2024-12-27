package fr.ecommerce.backend.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;



@Component
public class RequestValidationInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        if (requestURI.startsWith("/api/products")) {
            if (request.getMethod().equalsIgnoreCase("POST")) {
                String username = request.getParameter("username");
                String categoryId = request.getParameter("categoryId");
                String productJson = request.getParameter("productJson");
                if (username == null || username.isEmpty() || categoryId == null || productJson == null || productJson.isEmpty()) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid request parameters");
                    return false;
                }
            } else if (request.getMethod().equalsIgnoreCase("GET")) {
                if (requestURI.endsWith("/search")) {
                    String keyword = request.getParameter("keyword");
                    if (keyword == null || keyword.isEmpty()) {
                        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Keyword parameter is required");
                        return false;
                    }
                } else if (requestURI.endsWith("/search/category")) {
                    String categoryName = request.getParameter("categoryName");
                    if (categoryName == null || categoryName.isEmpty()) {
                        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Category name parameter is required");
                        return false;
                    }
                }
            } else if (request.getMethod().equalsIgnoreCase("DELETE")) {
                String id = request.getParameter("id");
                if (id == null || id.isEmpty()) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Product ID is required");
                    return false;
                }
            }
        }
        return true;
    }
}
