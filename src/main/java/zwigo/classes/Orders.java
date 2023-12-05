package zwigo.classes;

import java.sql.*;
import java.util.*;

public class Orders {
    ArrayList<String> orderData = new ArrayList<String>(Arrays.asList("order_id", "no_of_items", "delivered_address", "ordered_time", "delivery_status"));
    ArrayList<String> singleOrderCols = new ArrayList<String>(Arrays.asList("item_name", "quantity", "total_price", "mrp", "coupon","discount"));
    static ArrayList<Order> orders = new ArrayList<Order>();
    static ArrayList<Order> singleOrderData = new ArrayList<Order>();

    public void setOrderDetails(PreparedStatement preparedStatement) {
        try {
            clearOrdersInMemory();
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                HashMap<String, String> orderDetails = new HashMap<String, String>();
                for (String column : orderData) {
                    orderDetails.put(column, rs.getString(column));
                }
                Order order = new Order();
                order.setOrderData(orderDetails);
                orders.add(order);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setSingleOrderData(PreparedStatement preparedStatement) {
        try {
            clearSingleOrderData();
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                HashMap<String, String> orderDetails = new HashMap<String, String>();
                for (String column : singleOrderCols) {
                    orderDetails.put(column, rs.getString(column));
                }
                Order order = new Order();
                order.setOrderData(orderDetails);
                singleOrderData.add(order);
            }
            System.out.println(singleOrderData.size()+"---in orders");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public ArrayList<Order> getSingleOrderData() {
        return singleOrderData;
    }

    public void clearOrdersInMemory() {
        orders.clear();
    }

    public void clearSingleOrderData(){
        singleOrderData.clear();
    }
}
