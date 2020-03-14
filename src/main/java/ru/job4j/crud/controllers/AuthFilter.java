package ru.job4j.crud.controllers;

import ru.job4j.crud.models.User;
import ru.job4j.crud.models.Validate;
import ru.job4j.crud.models.ValidateService;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author Sir-Hedgehog (mailto:quaresma_08@mail.ru)
 * @version 1.0
 * @since 12.03.2020
 */

public class AuthFilter implements Filter {
    private final Validate collection = ValidateService.getInstance();

    @Override
    public void init(FilterConfig filterConfig) {

    }

    /**
     * Метод фильтрует входящие данные на этапах аутентификации и авторизации
     * @param servletRequest - запрос сервера
     * @param servletResponse - ответ серверу
     * @param chain - перевод на следующий фильтр
     */

    @Override
    public void doFilter(final ServletRequest servletRequest, final ServletResponse servletResponse, final FilterChain chain) throws IOException, ServletException {
        servletRequest.setCharacterEncoding("UTF-8");
        servletResponse.setContentType("text/html; charset=utf-8");
        final HttpServletRequest request = (HttpServletRequest) servletRequest;
        final HttpServletResponse response = (HttpServletResponse) servletResponse;
        final HttpSession session = request.getSession();
        final String login = request.getParameter("login");
        final String password = request.getParameter("password");
        if (request.getRequestURI().contains("/signin")
                || request.getRequestURI().contains("/create")
                || request.getRequestURI().contains("/upload")
                || request.getRequestURI().contains("/download")) {
            if (request.getRequestURI().contains("/signin")) {
                if (login == null && password == null) {
                    request.getRequestDispatcher("/WEB-INF/views/startView.jsp").forward(request, response);
                    return;
                } else if (session.getAttribute("login") != null && session.getAttribute("password") != null) {
                    final User user = this.collection.getUser(login, password);
                    final String role = session.getAttribute("role").toString();
                    request.getSession().setAttribute("currentId", user.getId());
                    this.moveToMenu(request, response, role, Integer.valueOf(user.getId()).toString());
                } else if (collection.checkAccount(login, password)) {
                    final User user = this.collection.getUser(login, password);
                    request.getSession().setAttribute("login", login);
                    request.getSession().setAttribute("password", password);
                    request.getSession().setAttribute("role", user.getRole());
                    request.getSession().setAttribute("currentId", user.getId());
                    this.moveToMenu(request, response, user.getRole(), Integer.valueOf(user.getId()).toString());
                } else {
                    request.setAttribute("error", "Ошибка ввода логина/пароля!");
                    request.getRequestDispatcher("/WEB-INF/views/startView.jsp").forward(request, response);
                    return;
                }
            }
        } else if (session.getAttribute("login") == null && session.getAttribute("password") == null) {
            response.sendRedirect(String.format("%s/signin", request.getContextPath()));
            return;
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }

    /**
     * Метод отображает данные в зависимости от того, какую роль имеет аутентифицированный пользователь
     * @param req - запрос серверу
     * @param resp - ответ сервера
     * @param role - роль аутентифицированного пользователя
     * @param currentId - идентификатор аутентифицированного пользователя
     */

    private void moveToMenu(HttpServletRequest req, HttpServletResponse resp, String role, String currentId) throws ServletException, IOException {
        req.setAttribute("clients", collection.findAll());
        if (role.equals("администратор")) {
            req.getRequestDispatcher("/WEB-INF/views/adminList.jsp").forward(req, resp);
        } else if (role.equals("пользователь")) {
            req.setAttribute("currentId", currentId);
            req.getRequestDispatcher("/WEB-INF/views/userList.jsp").forward(req, resp);
        }
    }
}