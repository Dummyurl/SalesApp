package crm.valai.com.inventorycrm.modals;

public class MyOrderAddItemDetails {
    private String orderId;
    private String itemId;
    private String itemtotalPrice;
    private String itemaddeddate;
    private String ordersubmitdate;
    private String name;
    private String pricePerMT;
    private String pricePerNos;
    private String availableQtyinMT;
    private String availableQtyinNos;
    private String userinputQty;
    private String itemUnitType;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemtotalPrice() {
        return itemtotalPrice;
    }

    public void setItemtotalPrice(String itemtotalPrice) {
        this.itemtotalPrice = itemtotalPrice;
    }

    public String getItemaddeddate() {
        return itemaddeddate;
    }

    public void setItemaddeddate(String itemaddeddate) {
        this.itemaddeddate = itemaddeddate;
    }

    public String getOrdersubmitdate() {
        return ordersubmitdate;
    }

    public void setOrdersubmitdate(String ordersubmitdate) {
        this.ordersubmitdate = ordersubmitdate;
    }

    public String getItemUnitType() {
        return itemUnitType;
    }

    public void setItemUnitType(String itemUnitType) {
        this.itemUnitType = itemUnitType;
    }

    public String getUserinputQty() {
        return userinputQty;
    }

    public void setUserinputQty(String userinputQty) {
        this.userinputQty = userinputQty;
    }

    public String getPricePerMT() {
        return pricePerMT;
    }

    public void setPricePerMT(String pricePerMT) {
        this.pricePerMT = pricePerMT;
    }

    public String getPricePerNos() {
        return pricePerNos;
    }

    public void setPricePerNos(String pricePerNos) {
        this.pricePerNos = pricePerNos;
    }

    public String getAvailableQtyinMT() {
        return availableQtyinMT;
    }

    public void setAvailableQtyinMT(String availableQtyinMT) {
        this.availableQtyinMT = availableQtyinMT;
    }

    public String getAvailableQtyinNos() {
        return availableQtyinNos;
    }

    public void setAvailableQtyinNos(String availableQtyinNos) {
        this.availableQtyinNos = availableQtyinNos;
    }

    public String getName() {
        return name;
    }

    public void setName(String Name) {
        this.name = Name;
    }
}