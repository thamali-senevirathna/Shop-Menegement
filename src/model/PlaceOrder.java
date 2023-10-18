package model;

import java.util.ArrayList;

public class PlaceOrder {
        private String orderId;
        private String date;
        private String customerId;
        private int customerBuyItemQty;
    private ArrayList<OrderDetails> orderDetails;
        public PlaceOrder(String orderId, String date, String customerId,int customerBuyItemQty, ArrayList<OrderDetails> orderDetails) {
            this.orderId = orderId;
            this.date = date;
            this.customerId = customerId;
            this.customerBuyItemQty = customerBuyItemQty;
            this.orderDetails = orderDetails;
        }
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
    public int getCustomerBuyItemQty() {
        return customerBuyItemQty;
    }

    public void setCustomerBuyItemQty(int customerBuyItemQty) {
        this.customerBuyItemQty = customerBuyItemQty;
    }

    public ArrayList<OrderDetails> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(ArrayList<OrderDetails> orderDetails) {
        this.orderDetails = orderDetails;
    }

    @Override
    public String toString() {
        return "PlaceOrder{" +
                "orderId='" + orderId + '\'' +
                ", date='" + date + '\'' +
                ", customerId='" + customerId + '\'' +
                ", customerBuyItemQty=" + customerBuyItemQty +
                ", orderDetails=" + orderDetails +
                '}';
    }
}


