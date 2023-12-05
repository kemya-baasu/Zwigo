<%@ page import="zwigo.classes.*,java.util.*,java.io.*,javax.servlet.http.*" %>
<html>
<head>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
<script>
function checkAll(source) {
  var checkboxes = document.querySelectorAll('tbody input[type="checkbox"]');
  for (var i = 0; i < checkboxes.length; i++) {
    checkboxes[i].checked = source.checked;
  }
}
</script>
<style>
  .small-button {
    padding: 0.1rem 0.1rem;
  }
</style>
</head>
<body>
<%
Integer recordLimit = (Integer)getServletContext().getAttribute("recordLimit");
Integer startIndex=(Integer)getServletContext().getAttribute("startIndex");
Integer endIndex=(Integer)getServletContext().getAttribute("endIndex");
HttpSession sessionObj=request.getSession();
String searchId=sessionObj.getAttribute("searchById")==null?"":(String)sessionObj.getAttribute("searchById");
String searchName=sessionObj.getAttribute("searchByName")==null?"":(String)sessionObj.getAttribute("searchByName");
String searchPrice=sessionObj.getAttribute("searchByPrice")==null?"":(String)sessionObj.getAttribute("searchByPrice");
String searchQuantity=sessionObj.getAttribute("searchByQuantity")==null?"":(String)sessionObj.getAttribute("searchByQuantity");
String searchType=sessionObj.getAttribute("searchByType")==null?"":(String)sessionObj.getAttribute("searchByType");
String searchAvailable=sessionObj.getAttribute("searchByAvailability")==null?"":(String)sessionObj.getAttribute("searchByAvailability");
String searchGroup=sessionObj.getAttribute("searchByGroup")==null?"":(String)sessionObj.getAttribute("searchByGroup");
%>
<form action="foodItems" method ="post">
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <ul class="navbar-nav ">
      <li class="nav-item">
        <button class="nav-link text-dark btn-light " name="addNewItem" value="addFoodItem">Add New Item</button>
      </li>
      <li class="nav-item">
        <button class="nav-link text-dark btn-light" name="button" value="deleteButton">Delete Bulk</button>
      </li>
      <li class="nav-item">
        <button class="nav-link text-dark btn-light " name="button" value=1>Mark Available</button>
      </li>
      <li class="nav-item">
        <button class="nav-link text-dark btn-light" name="button" value=-1>Mark Unavailable</button>
      </li>
      <li class="nav-item">
        <button class="nav-link text-dark btn-light " name="addNewGroup" value="addFoodGroup">Add New Group</button>
      </li>
      <li class="nav-item">
        <button class="nav-link text-dark btn-light " name="bulkAddItems" value="bulkAddItems">Bulk Add Items</button>
      </li>
      <li class="nav-item">
        <button class="nav-link text-dark btn-light " name="deleteGroup" value="deleteGroup">Delete Item Group</button>
      </li>
    </ul>
</nav>
            <div class="form-row" id="search-form">
                <div class="col">
                    <input type="text" name="searchById" placeholder="Id" <%=searchId.isEmpty()||searchId==""?"":"value="+searchId%>>
                </div>
                <div class="col">
                   <input type="text" name="searchByName" placeholder="Name" <%=searchName.isEmpty()||searchName==""?"":"value="+searchName%>>
                </div>
                <div class="col">
                  <input type="number" name="searchByPrice" placeholder="Price" min="0" <%=searchPrice.isEmpty()||searchPrice==""?"":"value="+searchPrice%>>
                </div>
                <div class="col">
                   <input type="number" name="searchByQuantity" placeholder="Quantity" min="0" <%=searchQuantity.isEmpty()||searchQuantity==""?"":"value="+searchQuantity%>>
                </div>
                <div class="col">
                   <select id="type" name="searchByType" placeholder="Veg/Non-Veg">
                   <option value="">Select Type</option>
                       <option value="veg" <%="veg".equals(searchType)?"selected":""%>>veg</option>
                       <option value="non-veg" <%="non-veg".equals(searchType)?"selected":""%>>non-veg</option>
                     </select>
                </div>
                <div class="col">
                    <select id="type" name="searchByAvailability" placeholder="Availability">
                    <option value="">Select Availability</option>
                        <option value="Available" <%="Available".equals(searchAvailable)?"selected":""%>>Available</option>
                        <option value="Unavailable" <%="Unavailable".equals(searchAvailable)?"selected":""%>>Unavailable</option>
                    </select>
                </div>
                <div class="col">
                                   <input type="text" name="searchByGroup" placeholder="Group" <%=searchGroup.isEmpty()||searchGroup==""?"":"value="+searchGroup%>>
                                </div>
                <div class="col">
                <button type="submit" name="search" value="searchFilter" class="btn btn-dark">Search</button>
                </div>
            </div>

