package com.zy.ticketseller.bean;

import java.io.Serializable;

/**
 * Created by Songzhihang on 2018/2/25.
 * 栏目栏栏目的实体
 */
public class TagItem implements Serializable{

    private String title;

    private String id;

    private String type;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
