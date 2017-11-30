<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<head>
    <title>Spring MVC Form Handling Example</title>

    <spring:url value="/resources/css/hello.css" var="coreCss" />
    <spring:url value="/resources/css/bootstrapforms.min.css"
                var="bootstrapCss" />
    <link href="${bootstrapCss}" rel="stylesheet" />
    <link href="${coreCss}" rel="stylesheet" />
</head>

<spring:url value="/" var="urlHome" />
<spring:url value="/user/addFeed" var="urlAddFeed" />
<spring:url value="/user/addPackage" var="urlAddPackage" />
<spring:url value="/user/logout" var="logout" />

<nav class="navbar navbar-inverse ">
    <div class="container">
        <div class="navbar-header">
            <a class="navbar-brand" href="${urlHome}">Spring MVC Form</a>
        </div>
        <div id="navbar">
            <ul class="nav navbar-nav navbar-right">
                <li class="active"><a href="${urlAddFeed}">Add Feed</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li class="active"><a href="${urlAddPackage}">Add Package</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li class="active"><a href="${logout}">Log Out</a></li>
            </ul>
        </div>
    </div>
</nav>