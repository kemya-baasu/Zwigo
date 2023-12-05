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
        <th scope="col">Order Id</th>
        <th scope="col">Ordered Time</th>
        <th scope="col">Delivered Address</th>
        <th scope="col">Delivery Status</th>
        <th scope="col">Order Details</th>
      </tr>
    </thead>
    <tbody>
     <%
     CustomerUtility ordersObj=new CustomerUtility();
     ArrayList<Order> orders=ordersObj.getOrderDetails();
     for(int i=0;i<orders.size();i++){
     HashMap<String,String> orderData=orders.get(i).getOrderData();
     %>
       <tr>
        <th scope="row"><%=orderData.get("order_id")%></th>
               <td><%=orderData.get("ordered_time")%></td>
               <td><%=orderData.get("delivered_address")%></td>
               <td class="<%=orderData.get("delivery_status").equals("Delivered")?"text-success":"text-danger"%>"><%=orderData.get("delivery_status")%></td>
                <td><form action="customer" method="get">
                <input type="hidden" name="id" value="<%=orderData.get("order_id")%>">
                <button class="btn btn-info" name="page" value="getSingleOrderData">MoreInfo</button>
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