<table class="table">
  <thead class="thead-dark">
    <tr>
      <th><input type="checkbox" name="checkedAll" onclick="checkAll(this)"></th>
      <th scope="col">Id <button class="bg-dark text-light small-button" name="sort" value="item_id ASC"><i class="fas fa-sort-up"></i></button>
                                              <button class="bg-dark text-light small-button" name="sort" value="item_id DESC"><i class="fas fa-sort-down"></i></button></th>
      <th scope="col">Name<button class="bg-dark text-light small-button" name="sort" value="item_name ASC"><i class="fas fa-sort-up"></i></button>
                                                                        <button class="bg-dark text-light small-button"name="sort" value="item_name DESC"><i class="fas fa-sort-down"></i></button></th>
      <th scope="col">Price<button class="bg-dark text-light small-button" name="sort" value="price ASC"><i class="fas fa-sort-up"></i></button>
                                                                         <button class="bg-dark text-light small-button" name="sort" value="price DESC"><i class="fas fa-sort-down"></i></button></th>
      <th scope="col">Quantity<button class="bg-dark text-light small-button" name="sort" value="quantity ASC"><i class="fas fa-sort-up"></i></button>
                                                                            <button class="bg-dark text-light small-button" name="sort" value="quantity DESC"><i class="fas fa-sort-down"></i></button></th>
      <th scope="col">Veg/Non-Veg<button class="bg-dark text-light small-button" name="sort" value="item_type ASC"><i class="fas fa-sort-up"></i></button>
                                                                               <button class="bg-dark text-light small-button" name="sort" value="item_type DESC"><i class="fas fa-sort-down"></i></button></th>
      <th scope="col">Available/Unavailable<button class="bg-dark text-light small-button" name="sort" value="is_available ASC"><i class="fas fa-sort-up"></i></button>
                                                                                         <button class="bg-dark text-light small-button" name="sort" value="is_available DESC"><i class="fas fa-sort-down"></i></button></th>
       <th scope="col">Item Group<button class="bg-dark text-light small-button" name="sort" value="fi.group_id ASC"><i class="fas fa-sort-up"></i></button>
                                                                                                                          <button class="bg-dark text-light small-button" name="sort" value="fi.group_id DESC"><i class="fas fa-sort-down"></i></button></th>
      <th scope="col">Edit</th>
      <th scope="col">Delete</th>
    </tr>
  </thead>
  <tbody>

   <%
ArrayList<FoodItem> foodItems= (ArrayList<FoodItem>) getServletContext().getAttribute("foodItems");
   for(int i=startIndex;i<endIndex&&i<foodItems.size();i++){
   HashMap<String,String> foodData=foodItems.get(i).getFoodData();
   String groupName=foodData.get("group_id")==null?"--":new AdminUtility().getGroupNameWithId(Integer.parseInt(foodData.get("group_id")));


   %>
     <tr>
     <td><input type="checkbox" name="checked" value="<%=foodData.get("item_id")%>"></td>
      <th scope="row"><%=foodData.get("item_id")%></th>
             <td><%=foodData.get("item_name")%></td>
             <td><%=foodData.get("price")%></td>
             <td><%=foodData.get("quantity")%></td>
             <td><%=foodData.get("item_type")%></td>
             <td><%=foodData.get("is_available")%></td>
             <td><%=groupName%></td>
             <td><button class="btn btn-primary" name="editId" value="<%= foodData.get("item_id") %>">Edit</button></td>
             <td><button class="btn btn-danger" name="deleteId" value="<%= foodData.get("item_id") %>">Delete</button></td>
           </tr>
<%
   }
   %>
  </tbody>
</table>
  </form>
<nav aria-label="Page navigation ">
<form action="foodItems" method="post">
<input type="number" name="recordLimit" value="<%=recordLimit%>" min="1">
<button type="submit" name="recordLimitPerPage" value="limit"  class="btn btn-dark btn-sm">^</button>
</form>
<form action="foodItems" method="post">
  <ul class="pagination justify-content-center">
  <%
  for(int i=1;i<=Math.ceil((double)foodItems.size()/(double)recordLimit);i++)
  {
  %>
    <li class="page-item"><button class="btn-sm" name="pageNo" value="<%=i%>"><%=i%></button></li>
  <%
  }
  %>
  </ul>
  </form>
</nav>
</body>
</html>
