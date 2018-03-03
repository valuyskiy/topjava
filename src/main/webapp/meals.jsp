<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>

<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles.css" type="text/css">
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>

${empty meals
    ? '<h2>Еды нет!</h2>'
    : '<table>
        <caption>Моя еда</caption>
        <tr>
            <th>Дата/Время</th>
            <th>Описание</th>
            <th>Калории</th>
        <tr>'
}

<c:forEach items="${meals}" var="meal">
    <tr class="${meal.isExceed() ? 'exceed' : 'normal'}">
        <td>${meal.getDateTime().toLocalDate()} ${meal.getDateTime().toLocalTime()}</td>
        <td>${meal.getDescription()}</td>
        <td>${meal.getCalories()}</td>
    </tr>
</c:forEach>

${empty meals ? ''  : '</table>'}

</body>
</html>