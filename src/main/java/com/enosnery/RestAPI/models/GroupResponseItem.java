package com.enosnery.RestAPI.models;

public class GroupResponseItem {

    public String name;

    public Integer count;

    public GroupResponseItem(String name, Integer count){
        this.name = name;
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
