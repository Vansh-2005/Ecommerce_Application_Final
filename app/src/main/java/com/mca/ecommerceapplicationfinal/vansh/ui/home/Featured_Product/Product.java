package com.mca.ecommerceapplicationfinal.vansh.ui.home.Featured_Product;

public class Product {
    private String Name;
    private double Price;
    private String Image;

    // Default constructor
    public Product() {
        // Default constructor required for Firebase
    }

    // Parameterized constructor
    public Product(String name, double price, String imageURL) {
        this.Name = name;
        this.Price = price;
        this.Image = imageURL;
    }

    // Getter for name
    public String getName() {
        return Name;
    }

    // Setter for name
    public void setName(String name) {
        this.Name = name;
    }

    // Getter for price
    public double getPrice() {
        return Price;
    }

    // Setter for price
    public void setPrice(double price) {
        this.Price = price;
    }

    // Getter for imageURL
    public String getImageURL() {
        return Image;
    }

    // Setter for imageURL
    public void setImageURL(String imageURL) {
        this.Image = imageURL;
    }
}
