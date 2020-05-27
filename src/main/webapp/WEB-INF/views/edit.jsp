<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<jsp:useBean id="person" scope="request" type="ru.job4j.crud.models.User"/>
<%--@elvariable id="currentRole" type="java.lang.String"--%>
<%--@elvariable id="success" type="java.lang.String"--%>
<!DOCTYPE html>
<html lang="ru">
    <head>
        <meta charset='utf-8'/>
        <title>Обновление данных</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
        <script>

            /**
             * Метод проверяет поля на заполненность
             */

            function validate() {
                let result = false;
                let selectedFile = $('#selectedFile').val();
                let name = $('#name').val();
                let country = $('#country').val();
                let city = $('#city').val();
                let email = $('#email').val();
                let login = $('#login').val();
                let password = $('#password').val();
                if (!selectedFile.includes(".jpg")) {
                    alert('Выберите фото!');
                } else if (name === '') {
                    alert('Введите имя!');
                } else if (country === '---') {
                    alert('Выберите страну!');
                } else if (city === '---') {
                    alert('Выберите город!');
                } else if (email === '') {
                    alert('Введите электронную почту!');
                } else if (login === '') {
                    alert('Введите логин!');
                } else if (password === '') {
                    alert('Введите пароль!');
                } else {
                    result = true;
                }
                return result;
            }

            /**
             * Метод распознает выбранную пользователем страну и, исходя из этого выбора, формирует список городов
             */

            function recognizeCountry() {
                let country = $('select#country').val();
                let city = $('select#city');
                $.ajax({
                    type: 'GET',
                    url: 'http://localhost:8082/edit/cities',
                    data: 'country=' + country,
                    dataType: 'json',
                    success: (function(response) {
                        city.find('option').remove();
                        $.each(response, function(index, value) {
                            $('<option>').val(value).text(value).appendTo(city);
                        })
                    }),
                    error: (function(err) {
                        alert(err);
                    })
                });
            }
        </script>
        <style>
            html, body {
                height: 100%;
                color: #ffffff;
                font-family: Arial, Tahoma, sans-serif;
            }

            h2 {
                margin-top: 1.2em;
                text-transform: uppercase;
                text-align: center;
                border-top: .125rem solid #fff;
                border-bottom: .125rem solid #fff;
            }

            body {
                background: radial-gradient(at 50% 50%, #80fbfc, #0081b5);
            }

            p {
                font-size: large;
                margin-left: 40px;
            }

            input, label, #role, select {
                margin-left: 40px;
            }

            .btn-default {
                color: #000;
            }

            .form-group {
                width: 40%;
            }

            #role {
                color: #ffffff;
            }
        </style>
    </head>
    <body>
        <form action='${pageContext.request.contextPath}/edit?id=${person.id}' method='post'>
            <h2>Редактирование учетной записи</h2><br/>
            <c:if test="${success != ''}">
                <p>
                    <c:out value="${success}"/>
                </p>
            </c:if>
            <div class="form-group">
                <label for="selectedFile">Выбранное фото: </label>
                <input type="text" class="form-control" id="selectedFile" name='file' value='${person.photoId}' readonly>
            </div>
            <div class="form-group">
                <label for="name">Имя: </label>
                <input type="text" class="form-control" id="name" name="name" value='${person.name}'/>
            </div>
            <div class="form-group">
                <label for="country">Выберите страну: </label>
                <select class="form-control" name="country" id="country" onclick="recognizeCountry()">
                    <option value="empty">---</option>
                    <option value="Россия">Россия</option>
                    <option value="Украина">Украина</option>
                    <option value="Беларусь">Беларусь</option>
                </select>
            </div>
            <div class="form-group">
                <label for="city">Выберите город: </label>
                <select class="form-control" name="city" id="city">
                    <option value="empty">---</option>
                </select>
            </div>
            <div class="form-group">
                <label for="email">Email: </label>
                <input type="text" class="form-control" id="email" name="email" value='${person.email}'/>
            </div>
            <div class="form-group">
                <label for="login">Логин: </label>
                <input type="text" class="form-control" id="login" name="login" value='${person.login}'/>
            </div>
            <div class="form-group">
                <label for="password">Введите пароль: </label>
                <input type="password" class="form-control" id="password" name="password" value='${person.password}'/>
            </div>
            <c:if test='${currentRole eq "администратор"}'>
                <div class="form-group">
                    <label for="role">Выберите роль: </label>
                    <div id="role" class="btn-group btn-group-toggle" data-toggle="buttons">
                        <label for="admin" class="btn btn-secondary active">
                            <input type="radio" name="role" id="admin" value="администратор" checked="checked"/>администратор
                        </label>
                        <label for="other" class="btn btn-secondary">
                            <input type="radio" name="role" id="other" value="пользователь"/>пользователь
                        </label>
                    </div>
                </div>
            </c:if>
            <c:if test='${currentRole eq "пользователь"}'>
                <input type="hidden" name="role" value="пользователь"/>
            </c:if>
            <input class="btn btn-default" type='submit' value='Сохранить' onclick="validate();"/>
        </form>
    </body>
</html>
