<%@ page import="com.mysite.basejava.model.ContactType" %>
<%@ page import="com.mysite.basejava.model.SectionType" %>
<%@ page import="com.mysite.basejava.model.ListContent" %>
<%@ page import="com.mysite.basejava.model.CompanyContent" %>
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
        <c:forEach var="value" items="<%=SectionType.values()%>">
            <c:set var="content" value="${resume.getSection(value)}"/>
            <jsp:useBean id="content" type="com.mysite.basejava.model.Content"/>
            <h3>${value.title}</h3>
            <c:choose>
                <c:when test="${value == 'OBJECTIVE' || value == 'PERSONAL'}">
                    <input type="text" name="${value}" size="50" value="<%=content%>">
                </c:when>
                <c:when test="${value == 'ACHIEVEMENT' || value == 'QUALIFICATIONS'}">
                    <textarea name="${value}" cols="50"
                              rows=5><%=String.join("\n", ((ListContent) content).getContent())%></textarea>
                </c:when>
                <c:when test="${value == 'EXPERIENCE' || value == 'EDUCATION'}">
                    <c:forEach var="company" items="<%=((CompanyContent)content).getCompanies()%>" varStatus="counter">
                        <dl>
                            <dt>Company Name</dt>
                            <dd><input type="text" name="${value}" size="50" value="${company.link.name}"></dd>
                        </dl>
                        <c:forEach var="period" items="${company.periods}">
                            <jsp:useBean id="period" type="com.mysite.basejava.model.Company.Period"/>
                            <dl>
                                <dt>Start date</dt>
                                <dd><input type="text" name="${type}${counter.index}startDate"
                                           value="<%=period.getStartDate()%>"></dd>
                                <dt>End date</dt>
                                <dd><input type="text" name="${type}${counter.index}endDate"
                                           value="<%=period.getEndDate()%>"></dd>
                                <dt>Title</dt>
                                <dd><input type="text" name="${type}${counter.index}title"
                                           value="<%=period.getTitle()%>"></dd>
                                <dt>Description</dt>
                                <dd><input type="text" name="${type}${counter.index}description"
                                           value="<%=period.getDescription()%>"></dd>
                            </dl>
                        </c:forEach>
                    </c:forEach>
                </c:when>
            </c:choose>
        </c:forEach>
        <hr>
        <button type="submit">Сохранить</button>
        <button onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
