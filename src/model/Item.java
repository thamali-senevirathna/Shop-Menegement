package model;

public class Item {
    public int getCustomerBuyItemQty;
    private String itemCode;
   private String description;
    private double unitPrice;
    private int stock;


    public Item(String itemCode, String description, double unitPrice, int stock) {
        this.itemCode = itemCode;
        this.description = description;
        this.unitPrice = unitPrice;
        this.stock = stock;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
