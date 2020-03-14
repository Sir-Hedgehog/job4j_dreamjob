package ru.job4j.crud.models;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateStub implements Validate {
    private final Map<Integer, User> store = new HashMap<>();
    private int id = 0;
    private static final Pattern CHECK_OF_NAME = Pattern.compile("^(([A-Z]|[А-Я]){1}([a-z]|[а-я]){1,})$");
    private static final Pattern CHECK_OF_EMAIL = Pattern.compile("^((\\w{1,}[-._]{0,1}\\w{1,})+@(\\w{1,}[-._]{0,1}\\w{1,})+[.]{1}[a-z]{2,4})$");
    private static final Pattern CHECK_OF_LOGIN = Pattern.compile("^(\\w{1,}[-._]{0,1}\\w{1,})$");
    private static final Pattern CHECK_OF_PHOTO = Pattern.compile("^((\\w|\\W){1,})+.(gif|jpg|png|jpeg|svg)$");
    private static final Pattern CHECK_OF_PASSWORD = Pattern.compile("^(((\\w|\\W){1,})[-._]{0,1}((\\w|\\W){1,}))$");

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

    @Override
    public boolean add(User user) {
        boolean result = false;
        if (this.checkName(user) && this.checkLogin(user) && this.checkPassword(user) && this.checkEmail(user) && this.checkPassword(user)) {
            user.setId(++this.id);
            this.store.put(user.getId(), user);
            result = true;
        }
        return result;
    }

    @Override
    public boolean update(int id, User user) {
        boolean result = false;
        if (this.checkName(user) && this.checkLogin(user) && this.checkPassword(user) && this.checkEmail(user) && this.checkPassword(user)) {
            this.store.put(id, user);
            result = true;
        }
        return result;
    }

    @Override
    public boolean delete(int id) {
        boolean result = false;
        User user = this.store.remove(id);
        if (user != null) {
            result = true;
        }
        return result;
    }

    @Override
    public CopyOnWriteArrayList<User> findAll() {
        return new CopyOnWriteArrayList<>(this.store.values());
    }

    @Override
    public User findById(int id) {
        return this.store.get(id);
    }

    public boolean checkAccount(String login, String password) {
        boolean result = false;
        CopyOnWriteArrayList<User> list = new CopyOnWriteArrayList<>(this.store.values());
        for (User user : list) {
            if (user.getLogin().equals(login) && user.getPassword().equals(password)) {
                result = true;
            }
        }
        return result;
    }

    public User getUser(String login, String password) {
        User result = null;
        CopyOnWriteArrayList<User> list = new CopyOnWriteArrayList<>(this.store.values());
        for (User user : list) {
            if (user.getLogin().equals(login) && user.getPassword().equals(password)) {
                result = user;
            }
        }
        return result;
    }
}
