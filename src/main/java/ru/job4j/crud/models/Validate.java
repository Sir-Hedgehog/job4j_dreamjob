package ru.job4j.crud.models;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Sir-Hedgehog (mailto:quaresma_08@mail.ru)
 * @version 2.0
 * @since 12.03.2020
 */

public interface Validate {
    boolean add(User user);
    boolean update(int id, User user);
    boolean delete(int id);
    CopyOnWriteArrayList<User> findAll();
    User findById(int id);
    boolean checkAccount(String login, String password);
    User getUser(String login, String password);
}
