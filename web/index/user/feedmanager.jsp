<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<jsp:include page="../fragments/header.jsp" />
<body>
<div class="container">

    <c:if test="${not empty msg}">
        <div class="alert alert-${css} alert-dismissible" role="alert">
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
            <strong>${msg}</strong>
        </div>
    </c:if>

    <h1>All Feeds</h1>

    <table class="table table-striped">
        <thead>
        <tr>
            <th>Name</th>
            <th>Source</th>
            <th>Action</th>
        </tr>
        </thead>

        <c:forEach var="feed" items="${feeds}">
            <tr>
                <td>
                        ${feed.feedName}
                </td>
                <td>${feed.feedSource}</td>
                <td>
                    <spring:url value="/user/feedmanager/${feed.id}/delete" var="deleteUrl" />
                    <spring:url value="/user/feedmanager/${feed.id}/update" var="updateUrl" />
                    <spring:url value="/user/feedmanager/${feed.id}/sync" var="syncUrl" />

                    <%--<button class="btn btn-primary" onclick="location.href='${updateUrl}'">Update</button>--%>
                    <button class="btn btn-primary" onclick="post('${updateUrl}')">Update</button>
                    <button class="btn btn-primary" onclick="post('${syncUrl}')">Sync</button>
                    <button class="btn btn-danger" onclick="this.disabled=true;post('${deleteUrl}')">Delete</button></td>
            </tr>
        </c:forEach>
    </table>
</div>
<jsp:include page="../fragments/footer.jsp" />
</body>
</html>
