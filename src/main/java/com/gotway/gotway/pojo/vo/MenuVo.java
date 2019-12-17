package com.gotway.gotway.pojo.vo;



public class MenuVo extends MenuP{
    public static Integer CHECKED_TRUE=1;
    public static Integer CHECKED_FALSE=0;
    public static Integer CHECKED_OTHER=10;


    private Integer checked = CHECKED_FALSE;

    public Integer getChecked() {
        return checked;
    }

    public void setChecked(Integer checked) {
        this.checked = checked;
    }

}
