package zwigo.classes;
import java.sql.*;
import java.util.*;

public class GetFoodItemsFromDB {
ArrayList<String> itemData=new ArrayList<String>(Arrays.asList("item_id","item_name","price","quantity","item_type","is_available","group_id"));
static ArrayList<FoodItem> foodItems=new ArrayList<FoodItem>();

public void setFoodItems(PreparedStatement preparedStatement) {
    System.out.println(preparedStatement);
    try {
        ResultSet rs = preparedStatement.executeQuery();
        while(rs.next()){
            HashMap<String,String> foodDetails=new HashMap<String, String>();
            for (String column:itemData) {
                foodDetails.put(column,rs.getString(column));
            }
            FoodItem item=new FoodItem();
            item.setFoodData(foodDetails);
            foodItems.add(item);
        }
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }

}
    public ArrayList<FoodItem> getFoodItems() {
        return foodItems;
    }
    public void clearFoodItemsInMemory(){
    foodItems.clear();
    }
}
