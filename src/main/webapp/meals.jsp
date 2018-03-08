<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>

<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles.css" type="text/css">
    <title>Подсчет коллорий</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>

<c:if test="${not empty errorMessage}">
    <h2 class="error" align="center">${errorMessage}</h2>
</c:if>

<b>${empty editMeal.getId() ? '<p class="add">Добавить запись</p>'  : '<p class="edit">Редактировать запись</p>'}</b>

<form method="post" action="${pageContext.request.contextPath}/meals?action=edit" name="meal">
    <input type="hidden" name="id" value="${editMeal.getId()}"/>

    <table class="form">
        <tr class="form">
            <td>Дата время:</td><td> <input type="datetime-local" name="dateTime" value="${editMeal.getDateTime()}"/></td>
        </tr>
        <tr>
            <td>Описание:</td><td><input name="description" value="${editMeal.getDescription()}"/></td>
        </tr>
        <tr>
            <td>Калории:</td><td><input name="calories" value="${editMeal.getCalories()}"/></td>
        </tr>
    </table>
    <br>
    <button type="submit">Сохранить</button>
    <button type="reset" onclick="window.location.href='meals'">Отмена</button>
</form>

${empty meals
    ? '<h2>Еды нет!</h2>'
    : '<table class="list">
        <caption class="list">Моя еда</caption>
        <tr>
            <th>Дата/Время</th>
            <th>Описание</th>
            <th>Калории</th>
            <th></th>
            <th></th>
        </tr>'
}

<c:forEach items="${meals}" var="meal">
    <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.model.MealWithExceed"/>
    <tr class="${meal.exceed ? 'exceed' : 'normal'}">
        <td>${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}</td>
        <td>${meal.description}</td>
        <td>${meal.calories}</td>
        <td><a href="meals?action=edit&id=${meal.id}">Edit</a></td>
        <td><a href="meals?action=delete&id=${meal.id}">Delete</a></td>
    </tr>
</c:forEach>

${empty meals ? ''  : '</table>'}

</body>
</html>