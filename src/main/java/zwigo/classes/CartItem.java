package zwigo.classes;

import java.util.HashMap;

public class CartItem {
    HashMap<String, String> cartItemData = new HashMap<String, String>();

    public void setCartItemData(HashMap<String, String> cartItemData) {
        this.cartItemData = cartItemData;
    }

    public HashMap<String, String> getCartItemData() {
        return cartItemData;
    }

}
