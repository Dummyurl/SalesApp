package crm.valai.com.inventorycrm.modals;

/**
 * @author by Mohit Arora on 19/4/18.
 */

public class MyOrderResultPOJO {
    private String branchName;
    private String customerName;
    private String clusterName;
    private String category;
    private String itemName;
    private Double quantityInMt;
    private Double rateInMt;
    private Double quantityInPcs;
    private Double rateInPcs;
    private String uom;
    private Double quantity;
    private Double totalAmount;
    private Integer itemId;

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Double getQuantityInMt() {
        return quantityInMt;
    }

    public void setQuantityInMt(Double quantityInMt) {
        this.quantityInMt = quantityInMt;
    }

    public Double getRateInMt() {
        return rateInMt;
    }

    public void setRateInMt(Double rateInMt) {
        this.rateInMt = rateInMt;
    }

    public Double getQuantityInPcs() {
        return quantityInPcs;
    }

    public void setQuantityInPcs(Double quantityInPcs) {
        this.quantityInPcs = quantityInPcs;
    }

    public Double getRateInPcs() {
        return rateInPcs;
    }

    public void setRateInPcs(Double rateInPcs) {
        this.rateInPcs = rateInPcs;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }
}