package ru.job4j.crud;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import ru.job4j.crud.controllers.UserCreateServlet;
import ru.job4j.crud.controllers.UserUpdateServlet;
import ru.job4j.crud.controllers.UsersServlet;
import ru.job4j.crud.models.Validate;
import ru.job4j.crud.models.ValidateService;
import ru.job4j.crud.models.ValidateStub;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.powermock.api.mockito.PowerMockito;

/**
 * @author Sir-Hedgehog (mailto:quaresma_08@mail.ru)
 * @version 2.0
 * @since 27.05.2020
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest(ValidateService.class)
public class CrudTest {
    @Test
    public void whenAddUserThenStoreIt() throws ServletException, IOException {
        Validate validate = new ValidateStub();
        PowerMockito.mockStatic(ValidateService.class);
        Mockito.when(ValidateService.getInstance()).thenReturn(validate);
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        RequestDispatcher requestDispatcher = Mockito.mock(RequestDispatcher.class);
        when(req.getParameter("name")).thenReturn("Иван");
        when(req.getParameter("country")).thenReturn("Украина");
        when(req.getParameter("city")).thenReturn("Киев");
        when(req.getParameter("email")).thenReturn("ivan@mail.ru");
        when(req.getParameter("login")).thenReturn("Vanya");
        when(req.getParameter("password")).thenReturn("vanya");
        when(req.getParameter("file")).thenReturn("messi.jpg");
        when(req.getParameter("role")).thenReturn("администратор");
        when(req.getRequestDispatcher("/WEB-INF/views/create.jsp")).thenReturn(requestDispatcher);
        new UserCreateServlet().doPost(req, resp);
        assertEquals(validate.findAll().iterator().next().getName(), "Иван");
        assertEquals(validate.findAll().iterator().next().getCountry(), "Украина");
        assertEquals(validate.findAll().iterator().next().getCity(), "Киев");
        assertEquals(validate.findAll().iterator().next().getEmail(), "ivan@mail.ru");
        assertEquals(validate.findAll().iterator().next().getLogin(), "Vanya");
        assertEquals(validate.findAll().iterator().next().getPassword(), "vanya");
        assertEquals(validate.findAll().iterator().next().getPhotoId(), "messi.jpg");
        assertEquals(validate.findAll().iterator().next().getRole(), "администратор");
    }

    @Test
    public void whenAddUserWrongThenNotStoreIt() throws ServletException, IOException {
        Validate validate = new ValidateStub();
        PowerMockito.mockStatic(ValidateService.class);
        Mockito.when(ValidateService.getInstance()).thenReturn(validate);
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        RequestDispatcher requestDispatcher = Mockito.mock(RequestDispatcher.class);
        when(req.getParameter("name")).thenReturn("ivan ivanov");
        when(req.getParameter("country")).thenReturn("Россия");
        when(req.getParameter("city")).thenReturn("Москва");
        when(req.getParameter("email")).thenReturn("ivanmail.ru");
        when(req.getParameter("login")).thenReturn("anya");
        when(req.getParameter("password")).thenReturn("vanya");
        when(req.getParameter("file")).thenReturn("messi.jpg");
        when(req.getParameter("role")).thenReturn("админ");
        when(req.getRequestDispatcher("/WEB-INF/views/create.jsp")).thenReturn(requestDispatcher);
        new UserCreateServlet().doPost(req, resp);
        assertTrue(validate.findAll().isEmpty());
    }

    @Test
    public void whenUpdateUserThenStoreIt() throws ServletException, IOException {
        Validate validate = new ValidateStub();
        PowerMockito.mockStatic(ValidateService.class);
        Mockito.when(ValidateService.getInstance()).thenReturn(validate);
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        RequestDispatcher requestDispatcher = Mockito.mock(RequestDispatcher.class);
        when(req.getParameter("name")).thenReturn("Ivan");
        when(req.getParameter("country")).thenReturn("Россия");
        when(req.getParameter("city")).thenReturn("Москва");
        when(req.getParameter("email")).thenReturn("ivan@mail.ru");
        when(req.getParameter("login")).thenReturn("Vanya");
        when(req.getParameter("password")).thenReturn("vanya");
        when(req.getParameter("file")).thenReturn("messi.jpg");
        when(req.getParameter("role")).thenReturn("администратор");
        when(req.getRequestDispatcher("/WEB-INF/views/create.jsp")).thenReturn(requestDispatcher);
        new UserCreateServlet().doPost(req, resp);
        when(req.getParameter("id")).thenReturn("1");
        when(req.getParameter("name")).thenReturn("Vasiliy");
        when(req.getParameter("country")).thenReturn("Россия");
        when(req.getParameter("city")).thenReturn("Москва");
        when(req.getParameter("email")).thenReturn("vasya@mail.ru");
        when(req.getParameter("login")).thenReturn("Vasya");
        when(req.getParameter("password")).thenReturn("vasya");
        when(req.getParameter("file")).thenReturn("warrior.jpg");
        when(req.getParameter("role")).thenReturn("пользователь");
        when(req.getSession()).thenReturn(session);
        when(session.getAttribute("currentRole")).thenReturn("пользователь");
        when(req.getRequestDispatcher("/WEB-INF/views/edit.jsp")).thenReturn(requestDispatcher);
        new UserUpdateServlet().doPost(req, resp);
        assertEquals(validate.findAll().iterator().next().getName(), "Vasiliy");
        assertEquals(validate.findAll().iterator().next().getCountry(), "Россия");
        assertEquals(validate.findAll().iterator().next().getCity(), "Москва");
        assertEquals(validate.findAll().iterator().next().getEmail(), "vasya@mail.ru");
        assertEquals(validate.findAll().iterator().next().getLogin(), "Vasya");
        assertEquals(validate.findAll().iterator().next().getPassword(), "vasya");
        assertEquals(validate.findAll().iterator().next().getPhotoId(), "warrior.jpg");
        assertEquals(validate.findAll().iterator().next().getRole(), "пользователь");
    }

    @Test
    public void whenUpdateUserWrongThenNotStoreIt() throws ServletException, IOException {
        Validate validate = new ValidateStub();
        PowerMockito.mockStatic(ValidateService.class);
        Mockito.when(ValidateService.getInstance()).thenReturn(validate);
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        RequestDispatcher requestDispatcher = Mockito.mock(RequestDispatcher.class);
        when(req.getParameter("name")).thenReturn("Ivan");
        when(req.getParameter("country")).thenReturn("Украина");
        when(req.getParameter("city")).thenReturn("Киев");
        when(req.getParameter("email")).thenReturn("ivan@mail.ru");
        when(req.getParameter("login")).thenReturn("Vanya");
        when(req.getParameter("password")).thenReturn("vanya");
        when(req.getParameter("file")).thenReturn("warrior.jpg");
        when(req.getParameter("role")).thenReturn("администратор");
        when(req.getRequestDispatcher("/WEB-INF/views/create.jsp")).thenReturn(requestDispatcher);
        new UserCreateServlet().doPost(req, resp);
        when(req.getParameter("id")).thenReturn("1");
        when(req.getParameter("name")).thenReturn("vasiliy");
        when(req.getParameter("country")).thenReturn("Украина");
        when(req.getParameter("city")).thenReturn("Донецк");
        when(req.getParameter("email")).thenReturn("vasyamail.ru");
        when(req.getParameter("login")).thenReturn("asya");
        when(req.getParameter("password")).thenReturn("vasya");
        when(req.getParameter("file")).thenReturn("123.png");
        when(req.getParameter("role")).thenReturn("user");
        when(req.getSession()).thenReturn(session);
        when(session.getAttribute("currentRole")).thenReturn("администратор");
        when(req.getRequestDispatcher("/WEB-INF/views/edit.jsp")).thenReturn(requestDispatcher);
        new UserUpdateServlet().doPost(req, resp);
        assertEquals(validate.findAll().get(0).getName(), "Ivan");
        assertEquals(validate.findAll().get(0).getCountry(), "Украина");
        assertEquals(validate.findAll().get(0).getCity(), "Киев");
        assertEquals(validate.findAll().get(0).getEmail(), "ivan@mail.ru");
        assertEquals(validate.findAll().get(0).getLogin(), "Vanya");
        assertEquals(validate.findAll().get(0).getPassword(), "vanya");
        assertEquals(validate.findAll().get(0).getPhotoId(), "warrior.jpg");
        assertEquals(validate.findAll().get(0).getRole(), "администратор");
    }

    /*@Test
    public void whenDeleteUserThenStoreIt() throws ServletException, IOException {
        Validate validate = new ValidateStub();
        PowerMockito.mockStatic(ValidateService.class);
        Mockito.when(ValidateService.getInstance()).thenReturn(validate);
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);
        HttpSession session = mock(HttpSession.class);
        when(req.getParameter("name")).thenReturn("Ivan");
        when(req.getParameter("country")).thenReturn("Россия");
        when(req.getParameter("city")).thenReturn("Москва");
        when(req.getParameter("email")).thenReturn("ivan@mail.ru");
        when(req.getParameter("login")).thenReturn("Vanya");
        when(req.getParameter("password")).thenReturn("vanya");
        when(req.getParameter("file")).thenReturn("warrior.jpg");
        when(req.getParameter("role")).thenReturn("администратор");
        when(req.getRequestDispatcher("/WEB-INF/views/create.jsp")).thenReturn(requestDispatcher);
        new UserCreateServlet().doPost(req, resp);
        when(req.getParameter("id")).thenReturn("1");
        when(req.getSession()).thenReturn(session);
        when(session.getAttribute("role")).thenReturn("администратор");
        when(req.getRequestDispatcher("/WEB-INF/views/adminList.jsp")).thenReturn(requestDispatcher);
        new UsersServlet().doPost(req, resp);
        assertThat(validate.findAll().isEmpty(), is(true));
    }*/

    @Test
    public void whenFindAllOfUserThenStoreItForAdmin() throws ServletException, IOException {
        Validate validate = new ValidateStub();
        PowerMockito.mockStatic(ValidateService.class);
        Mockito.when(ValidateService.getInstance()).thenReturn(validate);
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);
        HttpSession session = mock(HttpSession.class);
        when(req.getParameter("name")).thenReturn("Ivan");
        when(req.getParameter("country")).thenReturn("Беларусь");
        when(req.getParameter("city")).thenReturn("Гродно");
        when(req.getParameter("email")).thenReturn("ivan@mail.ru");
        when(req.getParameter("login")).thenReturn("Vanya");
        when(req.getParameter("password")).thenReturn("vanya");
        when(req.getParameter("file")).thenReturn("runner.jpg");
        when(req.getParameter("role")).thenReturn("администратор");
        when(req.getRequestDispatcher("/WEB-INF/views/create.jsp")).thenReturn(requestDispatcher);
        new UserCreateServlet().doPost(req, resp);
        when(req.getParameter("name")).thenReturn("Vasiliy");
        when(req.getParameter("country")).thenReturn("Беларусь");
        when(req.getParameter("city")).thenReturn("Минск");
        when(req.getParameter("email")).thenReturn("vasya@mail.ru");
        when(req.getParameter("login")).thenReturn("Vasya");
        when(req.getParameter("password")).thenReturn("vasya");
        when(req.getParameter("file")).thenReturn("warrior.jpg");
        when(req.getParameter("role")).thenReturn("пользователь");
        when(req.getRequestDispatcher("/WEB-INF/views/create.jsp")).thenReturn(requestDispatcher);
        new UserCreateServlet().doPost(req, resp);
        when(req.getSession()).thenReturn(session);
        when(session.getAttribute("role")).thenReturn("администратор");
        when(req.getRequestDispatcher("/WEB-INF/views/adminList.jsp")).thenReturn(requestDispatcher);
        new UsersServlet().doGet(req, resp);
        assertEquals(validate.findAll().size(), 2);
        assertEquals(validate.findAll().get(0).getName(), "Ivan");
        assertEquals(validate.findAll().get(0).getCountry(), "Беларусь");
        assertEquals(validate.findAll().get(0).getCity(), "Гродно");
        assertEquals(validate.findAll().get(0).getEmail(), "ivan@mail.ru");
        assertEquals(validate.findAll().get(0).getLogin(), "Vanya");
        assertEquals(validate.findAll().get(0).getPassword(), "vanya");
        assertEquals(validate.findAll().get(0).getPhotoId(), "runner.jpg");
        assertEquals(validate.findAll().get(0).getRole(), "администратор");
        assertEquals(validate.findAll().get(1).getName(), "Vasiliy");
        assertEquals(validate.findAll().get(1).getCountry(), "Беларусь");
        assertEquals(validate.findAll().get(1).getCity(), "Минск");
        assertEquals(validate.findAll().get(1).getEmail(), "vasya@mail.ru");
        assertEquals(validate.findAll().get(1).getLogin(), "Vasya");
        assertEquals(validate.findAll().get(1).getPassword(), "vasya");
        assertEquals(validate.findAll().get(1).getPhotoId(), "warrior.jpg");
        assertEquals(validate.findAll().get(1).getRole(), "пользователь");
    }
}
