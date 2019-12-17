package com.gotway.gotway.pojo.vo;

import java.util.ArrayList;
import java.util.List;

public class MenuP {
    private Integer id;

    private String name;

    private Integer parentId;

    private List<MenuP> child= new ArrayList<MenuP>();

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<MenuP> getChild() {
        return child;
    }

    public void setChild(List<MenuP> child) {
        this.child = child;
    }
}
