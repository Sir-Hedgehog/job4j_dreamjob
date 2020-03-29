package ru.job4j.crud.controllers;

import ru.job4j.crud.models.User;
import ru.job4j.crud.models.Validate;
import ru.job4j.crud.models.ValidateService;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author Sir-Hedgehog (mailto:quaresma_08@mail.ru)
 * @version 6.0
 * @since 29.03.2020
 */

public class UserUpdateServlet extends HttpServlet {
    private final Validate collection = ValidateService.getInstance();

    /**
     * Метод получает данные о пользователе, которые необходимо обновить
     * @param request - запрос серверу
     * @param response - ответ сервера
     */

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        final HttpSession session = request.getSession();
        request.setAttribute("person", collection.findById(Integer.valueOf(request.getParameter("id"))));
        request.setAttribute("currentRole", session.getAttribute("role"));
        request.getRequestDispatcher("/WEB-INF/views/edit.jsp").forward(request, response);
        response.sendRedirect(String.format("%s/?id=%s", request.getContextPath(), request.getParameter("id")));
    }

    /**
     * Метод формирует результат обновления данных о пользователе
     * @param request - запрос серверу
     * @param response - ответ сервера
     */

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        boolean result = collection.update(
                Integer.valueOf(request.getParameter("id")),
                new User(
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
                || request.getParameter("password").equals("")) {
            request.setAttribute("success", "");
        } else if (!result) {
            request.setAttribute("success", "Для изменения информации введите корректные данные!");
        } else {
            request.setAttribute("success", "Изменения успешно внесены!");
        }
        doGet(request, response);
    }
}
