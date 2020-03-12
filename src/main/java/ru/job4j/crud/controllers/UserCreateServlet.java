package ru.job4j.crud.controllers;

import ru.job4j.crud.models.User;
import ru.job4j.crud.models.Validate;
import ru.job4j.crud.models.ValidateService;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Sir-Hedgehog (mailto:quaresma_08@mail.ru)
 * @version 5.0
 * @since 12.03.2020
 */

public class UserCreateServlet extends HttpServlet {
    private final Validate collection = ValidateService.getInstance();

    /**
     * Метод создает форму для добавления новых пользователей
     * @param request - запрос серверу
     * @param response - ответ сервера
     */

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setAttribute("file", "Фото не выбрано");
        request.getRequestDispatcher("/WEB-INF/views/create.jsp").forward(request, response);
    }

    /**
     * Метод формирует результат добавления пользователя
     * @param request - запрос серверу
     * @param response - ответ сервера
     */

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        boolean result = this.collection.add(new User(
                request.getParameter("name"),
                request.getParameter("email"),
                request.getParameter("login"),
                request.getParameter("password"),
                request.getParameter("file"),
                request.getParameter("role")));
        if (result) {
            request.getRequestDispatcher("/WEB-INF/views/validCreate.jsp").forward(request, response);
            response.sendRedirect(String.format("%s/", request.getContextPath()));
        } else {
            request.setAttribute("file", "Фото не выбрано");
            request.getRequestDispatcher("/WEB-INF/views/invalidCreate.jsp").forward(request, response);
            response.sendRedirect(String.format("%s/", request.getContextPath()));
        }
    }
}
