<!DOCTYPE html>
<html>
    <head>
        <title>Online Bidding</title>
    </head>
    <body>
        <c:url var="logoutUrl" value="/logout"/>
        <form action="${logoutUrl}" method="post">
            <input type="submit" value="Log out" />
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>

        <h2>Bidding #${bidding.id}: <c:out value="${bidding.itemsubject}" /></h2>
        <security:authorize access="hasRole('ADMIN') or principal.username=='${bidding.customerName}'">            
            [<a href="<c:url value="/bidding/edit/${bidding.id}" />">Edit</a>]
        </security:authorize>
        <security:authorize access="hasRole('ADMIN')">            
            [<a href="<c:url value="/bidding/delete/${bidding.id}" />">Delete</a>]
        </security:authorize>
        <br /><br />
        <i>Customer Name - <c:out value="${bidding.customerName}" /></i><br /><br />
        Description: <c:out value="${bidding.body}" /><br /><br />
        Price: $<c:out value="${bidding.price}" /><br /><br />
        <c:if test="${fn:length(bidding.attachments) > 0}">
            Attachments:
            <c:forEach items="${bidding.attachments}" var="attachment"
                       varStatus="status">
                <c:if test="${!status.first}">, </c:if>
                <a href="<c:url value="/bidding/${bidding.id}/attachment/${attachment.name}" />">
                    <c:out value="${attachment.name}" /></a>
            </c:forEach><br /><br />
        </c:if>
        <a href="<c:url value="/bidding/list" />">Return to bidding list.</a>
    </body>
</html>