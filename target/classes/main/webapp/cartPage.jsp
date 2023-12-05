<%@ page import="zwigo.api.*,zwigo.classes.*,java.util.*,java.io.*"%>
<html>
<head>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
<ul class=" nav-tabs justify-content-end navbar-dark bg-dark navbar navbar-expand-lg list-unstyled">
<li class="navbar-brand mr-auto">Zwigo</li>
 <li class="nav-item">
 <div class="text-right">
         <form action="customer" method="post">
           <button type="submit" class="btn btn-success" name="button" value=3>Check Out</button>
           </form>
         </div>
         </li>
  <li class="nav-item">
  <form action="customer" method="get">
    <button class="btn btn-dark" name="page" value="accountDetails">Account</button>
    </form>
  </li>
   <li class="nav-item">
  <form>
    <a class="btn btn-dark" href="customer">Dashboard</a>
    </form>
    </li>
  <li class="nav-item">
  <form action="customer" method="get">
    <button class="btn btn-dark" name="page" value="getOrders">Orders</button>
  </form>
  </li>
</ul>

<p><b>Note</b> : Unavailable Items and Items With Selected Quantity Greater than the Available Quantity Will be Removed Automatically While Checking Out!</p>
  <table class="table">
    <thead class="thead-dark">
      <tr>
        <th scope="col">Item Name</th>
        <th scope="col">Quantity Selected</th>
        <th scope="col">MRP Price</th>
        <th scope="col">Total Price</th>
        <th scope="col">Availability</th>
        <th scope="col">Discount</th>
        <th scope="col">Coupon</th>
        <th scope="col">Edit/Add Coupon</th>
        <th scope="col">Delete</th>
      </tr>
    </thead>
    <tbody>
     <%
     CustomerUtility customerUtilityObj=new CustomerUtility();
     ArrayList<CartItem> items=customerUtilityObj.getCustomerCartItems();
     for(int i=0;i<items.size();i++){
     HashMap<String,String> cartData=items.get(i).getCartItemData();
     %>
       <tr>
        <th scope="row"><%=cartData.get("item_name")%></th>
               <td><%=cartData.get("cartQuantity")%></td>
               <td><%=cartData.get("price")%></td>
               <td><%=cartData.get("total_price")%></td>
               <td><%=cartData.get("is_available")%></td>
               <td><%=cartData.get("discount")!=null?cartData.get("discount"):"--"%></td>
               <td><%=cartData.get("coupon")!=null?cartData.get("coupon"):"--"%></td>
               <td><form action="customer" method="post">
               <input type="hidden" name="id" value="<%= cartData.get("item_id") %>">
               <button class="btn btn-primary" name="button" value=-4>Edit</button></td>
               <td><button class="btn btn-danger" name="button" value=-3>Delete</button>
               </form>
               </td>
               </tr>
  <%
     }
     %>
    </tbody>
  </table>
</body>
</html>
