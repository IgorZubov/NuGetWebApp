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

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Ooops!</title>

    <%--<spring:url value="./resources/css/bootstrap.min.css" var="mainCss" />--%>
    <%--<spring:url value="./resources/css/common.css" var="commonCss" />--%>
    <%--<spring:url value="./resources/js/bootstrap.min.js" var="jqueryJs" />--%>
    <%--&lt;%&ndash;<spring:url value="/resources/js/main.js" var="mainJs" />&ndash;%&gt;--%>

    <%--<link href="${mainCss}" rel="stylesheet" />--%>
    <%--<link href="${commonCss}" rel="stylesheet" />--%>
    <%--<script src="${jqueryJs}"></script>--%>
    <%--<script src="${mainJs}"></script>--%>

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>

<body>

<div class="container">

    <h2 class="form-heading text-center">Ooops! Looks like you are not logged in.</h2>

    <h4 class="text-center"><a href="${contextPath}/login">Login with you account</a></h4>

</div>
<!-- /container -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<%--<script src="${contextPath}/resources/js/bootstrap.min.js"></script>--%>
</body>
</html>