package com.snaillemon.rookiestore.bean;

/**
 * Created by GoodBoy on 10/4/2016.
 */

public class Category {
    public int id;
    public String name;
    public int sort;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }
}
