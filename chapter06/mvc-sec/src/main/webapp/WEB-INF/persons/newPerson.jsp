<%--
  Created by IntelliJ IDEA.
  User: iulianacosmina
  Date: 2019-07-28
  Time: 23:12
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>
        <spring:message code="home.title" />
    </title>
    <spring:theme var="cssStyle" code="css.style"/>
    <link type="text/css" rel="stylesheet" href="<c:url value="${cssStyle}" />"/>
    <link type="text/css" rel="stylesheet" href="<c:url value="/styles/general.css" />"/>
</head>
<body>
<div id="page">
    <div class="banner"></div>
    <div class="themeLocal">
        <c:choose>
            <c:when test="${requestContext.locale.language eq 'en'}">
                <c:url var="localeUrl" value="/">
                    <c:param name="locale" value="de"/>
                </c:url>
                <a href="${localeUrl}"><spring:message code="locale.de"/></a>
            </c:when>
            <c:otherwise>
                <c:url var="localeUrl" value="/">
                    <c:param name="locale" value="en"/>
                </c:url>
                <a href="${localeUrl}"><spring:message code="locale.en"/></a>
            </c:otherwise>
        </c:choose> |
        <c:choose>
            <c:when test="${requestContext.theme.name eq 'green'}">
                <c:url var="themeUrl" value="/">
                    <c:param name="theme" value="blue"/>
                </c:url>
                <a href="${themeUrl}"><spring:message code="theme.Blue"/></a>
            </c:when>
            <c:otherwise>
                <c:url var="themeUrl" value="/">
                    <c:param name="theme" value="green"/>
                </c:url>
                <a href="${themeUrl}"><spring:message code="theme.Green"/></a>
            </c:otherwise>
        </c:choose>
    </div>

    <div class="menu">
        <ul>
            <li><c:if test="${menuTab eq 'home'}">
                <strong><a href="<c:url value="/"/>"><spring:message code="menu.home"/></a></strong>
            </c:if>
                <c:if test="${menuTab != 'home'}">
                    <a href="<c:url value="/"/>"><spring:message code="menu.home"/></a>
                </c:if>
            </li>
            <li><c:if test="${menuTab eq 'persons'}">
                <strong><a href="<c:url value="/persons/list"/>"><spring:message code="menu.persons"/></a></strong>
            </c:if>
                <c:if test="${menuTab != 'persons'}">
                    <a href="<c:url value="/persons/list"/>"><spring:message code="menu.persons"/></a>
                </c:if>
                <sec:authorize access="hasRole('ROLE_ADMIN')">
                    <ul>
                        <li>
                            <c:if test="${navigationTab eq 'newPerson'}">
                                <strong>
                                    <a href="<c:url value="/persons/newPerson"/>">
                                        <spring:message code="menu.new.person"/>
                                    </a>
                                </strong>
                            </c:if>
                            <c:if test="${navigationTab != 'newPersons'}">
                                <a href="<c:url value="/persons/newPerson"/>">
                                    <spring:message code="menu.new.person"/>
                                </a>
                            </c:if>
                        </li>
                    </ul>
                </sec:authorize>
            </li>
            <li><c:if test="${menuTab eq 'detectives'}">
                <strong><a href="<c:url value="/detectives/list"/>"><spring:message code="menu.detectives"/></a></strong>
            </c:if>
                <c:if test="${menuTab != 'detectives'}">
                    <a href="<c:url value="/detectives/list"/>"><spring:message code="menu.detectives"/></a>
                </c:if>
            </li>
            <sec:authorize access="isAuthenticated()">
                <li>
                    <spring:url value="/logout" var="logoutUrl"/>
                    <form action="${logoutUrl}" id="logout" method="post">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    </form>
                    <a href="#" onclick="document.getElementById('logout').submit();"><spring:message code="menu.logout"/></a>
                </li>
            </sec:authorize>
        </ul>
    </div>

    <div class="content">
        <h2>
            <spring:message code="persons.new.title"/>
        </h2>

        <div class="person">

            <sf:form id="newPersonForm" method="POST" modelAttribute="person">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <table>
                    <tr>
                        <th>
                            <label for="firstName">
                                <span class="man">*</span> <spring:message code="label.Person.firstname"/> :
                            </label>
                        </th>
                        <td><sf:input path="firstName"/></td>
                        <td><sf:errors cssClass="error" path="firstName"/></td>
                    </tr>
                    <tr>
                        <th>
                            <label for="lastName">
                                <span class="man">*</span> <spring:message code="label.Person.lastname"/> :
                            </label>
                        </th>
                        <td><sf:input path="lastName"/></td>
                        <td><sf:errors cssClass="error" path="lastName"/></td>
                    </tr>
                    <tr>
                        <th>
                            <label for="username">
                                <span class="man">*</span> <spring:message code="label.Person.username"/> :
                            </label>
                        </th>
                        <td><sf:input path="username"/></td>
                        <td><sf:errors cssClass="error" path="username"/></td>
                    </tr>
                    <tr>
                        <th>
                            <label for="hiringDate">
                                <span class="man">*</span> <spring:message code="label.Person.since"/> :
                            </label>
                        </th>
                        <td><sf:input path="hiringDate"/></td>
                        <td><sf:errors cssClass="error" path="hiringDate"/></td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <button id="nextStep"  type="submit">
                                <spring:message code="command.save" />
                            </button>
                        </td>
                    </tr>
                </table>
            </sf:form>
    </div>

    <div class="footer">
        <sec:authorize access="isAuthenticated()">
            <p><spring:message code="user.loggedin"/>:
                <sec:authentication property="principal.username"/>
            </p>
        </sec:authorize>
        <p><spring:message code="footer.text"/></p>
    </div>
</div>
</body>
</html>