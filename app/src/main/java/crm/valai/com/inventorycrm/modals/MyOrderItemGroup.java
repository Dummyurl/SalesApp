package crm.valai.com.inventorycrm.modals;

import java.util.ArrayList;

public class MyOrderItemGroup {

    private String Name;
    private ArrayList<MyOrderAddItemDetails> Items;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public ArrayList<MyOrderAddItemDetails> getItems() {
        return Items;
    }

    public void setItems(ArrayList<MyOrderAddItemDetails> Items) {
        this.Items = Items;
    }

}
