package com.gotway.gotway.pojo;

import com.gotway.gotway.pojo.vo.MenuP;

public class Menu  extends MenuP {

    private String url;

    private Integer state;

    private Integer sort;

    private Integer type;

    private String icon;

    private Integer typeTag;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }

    public Integer getTypeTag() {
        return typeTag;
    }

    public void setTypeTag(Integer typeTag) {
        this.typeTag = typeTag;
    }
}