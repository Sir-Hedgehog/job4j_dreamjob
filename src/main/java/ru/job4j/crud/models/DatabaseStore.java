package ru.job4j.crud.models;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Sir-Hedgehog (mailto:quaresma_08@mail.ru)
 * @version 6.0
 * @since 27.05.2020
 */

public class DatabaseStore implements Store {
    private static final BasicDataSource SOURCE = new BasicDataSource();
    private static final DatabaseStore INSTANCE = new DatabaseStore();
    private static final Logger LOG = LoggerFactory.getLogger(DatabaseStore.class);

    /**
     * В конструкторе происходит инициализация пула соединений с базой данных
     */

    public DatabaseStore() {
        SOURCE.setUrl("jdbc:postgresql://ec2-54-246-85-151.eu-west-1.compute.amazonaws.com:5432/d7bf3lo9hlgal");
        SOURCE.setUsername("kywjzwrlvwsgva");
        SOURCE.setPassword("729654ed03d514cd0205fc280e0bb2373008bbc12fe5619b5fbfa1fb5f458dfb");
        SOURCE.setDriverClassName("org.postgresql.Driver");
        SOURCE.setMinIdle(5);
        SOURCE.setMaxIdle(10);
        SOURCE.setMaxOpenPreparedStatements(100);
    }

    /**
     * Метод дает право создать единственный экзепляр класса для взаимосвязи с логическим (валидационным) блоком проекта
     * @return - экземпляр класса DatabaseStore
     */

    public static DatabaseStore getInstance() {
        return INSTANCE;
    }

    /**
     * Метод добавляет нового пользователя в базу данных
     * @param user - новый пользователь
     * @return - успешность операции
     */

    @Override
    public boolean add(User user) {
        boolean result = false;
        try (Connection connection = SOURCE.getConnection(); PreparedStatement ps = connection.prepareStatement("INSERT INTO users(id, name, country, city, email, login, password, photoId, date_of_creation, role) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {
            ps.setInt(1, user.getId());
            ps.setString(2, user.getName());
            ps.setString(3, user.getCountry());
            ps.setString(4, user.getCity());
            ps.setString(5, user.getEmail());
            ps.setString(6, user.getLogin());
            ps.setString(7, user.getPassword());
            ps.setString(8, user.getPhotoId());
            ps.setString(9, user.getCreateDate());
            ps.setString(10, user.getRole());
            ps.executeUpdate();
            result = true;
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * Метод обновляет данные существующего пользователя в базе данных
     * @param id - идентификационный номер существующего пользователя
     * @param recent - новые данные
     * @return - успешность операции
     */

    @Override
    public boolean update(int id, User recent) {
        boolean result = false;
        try (Connection connection = SOURCE.getConnection(); PreparedStatement ps = connection.prepareStatement("UPDATE users SET name = ?, country = ?, city = ?, email = ?, login = ?, password = ?, photoId = ?, role = ? WHERE id = ?")) {
            ps.setString(1, recent.getName());
            ps.setString(2, recent.getCountry());
            ps.setString(3, recent.getCity());
            ps.setString(4, recent.getEmail());
            ps.setString(5, recent.getLogin());
            ps.setString(6, recent.getPassword());
            ps.setString(7, recent.getPhotoId());
            ps.setString(8, recent.getRole());
            ps.setInt(9, id);
            ps.executeUpdate();
            result = true;
        } catch (SQLException | IllegalStateException e) {
            LOG.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * Метод удаляет пользователя из базы данных
     * @param id - идентификационный номер существующего пользователя
     * @return - успешность операции
     */

    @Override
    public boolean delete(int id) {
        boolean result = false;
        try (Connection connection = SOURCE.getConnection(); PreparedStatement ps = connection.prepareStatement("DELETE FROM users WHERE id = ?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
            result = true;
        } catch (SQLException | IllegalStateException e) {
            LOG.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * Метод показывает список существующих пользователей
     * @return - список существующих пользователей
     */

    @Override
    public CopyOnWriteArrayList<User> findAll() {
        CopyOnWriteArrayList<User> list = new CopyOnWriteArrayList<>();
        try (Connection connection = SOURCE.getConnection(); PreparedStatement ps = connection.prepareStatement("SELECT * FROM users")) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User user = new User(rs.getString("name"), rs.getString("country"), rs.getString("city"), rs.getString("email"), rs.getString("login"), rs.getString("password"), rs.getString("photoId"), rs.getString("role"));
                user.setId(rs.getInt("id"));
                user.setCreateDate(rs.getString("date_of_creation"));
                list.add(user);
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return list;
    }

    /**
     * Метод получает данные существующего пользователя по id
     * @param id - идентификатор существующего пользователя
     * @return - данные существующего пользователя
     */

    @Override
    public User findById(int id) {
        User result = null;
        try (Connection connection = SOURCE.getConnection(); PreparedStatement ps = connection.prepareStatement("SELECT * FROM users WHERE id = ?")) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User user = new User(rs.getString("name"), rs.getString("country"), rs.getString("city"), rs.getString("email"), rs.getString("login"), rs.getString("password"), rs.getString("photoId"), rs.getString("role"));
                user.setId(rs.getInt("id"));
                user.setCreateDate(rs.getString("date_of_creation"));
                if (id == user.getId()) {
                    result = user;
                }
            }
        } catch (SQLException | IllegalStateException e) {
            LOG.error(e.getMessage(), e);
        }
        return result;
    }
}
