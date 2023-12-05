<!DOCTYPE html>
<%@ page import="zwigo.classes.*,java.util.*,java.io.*,javax.servlet.http.*" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Add</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
</head>
<body>
<h3>Add New Group for Food Items!</h3>
<form action="foodItems" method="post">
    <div class="form-group row">
            <label for="group" class="control-label col-sm-2">Item Group</label>
      <div class="col-sm-4">
      <select id="group" class="form-control" name="group">
       <option value="">Select Group</option>
      <%
      AdminUtility aUtility=new AdminUtility();
      HashMap<String,String> foodGroups=aUtility.getItemGroups();
      for(String key:foodGroups.keySet()){
      %>
          <option value="<%=key%>"><%=foodGroups.get(key)%></option>
          <%
          }
          %>
        </select>
        </div>
      </div>
    <div class="form-group row">
        <div class="offset-sm-2 col-sm-4 pull-right">
            <button type="submit" class="btn btn-primary" name="button" value="deleteGroup">Delete Group</button>
        </div>
    </div>
</form>
</body>
</html>