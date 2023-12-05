package zwigo.classes;

import java.util.HashMap;

public class Order {
    HashMap<String, String> orderData = new HashMap<String, String>();

    public void setOrderData(HashMap<String, String> orderData) {
        this.orderData = orderData;
    }

    public HashMap<String, String> getOrderData() {
        return orderData;
    }
}
