<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%--@elvariable id="error" type="java.lang.String"--%>
<!DOCTYPE html>
<html lang="ru">
    <head>
        <meta charset='utf-8'/>
        <title>Создание аккаунта</title>
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
        <style>
            html, body {
                height: 100%;
                text-align: center;
            }

            body {
                display: flex;
                flex-direction: column;
                background: radial-gradient(at 50% 50%, #80fbfc, #0081b5);
                font-family: Arial, Tahoma, sans-serif;
            }

            section {
                display: flex;
                flex-direction: column;
                align-items: center;
                justify-content: center;
                flex-grow: 15;
            }

            footer {
                flex-grow: 1;
            }

            header h1, footer p {
                color: #fff;
                padding-top: .125rem;
                padding-bottom: .125rem;
                border-top: .125rem solid #fff;
                border-bottom: .125rem solid #fff;
                text-transform: uppercase;
                text-align: center;
            }

            h3 {
                color: #0081b5;
                text-transform: uppercase;
            }

            table {
                background-color: #fff;
                border-radius: 10px 10px 10px 10px;
                border: 3px solid #fff;
            }

            table td {
                padding: 0;
                margin: 0;
                border-bottom: 3px solid #fff;
            }

            .field, #send {
                border: 3px solid #fff;
            }

            .field {
                background-color: #f4f5fa;
                border-right: 3px solid #fff;
                padding: 8px 6px;
            }

            #send {
                width: 100%;
                height: 100%;
                padding: 8px 0;
            }

            .text {
                text-align: right;
                color: #0081b5;
                padding: 8px 6px;
                background-color: #fff;
            }

            a {
                text-decoration: none;
                color: #0081b5;
            }

            a:hover {
                text-decoration: underline;
            }
        </style>
    </head>
    <body>
        <header>
            <h1>Добро пожаловать на наш сайт!</h1>
        </header>
        <section>
            <c:if test="${error != ''}">
                <p>
                    <c:out value="${error}"/>
                </p>
            </c:if>
            <h3>
                Вход в систему
            </h3>
            <form action='' method='post'>
                <table cellspacing="0">
                    <tr>
                        <td class="text">Логин </td>
                        <td>
                            <label>
                                <input id="login" class="field" type='text' placeholder='login' name='login' required/>
                            </label>
                        </td>
                    </tr>
                    <tr>
                        <td class="text">Пароль </td>
                        <td>
                            <label>
                                <input id="password" class="field" type='password' placeholder='password' name='password' required/>
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
        </section>
        <footer>
            <p>
                Всё бесплатно!
            </p>
        </footer>
    </body>
</html>
