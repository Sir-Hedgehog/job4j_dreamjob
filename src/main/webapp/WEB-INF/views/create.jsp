<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<jsp:useBean id='file' scope="request" type="java.lang.String"/>
<%--@elvariable id="success" type="java.lang.String"--%>
<!DOCTYPE html>
<html lang="ru">
    <head>
        <meta charset='utf-8'/>
        <title>Создание аккаунта</title>
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@9.10.12/dist/sweetalert2.all.min.js" crossorigin="anonymous"></script>
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
                    url: 'http://localhost:8082/create/cities',
                    data: 'country=' + country,
                    dataType: "json",
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

            /**
             * Метод проверяет размер выбранной пользователем аватарки на превышение максимального лимита
             */

            function validateFile() {
                let size = 0;
                let image = $('#image').prop('files')[0];
                let formData = new FormData();
                formData.append("image", image);
                for (let pair of formData.entries()) {
                    if (pair[1] instanceof Blob)
                        size += pair[1].size;
                    else
                        size += pair[1].length;
                }
                if (size > 256000) {
                    swal.fire({
                        icon: "warning",
                        title: "Внимание!",
                        text: "Превышен максимальный лимит по размеру аватарки, объем которой должен составлять не более 256кБ! Выберите другое фото.",
                        confirmButtonColor: '#23a843',
                    });
                }
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

            p {
                font-size: large;
                margin-left: 40px;
            }

            body {
                background: radial-gradient(at 50% 50%, #80fbfc, #0081b5);
            }

            #checkbox {
                display: inline-block;
                margin-right: 10px;
                width: 32%;
            }

            input, label, #role, select {
                margin-left: 40px;
            }

            .btn-default {
                color: #000;
            }

            #paste {
                margin-bottom: 0.2em;
            }

            .form-group {
                width: 40%;
            }
        </style>
    </head>
    <body>
        <form action='${pageContext.request.contextPath}/upload' method='post' enctype='multipart/form-data'>
            <h2>Создание учетной записи</h2><br/>
            <c:if test="${success != ''}">
                <p>
                    <c:out value="${success}"/>
                </p>
            </c:if>
            <div class="form-group" id='checkbox'>
                <label for="file">Выберите фото: </label>
                <input type="file" class="form-control" id="file" name="file"/>
            </div>
            <input id="paste" class="btn btn-default" type='submit' onclick="validateFile()" value='Прикрепить'/>
        </form>
        <form action='${pageContext.request.contextPath}/create' method='post'>
            <div class="form-group">
                <label for="selectedFile">Выбранное фото: </label>
                <input type="text" class="form-control" id="selectedFile" value='${file}' readonly>
            </div>
            <div class="form-group">
                <label for="name">Введите имя: </label>
                <input type="text" class="form-control" id="name" name="name">
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
                <label for="email">Введите email: </label>
                <input type="text" class="form-control" id="email" name="email">
            </div>
            <div class="form-group">
                <label for="login">Введите логин: </label>
                <input type="text" class="form-control" id="login" name="login">
            </div>
            <div class="form-group">
                <label for="password">Введите пароль: </label>
                <input type="password" class="form-control" id="password" name="password">
            </div>
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
            <input type='hidden' name='file' value='${file}'/>
            <input class="btn btn-default" type='submit' value='Добавить' onclick="validate();"/>
        </form>
    </body>
</html>
