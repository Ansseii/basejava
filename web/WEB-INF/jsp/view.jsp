<%@ page import="com.mysite.basejava.model.TextContent" %>
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
    <h2>${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png"></a></h2>
    <p>
        <c:forEach var="contactEntry" items="${resume.allContacts}">
            <jsp:useBean id="contactEntry"
                         type="java.util.Map.Entry<com.mysite.basejava.model.ContactType, java.lang.String>"/>
            <%=contactEntry.getKey().toHtml(contactEntry.getValue())%><br/>
        </c:forEach>
    </p>
    <p>
    <table>
        <c:forEach var="sectionsEntry" items="${resume.allSections}">
            <jsp:useBean id="sectionsEntry"
                         type="java.util.Map.Entry<com.mysite.basejava.model.SectionType, com.mysite.basejava.model.Content>"/>
            <c:set var="type" value="${sectionsEntry.key}"/>
            <c:set var="content" value="${sectionsEntry.value}"/>
            <jsp:useBean id="content"
                         type="com.mysite.basejava.model.Content"/>
            <tr>
            <td><h3><a name="type.name">${type.title}</a></h3></td>
            <c:if test="${type=='PERSONAL' || type=='OBJECTIVE'}">
                <tr>
                    <td>
                        <%=((TextContent) content).getContent()%>
                    </td>
                </tr>
            </c:if>
            <c:if test="${type=='ACHIEVEMENT' || type=='QUALIFICATIONS'}">
                <tr>
                    <td>
                        <ul>
                            <c:forEach var="item" items="<%=((ListContent)content).getContent()%>">
                                <li>${item}</li>
                            </c:forEach>
                        </ul>
                    </td>
                </tr>
            </c:if>
            <c:if test="${type=='EXPERIENCE' || type=='EDUCATION'}">
                <c:forEach var="company" items="<%=((CompanyContent)content).getCompanies()%>">
                    <tr>
                        <td>
                            <h3>${company.link.name}</h3>
                        </td>
                    </tr>
                    <c:forEach var="period" items="${company.periods}">
                        <tr>
                            <td><b>${period.startDate}<br>${period.endDate}</b></td>
                            <td><b>${period.title}</b><br>${period.description}</td>
                        </tr>
                    </c:forEach>
                </c:forEach>
            </c:if>
            </tr>
        </c:forEach>
    </table>
    </p>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>