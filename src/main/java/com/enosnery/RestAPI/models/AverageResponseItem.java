package com.enosnery.RestAPI.models;

public class AverageResponseItem {

    public String name;

    public Double saleAverage;

    public Double purchaseAverage;


    public AverageResponseItem(Double saleAverage, Double purchaseAverage, String name){
        this.saleAverage = saleAverage;
        this.purchaseAverage = purchaseAverage;
        this.name = name;
    }


    public Double getSaleAverage() {
        return saleAverage;
    }

    public void setSaleAverage(Double saleAverage) {
        this.saleAverage = saleAverage;
    }

    public Double getPurchaseAverage() {
        return purchaseAverage;
    }

    public void setPurchaseAverage(Double purchaseAverage) {
        this.purchaseAverage = purchaseAverage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
