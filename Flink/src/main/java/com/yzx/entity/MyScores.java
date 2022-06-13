package com.yzx.entity;

import java.util.ArrayList;
import java.util.List;

public class MyScores {
    public int id;
    public String name;
    public int chinese;
    public int english;
    public int math;

    public MyScores() {
    }

    public MyScores(int id, String name, int chinese, int english, int math) {
        this.id = id;
        this.name = name;
        this.chinese = chinese;
        this.english = english;
        this.math = math;
    }

    public String toCSVString(){
        return id +","+ name +","+ chinese+","+ english +"," +math;
    }
}
