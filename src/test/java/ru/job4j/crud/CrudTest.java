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
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
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
        assertThat(validate.findAll().iterator().next().getName(), is("Иван"));
        assertThat(validate.findAll().iterator().next().getCountry(), is("Украина"));
        assertThat(validate.findAll().iterator().next().getCity(), is("Киев"));
        assertThat(validate.findAll().iterator().next().getEmail(), is("ivan@mail.ru"));
        assertThat(validate.findAll().iterator().next().getLogin(), is("Vanya"));
        assertThat(validate.findAll().iterator().next().getPassword(), is("vanya"));
        assertThat(validate.findAll().iterator().next().getPhotoId(), is("messi.jpg"));
        assertThat(validate.findAll().iterator().next().getRole(), is("администратор"));
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
        assertThat(validate.findAll().isEmpty(), is(true));
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
        assertThat(validate.findAll().iterator().next().getName(), is("Vasiliy"));
        assertThat(validate.findAll().iterator().next().getCountry(), is("Россия"));
        assertThat(validate.findAll().iterator().next().getCity(), is("Москва"));
        assertThat(validate.findAll().iterator().next().getEmail(), is("vasya@mail.ru"));
        assertThat(validate.findAll().iterator().next().getLogin(), is("Vasya"));
        assertThat(validate.findAll().iterator().next().getPassword(), is("vasya"));
        assertThat(validate.findAll().iterator().next().getPhotoId(), is("warrior.jpg"));
        assertThat(validate.findAll().iterator().next().getRole(), is("пользователь"));
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
        assertThat(validate.findAll().get(0).getName(), is("Ivan"));
        assertThat(validate.findAll().get(0).getCountry(), is("Украина"));
        assertThat(validate.findAll().get(0).getCity(), is("Киев"));
        assertThat(validate.findAll().get(0).getEmail(), is("ivan@mail.ru"));
        assertThat(validate.findAll().get(0).getLogin(), is("Vanya"));
        assertThat(validate.findAll().get(0).getPassword(), is("vanya"));
        assertThat(validate.findAll().get(0).getPhotoId(), is("warrior.jpg"));
        assertThat(validate.findAll().get(0).getRole(), is("администратор"));
    }

    @Test
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
        when(req.getParameter("file")).thenReturn("vader.jpg");
        when(req.getParameter("role")).thenReturn("администратор");
        when(req.getRequestDispatcher("/WEB-INF/views/create.jsp")).thenReturn(requestDispatcher);
        new UserCreateServlet().doPost(req, resp);
        when(req.getParameter("id")).thenReturn("1");
        when(req.getSession()).thenReturn(session);
        when(session.getAttribute("role")).thenReturn("администратор");
        when(req.getRequestDispatcher("/WEB-INF/views/adminList.jsp")).thenReturn(requestDispatcher);
        new UsersServlet().doPost(req, resp);
        assertThat(validate.findAll().isEmpty(), is(true));
    }

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
        assertThat(validate.findAll().size(), is(2));
        assertThat(validate.findAll().get(0).getName(), is("Ivan"));
        assertThat(validate.findAll().get(0).getCountry(), is("Беларусь"));
        assertThat(validate.findAll().get(0).getCity(), is("Гродно"));
        assertThat(validate.findAll().get(0).getEmail(), is("ivan@mail.ru"));
        assertThat(validate.findAll().get(0).getLogin(), is("Vanya"));
        assertThat(validate.findAll().get(0).getPassword(), is("vanya"));
        assertThat(validate.findAll().get(0).getPhotoId(), is("runner.jpg"));
        assertThat(validate.findAll().get(0).getRole(), is("администратор"));
        assertThat(validate.findAll().get(1).getName(), is("Vasiliy"));
        assertThat(validate.findAll().get(1).getCountry(), is("Беларусь"));
        assertThat(validate.findAll().get(1).getCity(), is("Минск"));
        assertThat(validate.findAll().get(1).getEmail(), is("vasya@mail.ru"));
        assertThat(validate.findAll().get(1).getLogin(), is("Vasya"));
        assertThat(validate.findAll().get(1).getPassword(), is("vasya"));
        assertThat(validate.findAll().get(1).getPhotoId(), is("warrior.jpg"));
        assertThat(validate.findAll().get(1).getRole(), is("пользователь"));
    }
}
