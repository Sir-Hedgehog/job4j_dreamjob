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
 * @version 1.0
 * @since 14.03.2020
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
        when(req.getParameter("name")).thenReturn("Ivan");
        when(req.getParameter("email")).thenReturn("ivan@mail.ru");
        when(req.getParameter("login")).thenReturn("Vanya");
        when(req.getParameter("password")).thenReturn("vanya");
        when(req.getParameter("file")).thenReturn("kylo.jpg");
        when(req.getParameter("role")).thenReturn("администратор");
        when(req.getRequestDispatcher("/WEB-INF/views/validCreate.jsp")).thenReturn(requestDispatcher);
        new UserCreateServlet().doPost(req, resp);
        assertThat(validate.findAll().iterator().next().getName(), is("Ivan"));
        assertThat(validate.findAll().iterator().next().getEmail(), is("ivan@mail.ru"));
        assertThat(validate.findAll().iterator().next().getLogin(), is("Vanya"));
        assertThat(validate.findAll().iterator().next().getPassword(), is("vanya"));
        assertThat(validate.findAll().iterator().next().getPhotoId(), is("kylo.jpg"));
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
        when(req.getParameter("email")).thenReturn("ivanmail.ru");
        when(req.getParameter("login")).thenReturn("anya");
        when(req.getParameter("password")).thenReturn("vanya");
        when(req.getParameter("file")).thenReturn("kylo.jpg");
        when(req.getParameter("role")).thenReturn("админ");
        when(req.getRequestDispatcher("/WEB-INF/views/invalidCreate.jsp")).thenReturn(requestDispatcher);
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
        RequestDispatcher requestDispatcher = Mockito.mock(RequestDispatcher.class);
        when(req.getParameter("name")).thenReturn("Ivan");
        when(req.getParameter("email")).thenReturn("ivan@mail.ru");
        when(req.getParameter("login")).thenReturn("Vanya");
        when(req.getParameter("password")).thenReturn("vanya");
        when(req.getParameter("file")).thenReturn("kylo.jpg");
        when(req.getParameter("role")).thenReturn("администратор");
        when(req.getRequestDispatcher("/WEB-INF/views/validCreate.jsp")).thenReturn(requestDispatcher);
        new UserCreateServlet().doPost(req, resp);
        when(req.getParameter("id")).thenReturn("1");
        when(req.getParameter("name")).thenReturn("Vasiliy");
        when(req.getParameter("email")).thenReturn("vasya@mail.ru");
        when(req.getParameter("login")).thenReturn("Vasya");
        when(req.getParameter("password")).thenReturn("vasya");
        when(req.getParameter("file")).thenReturn("charmander.jpg");
        when(req.getParameter("role")).thenReturn("пользователь");
        when(req.getRequestDispatcher("/WEB-INF/views/validEdit.jsp")).thenReturn(requestDispatcher);
        new UserUpdateServlet().doPost(req, resp);
        assertThat(validate.findAll().iterator().next().getName(), is("Vasiliy"));
        assertThat(validate.findAll().iterator().next().getEmail(), is("vasya@mail.ru"));
        assertThat(validate.findAll().iterator().next().getLogin(), is("Vasya"));
        assertThat(validate.findAll().iterator().next().getPassword(), is("vasya"));
        assertThat(validate.findAll().iterator().next().getPhotoId(), is("charmander.jpg"));
        assertThat(validate.findAll().iterator().next().getRole(), is("пользователь"));
    }

    @Test
    public void whenUpdateUserWrongThenNotStoreIt() throws ServletException, IOException {
        Validate validate = new ValidateStub();
        PowerMockito.mockStatic(ValidateService.class);
        Mockito.when(ValidateService.getInstance()).thenReturn(validate);
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        RequestDispatcher requestDispatcher = Mockito.mock(RequestDispatcher.class);
        when(req.getParameter("name")).thenReturn("Ivan");
        when(req.getParameter("email")).thenReturn("ivan@mail.ru");
        when(req.getParameter("login")).thenReturn("Vanya");
        when(req.getParameter("password")).thenReturn("vanya");
        when(req.getParameter("file")).thenReturn("kylo.jpg");
        when(req.getParameter("role")).thenReturn("администратор");
        when(req.getRequestDispatcher("/WEB-INF/views/validCreate.jsp")).thenReturn(requestDispatcher);
        new UserCreateServlet().doPost(req, resp);
        when(req.getParameter("id")).thenReturn("1");
        when(req.getParameter("name")).thenReturn("vasiliy");
        when(req.getParameter("email")).thenReturn("vasyamail.ru");
        when(req.getParameter("login")).thenReturn("asya");
        when(req.getParameter("password")).thenReturn("vasya");
        when(req.getParameter("file")).thenReturn("123.png");
        when(req.getParameter("role")).thenReturn("user");
        when(req.getRequestDispatcher("/WEB-INF/views/invalidEdit.jsp")).thenReturn(requestDispatcher);
        new UserUpdateServlet().doPost(req, resp);
        assertThat(validate.findAll().get(0).getName(), is("Ivan"));
        assertThat(validate.findAll().get(0).getEmail(), is("ivan@mail.ru"));
        assertThat(validate.findAll().get(0).getLogin(), is("Vanya"));
        assertThat(validate.findAll().get(0).getPassword(), is("vanya"));
        assertThat(validate.findAll().get(0).getPhotoId(), is("kylo.jpg"));
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
        when(req.getParameter("email")).thenReturn("ivan@mail.ru");
        when(req.getParameter("login")).thenReturn("Vanya");
        when(req.getParameter("password")).thenReturn("vanya");
        when(req.getParameter("file")).thenReturn("kylo.jpg");
        when(req.getParameter("role")).thenReturn("администратор");
        when(req.getRequestDispatcher("/WEB-INF/views/validCreate.jsp")).thenReturn(requestDispatcher);
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
        when(req.getParameter("email")).thenReturn("ivan@mail.ru");
        when(req.getParameter("login")).thenReturn("Vanya");
        when(req.getParameter("password")).thenReturn("vanya");
        when(req.getParameter("file")).thenReturn("kylo.jpg");
        when(req.getParameter("role")).thenReturn("администратор");
        when(req.getRequestDispatcher("/WEB-INF/views/validCreate.jsp")).thenReturn(requestDispatcher);
        new UserCreateServlet().doPost(req, resp);
        when(req.getParameter("name")).thenReturn("Vasiliy");
        when(req.getParameter("email")).thenReturn("vasya@mail.ru");
        when(req.getParameter("login")).thenReturn("Vasya");
        when(req.getParameter("password")).thenReturn("vasya");
        when(req.getParameter("file")).thenReturn("charmander.jpg");
        when(req.getParameter("role")).thenReturn("пользователь");
        when(req.getRequestDispatcher("/WEB-INF/views/validCreate.jsp")).thenReturn(requestDispatcher);
        new UserCreateServlet().doPost(req, resp);
        when(req.getSession()).thenReturn(session);
        when(session.getAttribute("role")).thenReturn("администратор");
        when(req.getRequestDispatcher("/WEB-INF/views/adminList.jsp")).thenReturn(requestDispatcher);
        new UsersServlet().doGet(req, resp);
        assertThat(validate.findAll().size(), is(2));
        assertThat(validate.findAll().get(0).getName(), is("Ivan"));
        assertThat(validate.findAll().get(0).getEmail(), is("ivan@mail.ru"));
        assertThat(validate.findAll().get(0).getLogin(), is("Vanya"));
        assertThat(validate.findAll().get(0).getPassword(), is("vanya"));
        assertThat(validate.findAll().get(0).getPhotoId(), is("kylo.jpg"));
        assertThat(validate.findAll().get(0).getRole(), is("администратор"));
        assertThat(validate.findAll().get(1).getName(), is("Vasiliy"));
        assertThat(validate.findAll().get(1).getEmail(), is("vasya@mail.ru"));
        assertThat(validate.findAll().get(1).getLogin(), is("Vasya"));
        assertThat(validate.findAll().get(1).getPassword(), is("vasya"));
        assertThat(validate.findAll().get(1).getPhotoId(), is("charmander.jpg"));
        assertThat(validate.findAll().get(1).getRole(), is("пользователь"));
    }
}
