package zwigo.classes;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class CartItems {

    ArrayList<String> cartItemFields=new ArrayList<String>(Arrays.asList("item_name","cartQuantity","price","is_available","item_id","discount","coupon","total_price"));
    static ArrayList<CartItem> cartItems=new ArrayList<CartItem>();

    public void setCartItems(PreparedStatement preparedStatement) {
        try {
            int totalPrice=0;
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                HashMap<String,String> cartDetails=new HashMap<String, String>();
                for (String column:cartItemFields) {
                        cartDetails.put(column, rs.getString(column));
                }
                CartItem item=new CartItem();
                item.setCartItemData(cartDetails);
                cartItems.add(item);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public ArrayList<CartItem> getCartItems() {
        return cartItems;
    }
    public void clearCartItemsInMemory(){
        cartItems.clear();
    }
}
