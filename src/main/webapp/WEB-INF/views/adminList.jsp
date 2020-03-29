<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="clients" scope="request" type="java.util.List"/>
<!DOCTYPE html>
<html lang='ru'>
    <head>
        <title>Список пользователей</title>
        <meta charset='utf-8'/>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
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
                display: flex;
                flex-direction: column;
            }

            nav {
                margin-bottom: 20px;
                display: flex;
                flex-direction: column;
                justify-content: center;
                text-decoration: none;
            }

            nav ul {
                display: table;
                margin: 0;
                list-style-type: none;
                padding: 5px 0 5px 0;
            }

            nav ul li {
                font-size: medium;
                display: table-cell;
                padding: 5px 40px 5px 40px;
            }

            #add {
                text-align: left;
            }

            #out {
                text-align: right;
            }

            nav ul li a:link, nav ul li a:visited {
                border-bottom: none;
                font-weight: bold;
            }

            section {
                font-size: large;
                display: flex;
                flex-direction: column;
                align-items: center;
                justify-content: center;
            }

            .table-bordered {
                width: 96%;
                border: 3px solid #ffffff;
            }

            .data, th.contained {
                background-color: #bbbebe;
            }

            table.table-bordered tr td.data, table.table-bordered tr td.submit, table.table-bordered tr td#load, th.contained {
                vertical-align: middle;
                text-align: center;
            }

            table.table-bordered tr th, table.table-bordered tr td.submit, table.table-bordered tr td#load {
                border-right-style: hidden;
                border-top-style: hidden;
                border-bottom-style: hidden;
            }

            table.table-bordered tr th.contained {
                border: 1px solid #ddd;
                border-top: 3px #fff;
            }

            #picture, #big-border {
                border-right: 3px solid #fff;
            }

            a {
                color: #fff;
                text-decoration: underline;
            }

            .submit {
                color: #000;
            }

            input {
                margin: 3px;
            }
        </style>
    </head>
    <body>
        <header>
            <h2>Информация о пользователях</h2><br/>
        </header>
        <nav>
            <ul>
                <li id="add"><a href='${pageContext.request.contextPath}/create' title='Добавление пользователя'>Добавить пользователя</a></li>
                <li id="out"><a href='${pageContext.request.contextPath}/logout' title='Выход'>Выход</a></li>
            </ul>
        </nav>
        <section>
            <table class="table table-bordered">
                <tr>
                    <th class="contained">ID</th>
                    <th class="contained">Имя</th>
                    <th class="contained">Страна</th>
                    <th class="contained">Город</th>
                    <th class="contained">Эл.почта</th>
                    <th class="contained">Логин</th>
                    <th class="contained">Дата регистрации</th>
                    <th class="contained">Роль</th>
                    <th class="contained" id="big-border">Аватарка</th>
                    <th></th>
                    <th></th>
                    <th></th>
                <tr>
                <c:forEach items="${clients}" var="client">
                    <tr>
                        <td class="data"><c:out value="${client.id}"/></td>
                        <td class="data" id="pointOfPaste"><c:out value="${client.name}"/></td>
                        <td class="data"><c:out value="${client.country}"/></td>
                        <td class="data"><c:out value="${client.city}"/></td>
                        <td class="data"><c:out value="${client.email}"/></td>
                        <td class="data"><c:out value="${client.login}"/></td>
                        <td class="data"><c:out value="${client.createDate}"/></td>
                        <td class="data"><c:out value="${client.role}"/></td>
                        <td class="data" id="picture">
                            <img src="${pageContext.servletContext.contextPath}/download?name=${client.photoId}" width="100px" height="100px"/>
                        </td>
                        <td id='load'><a href="${pageContext.servletContext.contextPath}/download?name=${client.photoId}">Скачать картинку</a></td>
                        <td class='submit'>
                            <form action='${pageContext.request.contextPath}/edit' method='get'>
                                <input type='hidden' name='id' value='${client.id}'/>
                                <input type='submit' class="btn btn-default" value='Редактировать'>
                            </form>
                        </td>
                        <td class='submit'>
                            <form action='${pageContext.request.contextPath}/list' method='post'>
                                <input type='hidden' name='id' value='${client.id}'/>
                                <input type='submit' class="btn btn-default" value='Удалить'>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </section>
    </body>
</html>
