package ru.job4j.crud.controllers;

import ru.job4j.crud.models.Validate;
import ru.job4j.crud.models.ValidateService;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

/**
 * @author Sir-Hedgehog (mailto:quaresma_08@mail.ru)
 * @version 7.0
 * @since 29.03.2020
 */

public class UsersServlet extends HttpServlet {
    private final Validate collection = ValidateService.getInstance();

    /**
     * Метод формирует список существующих пользователей
     * @param request - запрос серверу
     * @param response - ответ сервера
     */

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        final HttpSession session = request.getSession();
        request.setAttribute("clients", collection.findAll());
        if (session.getAttribute("role").equals("администратор")) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/adminList.jsp");
            dispatcher.forward(request, response);
        } else if (session.getAttribute("role").equals("пользователь")) {
            request.setAttribute("currentId", session.getAttribute("id"));
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/userList.jsp");
            dispatcher.forward(request, response);
        }
    }

    /**
     * Метод удаляет пользовательский аккаунт вместе с картинкой при нажатии на кнопку "Удалить"
     * @param request - запрос серверу
     * @param response - ответ сервера
     */

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        this.deletePicture(request);
        this.doGet(request, response);
    }

    /**
     * Реализация удаления
     * @param request - запрос серверу
     */

    private void deletePicture(HttpServletRequest request) {
        boolean resultOfDelete = true;
        for (File file : Objects.requireNonNull(new File("/bin/images/").listFiles())) {
            if (file.getName().equals(collection.findById(Integer.valueOf(request.getParameter("id"))).getPhotoId())) {
                resultOfDelete = file.delete();
            }
        }
        if (resultOfDelete) {
            collection.delete(Integer.valueOf(request.getParameter("id")));
        }
    }
}