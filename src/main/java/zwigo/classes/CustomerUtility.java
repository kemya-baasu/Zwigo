package zwigo.classes;
import org.postgresql.util.PSQLException;
import zwigo.api.AsyncCheckOutListener;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.util.*;

public class CustomerUtility {
    public void setOrderDetails(HttpServletRequest req) throws SQLException {
        HttpSession session = req.getSession();
        String username = (String) session.getAttribute("username");
        ServletContext context = req.getServletContext();
        Connection c = (Connection) context.getAttribute("connection");
        PreparedStatement preparedStatement = c.prepareStatement("select oim.no_of_items,oim.order_id,oim.event_timestamp as ordered_time,zc.address as delivered_address,oim.delivery_status from order_item_mappings oim join zwigo_customers zc on oim.username =zc.username where oim.username = ? order by order_id ASC;");
        preparedStatement.setString(1, username);
        Orders orders = new Orders();
        orders.setOrderDetails(preparedStatement);
    }
    public void setSingleOrderData(HttpServletRequest req) throws SQLException {
        ServletContext context = req.getServletContext();
        Connection c = (Connection) context.getAttribute("connection");
        PreparedStatement preparedStatement = c.prepareStatement("select fi.item_name,zo.quantity ,zo.price as total_price,zo.mrp,zo.coupon,zo.discount from zwigo_orders zo inner join food_items fi on zo.item_id =fi.item_id where zo.order_id =?;");
        preparedStatement.setInt(1, Integer.parseInt(req.getParameter("id")));
        Orders orders = new Orders();
        orders.setSingleOrderData(preparedStatement);
    }
    public ArrayList<Order> getOrderDetails(){
        Orders orders=new Orders();
        return orders.getOrders();
    }
    public ArrayList<Order> getSingleOrderData(){
        Orders orders=new Orders();
        return orders.getSingleOrderData();
    }
    public HashMap<String,String> getAccountData(HttpServletRequest req) throws SQLException {
        HashMap<String ,String> accountDetails=new HashMap<String, String>();
        ArrayList<String> accountFields=new ArrayList<String>(Arrays.asList("name","address","phone_number"));
        ServletContext context = req.getServletContext();
        Connection c = (Connection) context.getAttribute("connection");
        PreparedStatement preparedStatement=c.prepareStatement("select name,address,phone_number from zwigo_customers where username=?");
        preparedStatement.setString(1,(String) req.getSession().getAttribute("username"));
        ResultSet rs=preparedStatement.executeQuery();
        while (rs.next()){
            for (String field:accountFields) {
                accountDetails.put(field,rs.getString(field));
            }
            break;
        }
        return accountDetails;
    }

    public void applyAccountChanges(HttpServletRequest req) throws SQLException {
        ServletContext context = req.getServletContext();
        Connection c = (Connection) context.getAttribute("connection");
        PreparedStatement preparedStatement=c.prepareStatement("update zwigo_customers set name=? ,address=? ,phone_number=? where username =?;");
        preparedStatement.setString(1,req.getParameter("customerName"));
        preparedStatement.setString(2,req.getParameter("address"));
        preparedStatement.setString(3,req.getParameter("phoneNumber"));
        preparedStatement.setString(4, (String) req.getSession().getAttribute("username"));
        preparedStatement.execute();
        System.out.println("Customer Account data is Successfully Updated!");
    }

