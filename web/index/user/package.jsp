<%@ page session="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="en">

<spring:url value="../resources/css/demo.css" var="demoCss" />
<spring:url value="../resources/css/normalize.css" var="normalizeCss" />
<spring:url value="../resources/css/component.css" var="componentCss" />
<spring:url value="../resources/js/custom-file-input.js" var="custmFile" />
<spring:url value="../resources/js/jquery.custom-file-input.js" var="jqueryJs" />
<spring:url value="../resources/js/jquery-v1.min.js" var="vJs" />
<%--<spring:url value="/resources/js/main.js" var="mainJs" />--%>

<link href="${demoCss}" rel="stylesheet" />
<link href="${normalizeCss}" rel="stylesheet" />
<link href="${componentCss}" rel="stylesheet" />

<%--<script src="${jqueryJs}"></script>--%>
<%--<script src="${vJs}"></script>--%>

<jsp:include page="../fragments/header.jsp" />

<div class="container">

    <h1>Add Package</h1>
    <br />

    <spring:url value="/user/addPackage" var="userActionUrl" />

    <form:form class="form-horizontal" method="post" modelAttribute="packageItem" enctype="multipart/form-data" action="${userActionUrl}">

        <spring:bind path="file">

            <div class="form-group ${status.error ? 'has-error' : ''}">
                <label class="col-sm-2 control-label">Package</label>
                <div class="col-sm-5">
                    <div class="box">
                        <form:input type="file" name="file" id="file" class="inputfile inputfile-1"
                               data-multiple-caption="{count} files selected"  path="file"/>
                        <label for="file" >
                            <svg xmlns="http://www.w3.org/2000/svg" width="20" height="17" viewBox="0 0 20 17">
                                <path d="M10 0l-5.2 4.9h3.3v5.1h3.8v-5.1h3.3l-5.2-4.9zm9.3 11.5l-3.2-2.1h-2l3.4 2.6h-3.5c-.1 0-.2.1-.2.1l-.8 2.3h-6l-.8-2.2c-.1-.1-.1-.2-.2-.2h-3.6l3.4-2.6h-2l-3.2 2.1c-.4.3-.7 1-.6 1.5l.6 3.1c.1.5.7.9 1.2.9h16.3c.6 0 1.1-.4 1.3-.9l.6-3.1c.1-.5-.2-1.2-.7-1.5z"/></svg>
                            <span>Choose a file&hellip;</span>
                        </label>
                    </div>
                    <%--<form:input path="file" type="file" class="form-control " id="file" placeholder="Select package" />--%>
                    <form:errors path="file" class="control-label" />
                </div>
                <div class="col-sm-5"></div>
            </div>
        </spring:bind>

        <spring:bind path="source">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <label class="col-sm-2 control-label">Source</label>
                <div class="col-sm-5">
                    <form:select path="source" class="form-control">
                        <form:option value="" label="--- Select ---" />
                        <form:options items="${sourceList}" />
                    </form:select>
                    <form:errors path="source" class="control-label" />
                </div>
                <div class="col-sm-5"></div>
            </div>
        </spring:bind>

        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button type="submit" class="btn-lg btn-primary pull-right">Add</button>
            </div>
        </div>
    </form:form>

</div>

<jsp:include page="../fragments/footer.jsp" />
<script src="${custmFile}"></script>
</body>
</html>