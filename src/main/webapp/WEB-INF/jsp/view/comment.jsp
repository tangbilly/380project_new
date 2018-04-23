<!DOCTYPE html>
<html>
  <head>
    <title>Comment Page</title>
  </head>
  <body>
    <c:url var="logoutUrl" value="/logout"/>
    <form action="${logoutUrl}" method="post">
      <input type="submit" value="Log out" />
      <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </form>

    <form:form method="POST">   
      <form:label path="body">Item content</form:label><br/>
      <form:textarea path="body" rows="5" cols="30" /><br/><br/>

      <input type="submit" value="Submit"/>
    </form:form>
  </body>
</html>