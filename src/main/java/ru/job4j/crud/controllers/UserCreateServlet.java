package ru.job4j.crud.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * @version 6.0
 * @since 29.03.2020
 */

public class UserCreateServlet extends HttpServlet {
    private final Validate collection = ValidateService.getInstance();
    private static final Logger LOG = LoggerFactory.getLogger(UsersServlet.class);

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
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        boolean result = this.collection.add(new User(
                request.getParameter("name"),
                request.getParameter("country"),
                request.getParameter("city"),
                request.getParameter("email"),
                request.getParameter("login"),
                request.getParameter("password"),
                request.getParameter("file"),
                request.getParameter("role")));
        if (request.getParameter("name").equals("")
                || request.getParameter("country").equals("")
                || request.getParameter("city").equals("")
                || request.getParameter("email").equals("")
                || request.getParameter("login").equals("")
                || request.getParameter("password").equals("")
                || !request.getParameter("file").contains(".jpg")) {
            request.setAttribute("success", "");
        } else if (!result) {
            request.setAttribute("success", "Введите корректные данные!");
        } else {
            request.setAttribute("success", "Пользователь успешно добавлен!");
        }
        doGet(request, response);
    }
}
