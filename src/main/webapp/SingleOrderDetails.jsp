<%@ page import="zwigo.api.*,zwigo.classes.*,java.util.*,java.io.*"%>
<html>
<head>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
<ul class=" nav-tabs justify-content-end navbar-dark bg-dark navbar navbar-expand-lg list-unstyled">
<li class="navbar-brand mr-auto">Zwigo</li>
  <li class="nav-item">
  <form action="customer" method="get">
    <button class="btn btn-dark" name="page" value="accountDetails">Account</button>
    </form>
  </li>
  <li class="nav-item">
    <form action="customer" method="get">
      <button class="btn btn-dark" name="page" value="getOrders">Orders</button>
    </form>
    </li>
   <li class="nav-item">
  <form>
    <a class="btn btn-dark" href="customerPage.jsp">Dashboard</a>
    </form>
    </li>
 <li class="nav-item">
  <form action="customer" method="get">
       <button class="btn btn-dark" name="page" value="getCart">
       <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-cart" viewBox="0 0 16 16">
       <path d="M0 1.5A.5.5 0 0 1 .5 1H2a.5.5 0 0 1 .485.379L2.89 3H14.5a.5.5 0 0 1 .491.592l-1.5 8A.5.5 0 0 1 13 12H4a.5.5 0 0 1-.491-.408L2.01 3.607 1.61 2H.5a.5.5 0 0 1-.5-.5zM3.102 4l1.313 7h8.17l1.313-7H3.102zM5 12a2 2 0 1 0 0 4 2 2 0 0 0 0-4zm7 0a2 2 0 1 0 0 4 2 2 0 0 0 0-4zm-7 1a1 1 0 1 1 0 2 1 1 0 0 1 0-2zm7 0a1 1 0 1 1 0 2 1 1 0 0 1 0-2z">
       </path>
       </svg>Cart</button>
       </form>
  </li>
</ul>
  <table class="table">
    <thead class="thead-dark">
      <tr>
        <th scope="col">Item Name</th>
        <th scope="col">Quantity Selected</th>
        <th scope="col">MRP Price</th>
        <th scope="col">Coupon</th>
        <th scope="col">Discount</th>
        <th scope="col">Total Price</th>
      </tr>
    </thead>
    <tbody>
     <%
     CustomerUtility ordersObj=new CustomerUtility();
     ArrayList<Order> orders=ordersObj.getSingleOrderData();
     System.out.println(orders.size());
     for(int i=0;i<orders.size();i++){
     HashMap<String,String> orderData=orders.get(i).getOrderData();
     %>
       <tr>
        <th scope="row"><%=orderData.get("item_name")%></th>
               <td><%=orderData.get("quantity")%></td>
               <td><%=orderData.get("total_price")%></td>
               <td><%=orderData.get("mrp")%></td>
               <td><%=orderData.get("coupon")%></td>
               <td><%=orderData.get("discount")%></td>
               </tr>
  <%
     }
     %>
    </tbody>
  </table>
</body>
</html>
