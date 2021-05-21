package com.example.cultivo;

public class ProductData {

    private String itemName;
    private String itemDescription;
    private String itemPrice;
    private String itemStartDate;
    private String itemEndDate;
    private String itemBidWinner;
    private int itemImage;

    public ProductData(String itemName, String itemDescription, String itemPrice, String itemStartDate, String itemEndDate, String itemBidWinner, int itemImage) {
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

    public String getItemStartDate() {
        return itemStartDate;
    }

    public String getItemEndDate() {
        return itemEndDate;
    }

    public String getItemBidWinner() {
        return itemBidWinner;
    }

    public int getItemImage() {
        return itemImage;
    }
}
