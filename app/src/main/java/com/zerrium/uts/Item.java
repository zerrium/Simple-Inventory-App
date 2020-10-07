package com.zerrium.uts;

public class Item {
    private String id, name, desc;
    private int qty;

    protected Item(String id, String name, int qty, String desc){
        this.id = id;
        this.name = name;
        this.qty = qty;
        this.desc = desc;
    }

    protected String getId(){
        return this.id;
    }

    protected String getName(){
        return this.name;
    }

    protected int getQty(){
        return this.qty;
    }

    protected String getDesc(){
        return this.desc;
    }

    protected void setName(String name){
        this.name = name;
    }

    protected void setQty(int qty){
        this.qty = qty;
    }

    protected void setDesc(String desc){
        this.desc = desc;
    }
}
