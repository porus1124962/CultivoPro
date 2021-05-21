package com.example.cultivo;

public class ProductData {

    private String itemName;
    private String itemDescription;
    private String itemPrice;
    private String itemQuality;
    private String itemStartDate;
    private String itemEndDate;
    private String itemBidWinner;
    private String itemImage;

    public ProductData(String itemName,String itemQuality, String itemDescription, String itemPrice, String itemStartDate, String itemEndDate, String itemBidWinner, String itemImage) {
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.itemPrice = itemPrice;
        this.itemStartDate = itemStartDate;
        this.itemEndDate = itemEndDate;
        this.itemBidWinner = itemBidWinner;
        this.itemImage = itemImage;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public String getItemPrice() {
        return itemPrice;
    }
    public String getItemQuality() {
        return itemQuality;
    }

    public String getItemStartDate() {
        return itemStartDate;
    }

    public String getItemEndDate() {
        return itemEndDate;
    }

    public String getItemBidWinner() {
        return itemBidWinner;
    }

    public String getItemImage() {
        return itemImage;
    }
}
