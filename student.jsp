<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="style.css">
</head>
<body>
<h1>Write information about student</h1>

<form action="${pageContext.request.contextPath}/student" method="post">
    <label for="firstName">first Name:</label>
    <input type="text" id="firstName" name="firstName" placeholder="Enter first Name"><br>
    <label for="lastName">last Name:</label>
    <input type="text" id="lastName" name="lastName" placeholder="Enter last Name"><br>
    <label for="age">Age:</label>
    <input type="number" id="age" name="age" placeholder="Enter age"><br>
    <label for="grooupTitle">Group:</label>
    <input type="text" id="grooupTitle" name="grooupTitle" placeholder="Enter group title"><br>
    <label for="cityName">City:</label>
    <input type="text" id="cityName" name="cityName" placeholder="Enter city"><br>
    <input type="submit" value="Send information">
</form>

<h2>List of students</h2>
<table>
    <tr>
        <th>ID</th>
        <th>First Name</th>
        <th>Last Name</th>
        <th>Age</th>
        <th>Group</th>
        <th>City</th>
        <th></th>
    </tr>
    <c:forEach var="student" items="${studentsList}">
        <tr>
            <c:if test="${not empty student and not empty student.id}">
                <td>${student.id}</td>
            </c:if>
            <td>${student.name}</td>
            <td>${student.lastname}</td>
            <td>${student.age}</td>
            <td>${student.grooup.title}</td>
            <td>${student.city.name}</td>

            <td>
                <form method="post" action="/student">
                    <input type="hidden" name="action" value="update">
                    <input type="hidden" name="id" value="${student.id}">
                    <button type="submit">Update</button>
                </form>
                <form method="post">
                    <input type="hidden" name="action" value="delete">
                    <input type="hidden" name="id" value="${student.id}">
                    <button type="submit">Delete</button>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>