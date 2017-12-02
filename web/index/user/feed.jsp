<%@ page session="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="en">

<c:set var="addFeedHidden" value="none" scope="request" />
<jsp:include page="../fragments/header.jsp" />

<div class="container">

    <c:choose>
        <c:when test="${feedItem['new']}">
            <h1>Add Feed</h1>
        </c:when>
        <c:otherwise>
            <h1>Update Feed</h1>
        </c:otherwise>
    </c:choose>
    <br />

    <spring:url value="/user/addFeed" var="userActionUrl" />

    <form:form class="form-horizontal" method="post" modelAttribute="feedItem" action="${userActionUrl}">

        <form:hidden path="id" />

        <spring:bind path="feedName">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <label class="col-sm-2 control-label">Name</label>
                <div class="col-sm-10">
                    <form:input path="feedName" type="text" class="form-control " id="feedName" placeholder="Name" />
                    <form:errors path="feedName" class="control-label" />
                </div>
            </div>
        </spring:bind>

        <spring:bind path="feedSource">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <label class="col-sm-2 control-label">Source</label>
                <div class="col-sm-10">
                    <form:input path="feedSource" class="form-control" id="feedSource" placeholder="Source" />
                    <form:errors path="feedSource" class="control-label" />
                </div>
            </div>
        </spring:bind>

        <spring:bind path="apiKey">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <label class="col-sm-2 control-label">Api Key</label>
                <div class="col-sm-10">
                    <form:input path="apiKey" class="form-control" id="apiKey" placeholder="Api Key" />
                    <form:errors path="apiKey" class="control-label" />
                </div>
            </div>
        </spring:bind>

        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <c:choose>
                    <c:when test="${feedItem['new']}">
                        <button type="submit" class="btn-lg btn-primary pull-right">Add</button>
                    </c:when>
                    <c:otherwise>
                        <button type="submit" class="btn-lg btn-primary pull-right">Update</button>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </form:form>

</div>

<jsp:include page="../fragments/footer.jsp" />

</body>
</html>