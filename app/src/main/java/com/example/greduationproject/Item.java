package com.example.greduationproject;

public class Item {
    String timeofdriverbegintravel,pricetotravel;

    public Item(String timeofdriverbegintravel, String pricetotravel) {
        this.timeofdriverbegintravel = timeofdriverbegintravel;
        this.pricetotravel = pricetotravel;
    }

    public String getTimeofdriverbegintravel() {
        return timeofdriverbegintravel;
    }

    public void setTimeofdriverbegintravel(String timeofdriverbegintravel) {
        this.timeofdriverbegintravel = timeofdriverbegintravel;
    }

    public String getPricetotravel() {
        return pricetotravel;
    }

    public void setPricetotravel(String pricetotravel) {
        this.pricetotravel = pricetotravel;
    }
}
