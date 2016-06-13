package com.pingplusplus.demoapp;

/**
 * Created by dong on 16/2/14.
 */
public class Card {
    private String id;
    private String object;
    private String created;
    private String last4;
    private String funding;
    private String brand;
    private String bank;
    private String customer;

    public Card(String id, String object, String created, String last4, String funding, String brand, String bank, String customer) {
        this.id = id;
        this.object = object;
        this.created = created;
        this.last4 = last4;
        this.funding = funding;
        this.brand = brand;
        this.bank = bank;
        this.customer = customer;
    }

    public Card(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getLast4() {
        return last4;
    }

    public void setLast4(String last4) {
        this.last4 = last4;
    }

    public String getFunding() {
        return funding;
    }

    public void setFunding(String funding) {
        this.funding = funding;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "Card{" +
                "id='" + id + '\'' +
                ", object='" + object + '\'' +
                ", created='" + created + '\'' +
                ", last4='" + last4 + '\'' +
                ", funding='" + funding + '\'' +
                ", brand='" + brand + '\'' +
                ", bank='" + bank + '\'' +
                ", customer='" + customer + '\'' +
                '}';
    }
}
