package zwigo.classes;
import java.sql.*;
import java.util.*;
public class FoodGroups{
    ArrayList<String> columnNames= new ArrayList<String>(Arrays.asList("item_id","item_name","price","quantity","item_type","is_available","group_id","group_name"));
    static HashMap<String,ArrayList<FoodItem>> groupMaps=new HashMap<String, ArrayList<FoodItem>>();
    public void setGroupedFood(Connection c) throws SQLException {
        PreparedStatement getGroupQuery=c.prepareStatement("select distinct group_id from food_items fi order by group_id;");
        ResultSet rs=getGroupQuery.executeQuery();
        while(rs.next()){
            PreparedStatement getGroupSpecificItems;
            if(rs.getString("group_id")!=null) {
                 getGroupSpecificItems = c.prepareStatement("select fi.item_id,fi.item_name,fi.price,fi.quantity,fi.item_type,fi.is_available,fi.group_id,fig.group_name  from food_items fi left join food_item_groups fig on fi.group_id=fig.group_id where fi.group_id=?;");
                getGroupSpecificItems.setInt(1, Integer.parseInt(rs.getString("group_id")));
            }
            else {
                 getGroupSpecificItems = c.prepareStatement("select fi.item_id,fi.item_name,fi.price,fi.quantity,fi.item_type,fi.is_available,fi.group_id,fig.group_name  from food_items fi left join food_item_groups fig on fi.group_id=fig.group_id where fi.group_id is null;");
            }
            ResultSet groupData=getGroupSpecificItems.executeQuery();
            ArrayList<FoodItem> groupItems=new ArrayList<FoodItem>();
            String groupName="";
            while(groupData.next()){
                groupName=groupData.getString("group_name")!=null?groupData.getString("group_name"):"Uncategorized";
                HashMap<String,String> groupItem=new HashMap<String,String>();
                for(String column:columnNames){
                    groupItem.put(column,groupData.getString(column));
                }
                FoodItem foodItem=new FoodItem();
                foodItem.setFoodData(groupItem);
                groupItems.add(foodItem);
            }
            groupMaps.put(groupName,groupItems);
        }
    }

    public HashMap<String,ArrayList<FoodItem>> getGroupMaps(){
        return groupMaps;
    }
}