    public void addItemToCart(HttpServletRequest req) throws SQLException {
        ServletContext context = req.getServletContext();
        Connection c = (Connection) context.getAttribute("connection");
        try {
            PreparedStatement priceQuery = c.prepareStatement("select price from food_items where item_id=?;");
            priceQuery.setInt(1, Integer.parseInt(req.getParameter("item_id")));
            ResultSet rs=priceQuery.executeQuery();
            int item_price=0;
            while(rs.next()){
                item_price=Integer.parseInt(rs.getString("price"));
            }
            PreparedStatement preparedStatement = c.prepareStatement("insert into customer_cart(customer_name,item_id,quantity,total_price) values(?,?,?,?);");
            preparedStatement.setString(1, (String) req.getSession().getAttribute("username"));
            preparedStatement.setInt(2, Integer.parseInt(req.getParameter("item_id")));
            preparedStatement.setInt(3, Integer.parseInt(req.getParameter("quantity")));
            preparedStatement.setInt(4,Integer.parseInt(req.getParameter("quantity"))*item_price);
            preparedStatement.execute();
            System.out.println("Item Added To Cart Successfully!");
        } catch (PSQLException e) {
                if (e.getMessage().contains("duplicate key value violates unique constraint")) {
                    PreparedStatement getQuantity=c.prepareStatement("select * from customer_cart where item_id=? and customer_name=?");
                    getQuantity.setInt(1,Integer.parseInt(req.getParameter("item_id")));
                    getQuantity.setString(2,(String) req.getSession().getAttribute("username"));
                    ResultSet rs=getQuantity.executeQuery();
                    int oldQuantity=0;
                    while(rs.next()){
                        oldQuantity= Integer.parseInt(rs.getString("quantity"));
                        break;
                    }
                    PreparedStatement priceQuery = c.prepareStatement("select price from food_items where item_id=?;");
                    priceQuery.setInt(1, Integer.parseInt(req.getParameter("item_id")));
                    ResultSet rs1=priceQuery.executeQuery();
                    int item_price=0;
                    while(rs1.next()){
                        item_price=Integer.parseInt(rs1.getString("price"));
                    }
                    PreparedStatement preparedStatement=c.prepareStatement("update customer_cart set quantity = ?,total_price=? where item_id  = ? and  customer_name = ?;");
                  preparedStatement.setInt(1, oldQuantity+Integer.parseInt(req.getParameter("quantity")));
                    preparedStatement.setInt(2,(oldQuantity+Integer.parseInt(req.getParameter("quantity")))*item_price);
                    preparedStatement.setInt(3, Integer.parseInt(req.getParameter("item_id")));
                    preparedStatement.setString(4,(String) req.getSession().getAttribute("username"));
                  preparedStatement.execute();
                    System.out.println("Updated item count in cart successfully!");
                } else {
                    e.printStackTrace();
                }
        }
    }

    public void setCustomerCartItems(HttpServletRequest req) throws SQLException {
        ServletContext context = req.getServletContext();
        Connection c = (Connection) context.getAttribute("connection");
        PreparedStatement preparedStatement=c.prepareStatement("select cc.quantity as cartQuantity,fi.item_name,fi.price,fi.is_available,fi.item_id,cc.discount,cc.coupon,cc.total_price from customer_cart cc inner join food_items fi on fi.item_id = cc.item_id where cc.customer_name =?;");
        preparedStatement.setString(1, (String) req.getSession().getAttribute("username"));
        CartItems cartItems=new CartItems();
        cartItems.clearCartItemsInMemory();
        cartItems.setCartItems(preparedStatement);
    }

    public ArrayList<CartItem> getCustomerCartItems(){
        return new CartItems().getCartItems();
    }

    public HashMap<String,String> getCartItem(int itemId,HttpServletRequest req) throws SQLException {
        ArrayList<String> cartItemFields=new ArrayList<String>(Arrays.asList("cartquantity","item_name","price","is_available","discount","coupon","availableQuantity"));
        ServletContext context = req.getServletContext();
        Connection c = (Connection) context.getAttribute("connection");
        HashMap<String,String> cartItem=new HashMap<String, String>();
        PreparedStatement preparedStatement=c.prepareStatement("select cc.quantity as cartQuantity,fi.item_name,fi.price,fi.is_available,cc.coupon,cc.discount,cc.total_price,fi.quantity as availableQuantity from customer_cart cc inner join food_items fi on fi.item_id = cc.item_id where cc.customer_name =? and cc.item_id=?;");
        preparedStatement.setString(1,(String) req.getSession().getAttribute("username"));
        preparedStatement.setInt(2,itemId);
        ResultSet rs= preparedStatement.executeQuery();
        while(rs.next()){
            for (String field:cartItemFields) {
                cartItem.put(field,rs.getString(field));
            }
        }
        return cartItem;
    }
    public void updateCartDetails(HttpServletRequest req) throws SQLException {
        ServletContext context = req.getServletContext();
        Connection c = (Connection) context.getAttribute("connection");
        PreparedStatement priceQuery = c.prepareStatement("select price from food_items where item_id=?;");
        priceQuery.setInt(1, Integer.parseInt(req.getParameter("id")));
        ResultSet rs1=priceQuery.executeQuery();
        int item_price=0;
        while(rs1.next()){
            item_price=Integer.parseInt(rs1.getString("price"));
        }
        int discount=req.getParameter("coupon")!=null?10:1;
        int total_price=(Integer.parseInt(req.getParameter("cartQuantity"))*item_price)-((discount*Integer.parseInt(req.getParameter("cartQuantity"))*item_price)/100);
        PreparedStatement preparedStatement=c.prepareStatement("update customer_cart set coupon=?,discount=?,total_price=?,quantity=? where customer_name=? and item_id=?");
        preparedStatement.setString(1,req.getParameter("coupon"));
        preparedStatement.setInt(2,discount==1?0:discount);
        preparedStatement.setInt(3,total_price);
        preparedStatement.setInt(4, Integer.parseInt(req.getParameter("cartQuantity")));
        preparedStatement.setString(5,(String) req.getSession().getAttribute("username"));
        preparedStatement.setInt(6, Integer.parseInt(req.getParameter("id")));
        preparedStatement.execute();
    }

