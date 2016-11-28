package com.mahui.mhmvp.model;

/**
 * Created by Administrator on 2016/11/28.
 */

public class Person extends BaseBean {
    String name;
    String sex;

    public Person(String name, String sex) {
        this.name = name;
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
