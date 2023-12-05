<%@ page import="zwigo.api.*,zwigo.classes.*,java.util.*,java.io.*,java.sql.*,javax.servlet.*"%>
<html>
<head>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
<ul class="sticky-top nav-tabs justify-content-end navbar-dark bg-dark navbar navbar-expand-lg list-unstyled" style="margin-bottom: 0px;border-bottom-width: 0px;">
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
  <form action="customer" method="get">
       <button class="btn btn-dark" name="page" value="getCart">
       <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-cart" viewBox="0 0 16 16">
       <path d="M0 1.5A.5.5 0 0 1 .5 1H2a.5.5 0 0 1 .485.379L2.89 3H14.5a.5.5 0 0 1 .491.592l-1.5 8A.5.5 0 0 1 13 12H4a.5.5 0 0 1-.491-.408L2.01 3.607 1.61 2H.5a.5.5 0 0 1-.5-.5zM3.102 4l1.313 7h8.17l1.313-7H3.102zM5 12a2 2 0 1 0 0 4 2 2 0 0 0 0-4zm7 0a2 2 0 1 0 0 4 2 2 0 0 0 0-4zm-7 1a1 1 0 1 1 0 2 1 1 0 0 1 0-2zm7 0a1 1 0 1 1 0 2 1 1 0 0 1 0-2z">
       </path>
       </svg>Cart</button>
       </form>
  </li>
</ul>
<%
CustomerUtility customerUtility=new CustomerUtility();
HashMap<String,ArrayList<FoodItem>> temp=customerUtility.getGroupMaps();
 TreeMap<String, ArrayList<FoodItem>> groupedItems = new TreeMap<>(temp);
for (String key:groupedItems.keySet()) {
%>
<div class="card" style="border-width: 0px;">
  <div class="card-header text-light" style="background-color:#696969;border-radius:0px;">
    <b><%=key%></b>
  </div>
  <div class="card-body" style="background-color:  #c6ccd3;">
<div class="row row-cols-1 row-cols-md-2 row-cols-lg-4 mb-4 ">
<%
    ArrayList<FoodItem> items=groupedItems.get(key);
    for (int i = 0; i <items.size() ; i++) {
        HashMap<String,String> itemData=items.get(i).getFoodData();
%>
<div class="col mb-4" style="padding-bottom: 30px;padding-left: 30px;">
<div class="card bg-light">
  <div class="card-header">
    <%=itemData.get("item_type")%>
  </div>
  <div class="card-body">
    <h5 class="card-title"><%=itemData.get("item_name")%></h5>
    <p class="card-text">Price              : <%=itemData.get("price")%></p>
    <p class="card-text">Quantity Available : <%=itemData.get("quantity")%></p>
    <%
    String disable="";
    if(Integer.parseInt(itemData.get("quantity"))<=0){
    disable="disabled";
    }
    %>
    <form action="customer" method="post">
    <input type="hidden" name="item_id" value="<%=itemData.get("item_id")%>" required>
      <input type="number" name="quantity" placeholder="Quantity" max="<%=itemData.get("quantity")%>" min="1" required <%=disable%>>
    <button class="btn btn-dark <%=disable%>" name="button" value="2" <%=disable%>>Add To Cart</button>
    </form>
  </div>
</div>
</div>
<%
}
%>
</div>
</div>
</div>
<%
}
%>
</body>
</html>