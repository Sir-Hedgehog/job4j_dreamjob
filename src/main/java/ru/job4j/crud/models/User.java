package ru.job4j.crud.models;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Sir-Hedgehog (mailto:quaresma_08@mail.ru)
 * @version 4.0
 * @since 29.03.2020
 */

public class User {
    private int id;
    private String name;
    private String country;
    private String city;
    private String login;
    private String email;
    private String photoId;
    private String createDate;
    private String password;
    private String role;

    public User(String name, String country, String city, String email, String login, String password, String photoId, String role) {
        this.id = ThreadLocalRandom.current().nextInt(1, 1000000);
        this.name = name;
        this.country = country;
        this.city = city;
        this.login = login;
        this.email = email;
        this.photoId = photoId.substring(photoId.lastIndexOf("\\") + 1);
        Date date = new Date();
        this.createDate = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy").format(date);
        this.password = password;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhotoId() {
        return photoId;
    }

    public void setPhotoId(String photoId) {
        this.photoId = photoId;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
