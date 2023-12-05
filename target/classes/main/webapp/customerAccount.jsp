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
<h3>Account Details!</h3>
<%
if(request.getAttribute("successMsg")!=null){
%>
<h2><%=request.getAttribute("successMsg")%></h2>
<%
}
        HashMap<String,String> accountDetails= (HashMap<String, String>) request.getSession().getAttribute("accountInfo");
   %>
<form action="customer" method="post">

  <input type="hidden" name="id" value="<%=request.getSession().getAttribute("username")%>">
  <div class="form-group row">
    <label for="customer_name" class="control-label col-sm-2">Customer Name</label>
    <div class="col-sm-4">
      <input type="text" class="form-control" id="customer_name" name="customerName" placeholder="Customer Name" value="<%=accountDetails.get("name")%>">
    </div>
  </div>
  <div class="form-group row">
    <label for="address" class="control-label col-sm-2">Address</label>
    <div class="col-sm-4">
      <input type="text" class="form-control" id="address" name="address" placeholder="Address" value="<%=accountDetails.get("address")%>">
    </div>
  </div>
  <div class="form-group row">
    <label for="phoneNumber" class="control-label col-sm-2">Phone Number</label>
    <div class="col-sm-4">
      <input type="number" class="form-control" id="phoneNumber" name="phoneNumber" placeholder="phoneNumber" value="<%=accountDetails.get("phone_number")%>">
    </div>
  </div>
   <div class="form-group row">
<div class="col-sm-4">
        <button type="submit" class="btn btn-primary" name="button" value=-1>Back</button>
        </div>
        <div class="col-sm-4">
            <button type="submit" class="btn btn-primary" name="button" value=1>Apply Changes</button>
        </div>
    </div>
</form>
</body>
</html>