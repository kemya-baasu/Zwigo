<%@ page import="zwigo.classes.*,java.util.*" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Add</title>
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
        integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
</head>
<body>
<h3>Edit Existing Food Items for Sale!</h3>


<%
GetFoodItemsFromDB foodItemsObj=new GetFoodItemsFromDB();
   ArrayList<FoodItem> foodItems=foodItemsObj.getFoodItems();
   for(int i=0;i<foodItems.size();i++){
   HashMap<String,String> foodData=foodItems.get(i).getFoodData();
   String id=request.getParameter("editId");
   if(foodData.get("item_id").equals(id))
   {
   System.out.println(foodData.get("item_id")+"---item_id");
   %>
<form action="foodItems" method="post">
  <input type="hidden" name="id" value="<%=request.getParameter("editId")%>">
  <div class="form-group row">
    <label for="item_name" class="control-label col-sm-2">Item Name</label>
    <div class="col-sm-4">
      <input type="text" class="form-control" id="item_name" name="item_name" placeholder="Item Name" value="<%=foodData.get("item_name")%>">
    </div>
  </div>
  <div class="form-group row">
    <label for="price" class="control-label col-sm-2">Price</label>
    <div class="col-sm-4">
      <input type="number" class="form-control" id="price" name="item_price" placeholder="Price" value="<%=foodData.get("price")%>">
    </div>
  </div>
  <div class="form-group row">
    <label for="email" class="control-label col-sm-2">Quantity</label>
    <div class="col-sm-4">
      <input type="number" class="form-control" id="email" name="item_quantity" placeholder="Quantity" value="<%=foodData.get("quantity")%>">
    </div>
  </div>
  <div class="form-group row">
    <label for="type" class="control-label col-sm-2">Type</label>
    <div class="col-sm-4">
<select id="type" class="form-control" name="type">
    <option value="veg" <%= ("veg".equals(foodData.get("item_type"))) ? "selected" : "" %>>veg</option>
    <option value="non-veg" <%= ("non-veg".equals(foodData.get("item_type"))) ? "selected" : "" %>>non-veg</option>
  </select>
    </div>
  </div>
  <div class="form-group row">
      <label for="availablility" class="control-label col-sm-2">Available/Unavailable</label>
<div class="col-sm-4">
<select id="availablility" class="form-control" name="availablility">
    <option value="Available" <%= ("Available".equals(foodData.get("is_available"))) ? "selected" : "" %>>Available</option>
    <option value="Unavailable" <%= ("Unavailable".equals(foodData.get("is_available"))) ? "selected" : "" %>>Unavailable</option>
  </select>
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
      <option value="<%=key%>" <%= (key.equals(foodData.get("group_id"))) ? "selected" : "" %>><%=foodGroups.get(key)%></option>
      <%
      }
      %>
    </select>
    </div>
  </div>
  <div class="form-group row">
    <div class="offset-sm-2 col-sm-4 pull-right">
      <button type="submit" class="btn btn-primary" name="button" value=0>Apply</button>
    </div>
  </div>

</form>
<%
}
}
%>
</body>
</html>