<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%--@elvariable id="error" type="java.lang.String"--%>
<!DOCTYPE html>
<html lang="ru">
    <head>
        <title>Создание аккаунта</title>
        <meta charset='utf-8'/>
        <style>
            html, body {
                height: 100%;
                text-align: center;
            }

            body {
                display: flex;
                flex-direction: column;
            }

            article {
                display: flex;
                flex-direction: column;
                align-items: center;
                justify-content: center;
                flex-grow: 15;
            }

            footer {
                flex-grow: 1;
            }

            table {
                border: none;
                border-collapse: collapse;
                border-spacing: 0;
            }

            table td {
                padding: 0;
                margin: 0;
            }

            .field {
                padding: 8px 6px;
            }

            #send {
                width: 100%;
                height: 100%;
                padding: 8px 0;
            }

            .text {
                padding: 8px 6px;
                background-color: paleturquoise;
            }
        </style>
    </head>
    <body>
        <header>
            <h1>
                Добро пожаловать на наш сайт!
            </h1>
        </header>
        <article>
            <c:if test="${error != ''}">
                <p>
                    <c:out value="${error}"/>
                </p>
            </c:if>
            <h3>
                Вход в систему:
            </h3>
            <form action='' method='post'>
                <table cellspacing="0">
                    <tr>
                        <td class="text">Логин:</td>
                        <td>
                            <label>
                                <input class="field" type='text' required placeholder='login' name='login'/>
                            </label>
                        </td>
                    </tr>
                    <tr>
                        <td class="text">Пароль:</td>
                        <td>
                            <label>
                                <input class="field" type='password' required placeholder='password' name='password'/>
                            </label>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <input id='send' type='submit' value='Отправить'/>
                        </td>
                    </tr>
                </table>
            </form>
            <p>
                <a href='${pageContext.request.contextPath}/create' title='Регистрация'>Регистрация</a>
            </p>
        </article>
        <footer>
            <p>
                Всё бесплатно!
            </p>
        </footer>
    </body>
</html>
