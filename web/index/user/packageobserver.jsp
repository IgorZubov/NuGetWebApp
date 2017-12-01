<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<c:set var="searchPachageHidden" value="none" scope="request" />
<jsp:include page="../fragments/header.jsp" />
<body>
<div class="container">

    <%--<c:if test="${not empty msg}">--%>
        <%--<div class="alert alert-${css} alert-dismissible" role="alert">--%>
            <%--<button type="button" class="close" data-dismiss="alert" aria-label="Close">--%>
                <%--<span aria-hidden="true">&times;</span>--%>
            <%--</button>--%>
            <%--<strong>${msg}</strong>--%>
        <%--</div>--%>
    <%--</c:if>--%>

    <h1>Package observer</h1>

    <spring:url value="/user/packageobserver" var="userActionUrl" />

    <form:form class="form-horizontal" method="post" modelAttribute="searchExp" action="${userActionUrl}">

        <spring:bind path="searchExpression">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <label class="col-sm-2 control-label">Search phrase</label>
                <div class="col-sm-10">
                    <form:input path="searchExpression" type="text" class="form-control " id="searchExpression" placeholder="Name" />
                    <form:errors path="searchExpression" class="control-label" />
                </div>
            </div>
        </spring:bind>

        <spring:bind path="feedSource">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <label class="col-sm-2 control-label">Source</label>
                <div class="col-sm-5">
                    <form:select path="feedSource" class="form-control">
                        <form:option value="" label="--- Select ---" />
                        <form:options items="${sourceList}" />
                    </form:select>
                    <form:errors path="feedSource" class="control-label" />
                </div>
                <div class="col-sm-5"></div>
            </div>
        </spring:bind>

        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button type="submit" class="btn-lg btn-primary pull-right">Search</button>
            </div>
        </div>

    </form:form>

    <table class="table table-striped">
        <thead>
        <tr>
            <th>Name</th>
            <th>Version</th>
            <th>Description</th>
            <th>Authors</th>
            <th>Tags</th>
        </tr>
        </thead>

        <c:forEach var="package" items="${packages}">
            <tr>
                <td>
                        ${package.id}
                </td>
                <td>${package.version}</td>
                <td>${package.description}</td>
                <td>${package.authors}</td>
                <td>${package.tags}</td>
            </tr>
        </c:forEach>
    </table>
</div>
<jsp:include page="../fragments/footer.jsp" />
</body>
</html>