    public void deleteCartItemById(HttpServletRequest req) throws SQLException {
        ServletContext context = req.getServletContext();
        Connection c = (Connection) context.getAttribute("connection");
        PreparedStatement deleteQuery = c.prepareStatement("delete from customer_cart where item_id=? and customer_name=?");
        deleteQuery.setInt(1, Integer.parseInt(req.getParameter("id")));
        deleteQuery.setString(2,(String) req.getSession().getAttribute("username"));
        deleteQuery.execute();
    }

    public void checkOut(HttpServletRequest req) throws SQLException {
        ServletContext context = req.getServletContext();
        Connection c = (Connection) context.getAttribute("connection");
        PreparedStatement placeOrder=c.prepareStatement("insert into order_item_mappings(username,event_timestamp,no_of_items) select cc.customer_name ,CURRENT_TIMESTAMP as event_timestamp ,count(cc.item_id) from customer_cart cc where cc.customer_name =? group by cc.customer_name returning order_id;");
        placeOrder.setString(1,(String) req.getSession().getAttribute("username"));
        ResultSet orderId=placeOrder.executeQuery();
        orderId.next();
        PreparedStatement addToOrdersQuery=c.prepareStatement("insert into zwigo_orders(order_id ,item_id,quantity,price,mrp,discount,coupon) select ? as order_id , cc.item_id ,cc.quantity,cc.total_price as price,fi.price as mrp,cc.discount,cc.coupon from customer_cart cc inner join food_items fi ON fi.item_id =cc.item_id where cc.customer_name =? and fi.quantity >= cc.quantity and fi.is_available ='Available';");
        addToOrdersQuery.setInt(1,orderId.getInt("order_id"));
        addToOrdersQuery.setString(2,(String) req.getSession().getAttribute("username"));
        addToOrdersQuery.execute();
        PreparedStatement updateFoodCountQuery=c.prepareStatement("UPDATE food_items AS t1 SET quantity  = t1.quantity - t2.quantity FROM customer_cart  AS t2 WHERE t1.item_id  = t2.item_id and t2.customer_name =? and t1.is_available ='Available' and t1.quantity >=t2.quantity ;");
        updateFoodCountQuery.setString(1,(String) req.getSession().getAttribute("username"));
        updateFoodCountQuery.execute();
        PreparedStatement deleteCartItemsQuery=c.prepareStatement("DELETE FROM customer_cart cc USING food_items fi WHERE cc.item_id = fi.item_id AND cc.customer_name = ? AND cc.quantity <= fi.quantity AND fi.is_available = 'Available';");
        deleteCartItemsQuery.setString(1,(String) req.getSession().getAttribute("username"));
        deleteCartItemsQuery.execute();
        PreparedStatement updateAvailableStatus=c.prepareStatement("update food_items as fi set is_available='Unavailable' where quantity=0 returning item_id");
        updateAvailableStatus.execute();
        AsyncContext asyncContext = req.startAsync();
        asyncContext.addListener(new AsyncCheckOutListener());
        req.setAttribute("orderId",orderId.getInt("order_id"));
        req.setAttribute("connection",c);
        asyncContext.setTimeout(0);
        final AsyncContext finalAsyncContext = asyncContext;
        asyncContext.start(new Runnable() {
            public void run() {
                long start=System.currentTimeMillis();
                try {
                    Integer order_ids= (Integer) finalAsyncContext.getRequest().getAttribute("orderId");
                    Thread.sleep(2 * 60 * 1000);
                    Connection c=(Connection) finalAsyncContext.getRequest().getAttribute("connection");
                    PreparedStatement markItemsDelivered=c.prepareStatement("update order_item_mappings set delivery_status='Delivered' where order_id=?");
                    markItemsDelivered.setInt(1,order_ids);
                    markItemsDelivered.execute();
                    finalAsyncContext.complete();
                    System.out.println(markItemsDelivered+"--Items Delivered After : "+(System.currentTimeMillis()-start));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
        System.out.println("Started process!");
    }
    public void setGroupedFoodItems(HttpServletRequest req) throws SQLException {
        ServletContext context = req.getServletContext();
        Connection c = (Connection) context.getAttribute("connection");
        FoodGroups foodGroups=new FoodGroups();
        foodGroups.setGroupedFood(c);
    }
    public HashMap<String,ArrayList<FoodItem>> getGroupMaps(){
        FoodGroups foodGroups=new FoodGroups();
        return foodGroups.getGroupMaps();
    }
}
