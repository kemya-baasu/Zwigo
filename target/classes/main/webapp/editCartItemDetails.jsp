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
<h3>Edit Cart Items!</h3>
<%
if(request.getAttribute("successMsg")!=null){
%>
<h2><%=request.getAttribute("successMsg")%></h2>
<%
}
  HashMap<String,String> custDetails= (HashMap<String, String>) request.getSession().getAttribute("custDetails");
   %>
<form action="customer" method="post">

  <input type="hidden" name="id" value="<%=request.getParameter("id")%>">
  <div class="form-group row">
    <label for="item_name" class="control-label col-sm-2">Item Name</label>
    <div class="col-sm-4">
      <input type="text" class="form-control" id="item_name" name="item_name" placeholder="Item Name" value="<%=custDetails.get("item_name")%>" disabled>
    </div>
  </div>
  <div class="form-group row">
    <label for="cartQuantity" class="control-label col-sm-2">Quantity Selected</label>
    <div class="col-sm-4">
      <input type="number" class="form-control" id="cartQuantity" name="cartQuantity" placeholder="Price" value="<%=custDetails.get("cartquantity")%>" >
    </div>
  </div>
  <div class="form-group row">
    <label for="availableQuantity" class="control-label col-sm-2">Available Quantity</label>
    <div class="col-sm-4">
      <input type="text" class="form-control" id="availableQuantity" name="availableQuantity"  value="<%=custDetails.get("availableQuantity")%>" disabled>
    </div>
  </div>
 <div class="form-group row">
    <label for="coupon" class="control-label col-sm-2">Coupon</label>
    <div class="col-sm-4">
      <input type="text" class="form-control" id="coupon" name="coupon" placeholder="Coupon" value="<%=custDetails.get("coupon")!=null?custDetails.get("coupon"):""%>" >
    </div>
  </div>
  <div class="form-group row">
  <div class="col-sm-4">
          <button type="submit" class="btn btn-primary" name="button" value=-2>Back</button>
          </div>
    <div class="offset-sm-2 col-sm-4 pull-right">
      <button type="submit" class="btn btn-primary" name="button" value=0>Apply</button>
    </div>
  </div>
</form>
</body>
</html>