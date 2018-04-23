<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Bidding Page</title>
  </head>
  <body>
         <c:url var="logoutUrl" value="/logout"/>
        <form action="${logoutUrl}" method="post">
            <input type="submit" value="Log out" />
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>
        
        <form:form method="POST" enctype="multipart/form-data"
                   modelAttribute="biddingForm">   
            <form:label path="price">Expected Price:</form:label><br/>
            <form:input type="text" path="price" /><br/><br/>
             <input type="submit" value="Submit"/>
             </form:form>
  </body>
</html>
