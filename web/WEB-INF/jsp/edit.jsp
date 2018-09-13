<%@ page import="com.mysite.basejava.model.CompanyContent" %>
<%@ page import="com.mysite.basejava.model.ContactType" %>
<%@ page import="com.mysite.basejava.model.ListContent" %>
<%@ page import="com.mysite.basejava.model.SectionType" %>
<%@ page import="com.mysite.basejava.util.DateUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="com.mysite.basejava.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <dt>Имя:</dt>
            <dd><input type="text" name="fullName" size=50 value="${resume.fullName}"></dd>
        </dl>
        <h3>Контакты:</h3>
        <c:forEach var="type" items="<%=ContactType.values()%>">
            <dl>
                <dt>${type.title}</dt>
                <dd><input type="text" name="${type.name()}" size=30 value="${resume.getContact(type)}"></dd>
            </dl>
        </c:forEach>
        <h3>Секции:</h3>
        <c:forEach var="type" items="<%=SectionType.values()%>">
            <c:set var="content" value="${resume.getSection(type)}"/>
            <jsp:useBean id="content" type="com.mysite.basejava.model.Content"/>
            <h3><a>${type.title}</a></h3>
            <c:choose>
                <c:when test="${type=='OBJECTIVE' || type=='PERSONAL'}">
                    <textarea name='${type}' cols=50 rows=5><%=content%></textarea>
                </c:when>
                <c:when test="${type=='QUALIFICATIONS' || type=='ACHIEVEMENT'}">
                    <textarea name='${type}' cols=50
                              rows=5><%=String.join("\n", ((ListContent) content).getContent())%></textarea>
                </c:when>
                <c:when test="${type=='EXPERIENCE' || type=='EDUCATION'}">
                    <c:forEach var="company" items="<%=((CompanyContent) content).getCompanies()%>"
                               varStatus="counter">
                        <dl>
                            <dt>Организация:</dt>
                            <dd><input type="text" name='${type}' size=100 value="${company.link.name}"></dd>
                        </dl>
                        <dl>
                            <dt>URL:</dt>
                            <dd><input type="text" name='${type}url' size=100 value="${company.link.name}"></dd>
                            </dd>
                        </dl>
                        <c:forEach var="period" items="${company.periods}">
                            <jsp:useBean id="period" type="com.mysite.basejava.model.Company.Period"/>
                            <dl>
                                <dt>Начало:</dt>
                                <dd>
                                    <input type="text" name="${type}${counter.index}startDate"
                                           value="<%=DateUtil.format(period.getStartDate())%>" placeholder="MM/yyyy">
                                </dd>
                            </dl>
                            <dl>
                                <dt>Конец:</dt>
                                <dd>
                                    <input type="text" name="${type}${counter.index}endDate"
                                           value="<%=DateUtil.format(period.getEndDate())%>" placeholder="MM/yyyy">
                            </dl>
                            <dl>
                                <dt>Должность:</dt>
                                <dd><input type="text" name='${type}${counter.index}title' size=50
                                           value="${period.title}">
                            </dl>
                            <dl>
                                <dt>Описание:</dt>
                                <dd><textarea name="${type}${counter.index}description" rows=5
                                              cols=75>${period.description}</textarea></dd>
                            </dl>
                        </c:forEach>
                    </c:forEach>
                </c:when>
            </c:choose>
        </c:forEach>
        <button type="submit">Сохранить</button>
        <button onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
