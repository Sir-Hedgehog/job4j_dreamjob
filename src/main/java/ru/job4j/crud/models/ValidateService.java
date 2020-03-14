package ru.job4j.crud.models;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.crud.controllers.UserServlet;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Sir-Hedgehog (mailto:quaresma_08@mail.ru)
 * @version 4.0
 * @since 12.02.2020
 */

public class ValidateService implements Validate {
    private static final Pattern CHECK_OF_NAME = Pattern.compile("^(([A-Z]|[А-Я]){1}([a-z]|[а-я]){1,})$");
    private static final Pattern CHECK_OF_EMAIL = Pattern.compile("^((\\w{1,}[-._]{0,1}\\w{1,})+@(\\w{1,}[-._]{0,1}\\w{1,})+[.]{1}[a-z]{2,4})$");
    private static final Pattern CHECK_OF_LOGIN = Pattern.compile("^(\\w{1,}[-._]{0,1}\\w{1,})$");
    private static final Pattern CHECK_OF_PHOTO = Pattern.compile("^((\\w|\\W){1,})+.(gif|jpg|png|jpeg|svg)$");
    private static final Pattern CHECK_OF_PASSWORD = Pattern.compile("^(((\\w|\\W){1,})[-._]{0,1}((\\w|\\W){1,}))$");
    private static final Logger LOG = LoggerFactory.getLogger(UserServlet.class);

    private final Store logic = DatabaseStore.getInstance();

    private static Validate validateInstance = new ValidateService();

    /**
     * Метод дает право создать единственный экзепляр класса для взаимосвязи с сервлетом
     * @return - экзепляр класса ValidateService
     */

    public static Validate getInstance() {
        return validateInstance;
    }

    /**
     * Метод проверяет введенное имя на валидность
     * @param user - пользователь
     * @return - валидные данные или нет
     */

    private boolean checkName(User user) {
        Matcher matcher = CHECK_OF_NAME.matcher(user.getName());
        return matcher.find();
    }

    /**
     * Метод проверяет введенный адрес почты на валидность
     * @param user - пользователь
     * @return - валидные данные или нет
     */

    private boolean checkEmail(User user) {
        Matcher matcher = CHECK_OF_EMAIL.matcher(user.getEmail());
        return matcher.find();
    }

    /**
     * Метод проверяет введенный логин на валидность
     * @param user - пользователь
     * @return - валидные данные или нет
     */

    private boolean checkLogin(User user) {
        Matcher matcher = CHECK_OF_LOGIN.matcher(user.getLogin());
        return matcher.find();
    }

    /**
     * Метод проверяет формат прикрепленной аватарки на валидность
     * @param user - пользователь
     * @return - валидные данные или нет
     */

    private boolean checkPhoto(User user) {
        Matcher matcher = CHECK_OF_PHOTO.matcher(user.getPhotoId());
        return matcher.find();
    }

    /**
     * Метод проверяет введенный пароль на валидность
     * @param user - пароль
     * @return - валидные данные или нет
     */

    private boolean checkPassword(User user) {
        Matcher matcher = CHECK_OF_PASSWORD.matcher(user.getPassword());
        return matcher.find();
    }

    /**
     * Метод добавляет нового пользователя с учетом фильтра валидности
     * @param user - пользователь
     * @return - успешность операции
     */

    public boolean add(User user) {
        boolean result = false;
        if (checkName(user) && checkEmail(user) && checkLogin(user) && checkPassword(user) && checkPhoto(user)) {
            logic.add(user);
            result = true;
            LOG.info("LOGGER: Successful execution to add");
        }
        return result;
    }

    /**
     * Метод обновляет данные существующего пользователя с учетом фильтра валидности
     * @param user - пользователь
     * @return - успешность операции
     */

    public boolean update(int id, User user) {
        boolean result = false;
        if (checkName(user) && checkEmail(user) && checkLogin(user) && checkPassword(user) && checkPhoto(user)) {
            logic.update(id, user);
            result = true;
            LOG.info("LOGGER: Successful execution to update");
        }
        return result;
    }

    /**
     * Метод удаляет существующего пользователя
     * @return - успешность операции
     */

    public boolean delete(int id) {
        boolean result = logic.delete(id);
        if (result) {
            LOG.info("LOGGER: Successful execution to delete");
        }
        return result;
    }

    /**
     * Метод получает список существующих пользователей
     * @return - список существующих пользователей
     */

    public CopyOnWriteArrayList<User> findAll() {
        return logic.findAll();
    }

    /**
     * Метод получает данные существующего пользователя по id
     * @return - данные существующего пользователя
     */

    public User findById(int id) {
        return logic.findById(id);
    }

    /**
     * Метод осуществляет валидацию при аутентификации пользователя
     * @param login - введенный логин
     * @param password - введенный пароль
     * @return - успешность валидации
     */

    public boolean checkAccount(String login, String password) {
        boolean result = false;
        for (User existing : logic.findAll()) {
            if (existing.getLogin().equals(login) && existing.getPassword().equals(password)) {
                result = true;
                break;
            }
        }
        return result;
    }

    /**
     * Метод выдает все данные о пользователе по логину и паролю
     * @param login - введенный логин
     * @param password - введенный пароль
     * @return - существующий пользователь
     */

    public User getUser(String login, String password) {
        User user = null;
        for (User existing : logic.findAll()) {
            if (existing.getLogin().equals(login) && existing.getPassword().equals(password)) {
                user = existing;
                break;
            }
        }
        return user;
    }
}
