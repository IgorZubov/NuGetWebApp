<%@ page session="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="en">

<c:set var="addFeedHidden" value="none" scope="request" />
<jsp:include page="../fragments/header.jsp" />

<spring:url value="./resources/css/bootstrapforms.min.css" var="formsCss" />
<spring:url value="./resources/css/common.css" var="commonCss" />

<link href="${formsCss}" rel="stylesheet" />
<link href="${commonCss}" rel="stylesheet" />

<div class="container">

    <c:if test="${not empty msg}">
        <div class="alert alert-${css} alert-dismissible" role="alert">
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        <strong>${msg}</strong>
        </div>
    </c:if>


    <spring:url value="/user/sync" var="userActionUrl" />
    <form method="POST"action="${userActionUrl}" class="form-signin">
        <h2 class="form-heading">Synchronisation will take some time. If you would like to proceed press "Sync".</h2>

        <div class="form-group ${error != null ? 'has-error' : ''}">
            <button class="btn btn-lg btn-primary" type="submit">Sync</button>
        </div>

    </form>

</div>

<jsp:include page="../fragments/footer.jsp" />

</body>
</html>