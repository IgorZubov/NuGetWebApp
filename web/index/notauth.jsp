<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>

    <style>
        .text-center {
            text-align: center
        }

        body {
            padding-top: 40px;
            padding-bottom: 40px;
            background-color: #eee;
        }

        h2 {
            font-size: 30px;
            margin-top: 20px;
            margin-bottom: 10px;
            font-family: inherit;
            font-weight: 500;
            line-height: 1.1;
            color: inherit;
        }

        h4 {
            font-size: 18px;
        }

        a {
            color: #337ab7;
            text-decoration: none;
            background-color: transparent;
        }

        .container {
            padding-right: 15px;
            padding-left: 15px;
            margin-right: auto;
            margin-left: auto
        }
    </style>

    <title>Ooops!</title>

</head>

<body>

<div class="container">

    <h2 class="form-heading text-center">Ooops! Looks like you are not logged in.</h2>

    <h4 class="text-center"><a href="${contextPath}/login">Login with you account</a></h4>

</div>
</body>
</html>