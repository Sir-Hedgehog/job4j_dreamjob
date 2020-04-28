package ru.job4j.crud.controllers;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;

/**
 * @author Sir-Hedgehog (mailto:quaresma_08@mail.ru)
 * @version 1.0
 * @since 29.03.2020
 */

public class AjaxForCitiesServlet extends HttpServlet {

    /**
     * Метод получает данные о стране, список городов которой необходимо динамически подгрузить для клиента
     * @param request - запрос серверу
     * @param response - ответ сервера
     */

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        List<String> list = new ArrayList<>();
        String json = "";
        String country = request.getParameter("country");
        if (country.equals("Россия")) {
            list.add("Москва");
            list.add("Санкт-Петербург");
            list.add("Казань");
        } else if (country.equals("Украина")) {
            list.add("Киев");
            list.add("Донецк");
            list.add("Львов");
        } else if (country.equals("Беларусь")) {
            list.add("Минск");
            list.add("Брест");
            list.add("Гродно");
        }
        json = new Gson().toJson(list);
        response.getWriter().write(json);
    }
}
