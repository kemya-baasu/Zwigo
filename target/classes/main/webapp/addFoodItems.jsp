<%@ page import="zwigo.classes.*,java.util.*,java.io.*" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Add</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
</head>
<body>
<h3>Add New Food Items for Sale!</h3>
<form action="foodItems" method="post">
    <div class="form-group row">
        <label for="item_name" class="control-label col-sm-2">Item Name</label>
        <div class="col-sm-4">
            <input type="text" class="form-control" id="item_name" name="item_name" placeholder="Item Name">
        </div>
    </div>
    <div class="form-group row">
        <label for="price" class="control-label col-sm-2">Price</label>
        <div class="col-sm-4">
            <input type="number" class="form-control" id="price" name="item_price" placeholder="Price">
        </div>
    </div>
    <div class="form-group row">
        <label for="email" class="control-label col-sm-2">Quantity</label>
        <div class="col-sm-4">
            <input type="number" class="form-control" id="email" name="item_quantity" placeholder="Quantity">
        </div>
    </div>
    <div class="form-group row">
        <label for="type" class="control-label col-sm-2">Type</label>
        <div class="col-sm-4">
            <input type="text" class="form-control" id="type" name="item_type" placeholder="Type">
        </div>
    </div>
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
            <button type="submit" class="btn btn-primary" name="button" value="addItem">Add Item</button>
        </div>
    </div>

</form>
</body>
</html>