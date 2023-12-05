package zwigo.classes;

import java.util.HashMap;

public class FoodItem {
    HashMap<String, String> foodDetails = new HashMap<String, String>();

    public void setFoodData(HashMap<String, String> foodDetails) {
        this.foodDetails = foodDetails;
    }

    public HashMap<String, String> getFoodData() {
        return foodDetails;
    }
}
