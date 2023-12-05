package zwigo.classes;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import java.util.*;

public class AdminUtility {
    Connection c;
    public AdminUtility(){
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection("jdbc:postgresql://localhost/Zwigo","postgres","postgres" );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void markAvailable(String[] recordIds) throws SQLException {
        PreparedStatement preparedStatement=c.prepareStatement("update food_items set is_available='Available' where item_id = ANY(string_to_array(?,',')::int[])");
        String arrayString = "";
        for (int i = 0; i < recordIds.length; i++) {
            arrayString += recordIds[i];
            if (i < recordIds.length - 1) {
                arrayString += ",";
            }
        }
        preparedStatement.setString(1, arrayString);
        preparedStatement.execute();

    }


    public void markUnavailable(String[] recordIds) throws SQLException {
        PreparedStatement preparedStatement=c.prepareStatement("update food_items set is_available='Unavailable' where item_id = ANY(string_to_array(?,',')::int[])");
        String arrayString = "";
        for (int i = 0; i < recordIds.length; i++) {
            arrayString += recordIds[i];
            if (i < recordIds.length - 1) {
                arrayString += ",";
            }
        }
        preparedStatement.setString(1, arrayString);
        preparedStatement.execute();
    }

    public void updateFoodData(int id, HttpServletRequest req) throws SQLException {
        PreparedStatement preparedStatement=c.prepareStatement("update food_items set item_name=?,price=?,quantity=?,item_type=?,is_available=?,group_id=? where item_id=?");
        GetFoodItemsFromDB foodItemsObj=new GetFoodItemsFromDB();
        ArrayList<FoodItem> foodItems=foodItemsObj.getFoodItems();
        for(int i=0;i<foodItems.size();i++){
            HashMap<String,String> foodData=foodItems.get(i).getFoodData();
            if(foodData.get("item_id").equals(id+"")) {
                preparedStatement.setString(1,req.getParameter("item_name"));
                preparedStatement.setInt(2, Integer.parseInt(req.getParameter("item_price")));
                preparedStatement.setInt(3, Integer.parseInt(req.getParameter("item_quantity")));
                preparedStatement.setString(4,req.getParameter("type"));
                preparedStatement.setString(5,req.getParameter("availablility"));
                if (req.getParameter("group").isEmpty()) {
                    preparedStatement.setNull(6, Types.INTEGER);
                } else {
                    preparedStatement.setInt(6,Integer.parseInt(req.getParameter("group")));
                }
                preparedStatement.setInt(7,id);
                preparedStatement.execute();
                break;
            }
        }
    }
    public void bulkDeleteItem(String[] ids) throws SQLException {
        PreparedStatement deleteItem = c.prepareStatement("update food_items set is_deleted='Yes' where item_id=ANY(string_to_array(?,',')::int[])");
        String arrayString = "";
        for (int i = 0; i < ids.length; i++) {
            arrayString += ids[i];
            if (i < ids.length - 1) {
                arrayString += ",";
            }
        }
        deleteItem.setString(1, arrayString);
        deleteItem.execute();
    }

    public void deleteById(int id) throws SQLException {
        PreparedStatement deleteItem = c.prepareStatement("update food_items set is_deleted='Yes' where item_id=?");
        deleteItem.setInt(1, id);
        deleteItem.execute();
    }

    public void addSingleItem(String itemName,String price,String quantity,String type,String groupId){
       try {
           PreparedStatement insertItems = c.prepareStatement("insert into food_items (item_name,price,quantity,item_type,group_id) values (?,?,?,?,?)");
           insertItems.setString(1, itemName);
           insertItems.setInt(2, Integer.parseInt(price));
           insertItems.setInt(3, Integer.parseInt(quantity));
           insertItems.setString(4, type);
           if (groupId.isEmpty()) {
               insertItems.setNull(5, Types.INTEGER);
           } else {
               insertItems.setInt(5, Integer.parseInt(groupId));
           }
           insertItems.execute();
       }catch(SQLException s){
           System.out.println(s+"--inAddSingleItem");
       }
    }

    public void bulkAddItems(HttpServletRequest req) {
        try {
            Part filePart = req.getPart("myfile");
            InputStream fileStream = filePart.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(fileStream));
            String line;
            PreparedStatement preparedStatement = c.prepareStatement("insert into food_items (item_name,price,quantity,item_type,group_id) values (?,?,?,?,?)");

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    preparedStatement.setString(1, parts[0]);
                    preparedStatement.setInt(2, Integer.parseInt(parts[1]));
                    preparedStatement.setInt(3, Integer.parseInt(parts[2]));
                    preparedStatement.setString(4,parts[3] );
                    if (parts[4].equals("null")) {
                        preparedStatement.setNull(5, Types.INTEGER);
                    } else {
                        preparedStatement.setInt(5, Integer.parseInt(parts[4]));
                    }
                    preparedStatement.addBatch();
                }
            }
            int[] batchResult = preparedStatement.executeBatch();
            int totalInserted = 0;
            for (int result : batchResult) {
                if (result >= 0) {
                    totalInserted++;
                }
            }
            System.out.println("Total records inserted: " + totalInserted);
        }catch (Exception e){
            System.out.println("Exception in Adding Items Bulk : "+e);
        }
    }

    public HashMap<String,String> getItemGroups() throws SQLException {
        PreparedStatement getGroups=c.prepareStatement("select * from food_item_groups");
        ResultSet rs=getGroups.executeQuery();
        HashMap<String,String> foodGroups=new HashMap<String, String>();
        while(rs.next()){
            foodGroups.put(rs.getString("group_id"),rs.getString("group_name"));
        }
        return foodGroups;
    }

    public String getGroupNameWithId(int groupId) throws SQLException {
        PreparedStatement preparedStatement=c.prepareStatement("select * from food_item_groups where group_id=?");
        preparedStatement.setInt(1,groupId);
        ResultSet rs=preparedStatement.executeQuery();
        String groupName="";
        while(rs.next()){
            groupName=rs.getString("group_name");
        }
        return groupName;
    }

    public void addNewFoodGroup(String groupName){
        try {
            PreparedStatement preparedStatement = c.prepareStatement("insert into food_item_groups(group_name) values(?);");
            preparedStatement.setString(1, groupName);
            preparedStatement.execute();
        }catch (SQLException s){
            System.out.println(s+"--in addNewFoodGroup");
        }
    }

    public void deleteFoodGroup(int groupId) {
        try {
            PreparedStatement updateFoodItemsFoodGroup = c.prepareStatement("update food_items set group_id=null where group_id=?;");
            updateFoodItemsFoodGroup.setInt(1,groupId);
            updateFoodItemsFoodGroup.execute();
            PreparedStatement deleteGroup = c.prepareStatement("delete from food_item_groups where group_id =?;");
            deleteGroup.setInt(1,groupId);
            deleteGroup.execute();
        }catch (SQLException s){
            System.out.println(s+"--in deletion of Group");
        }
    }
}
