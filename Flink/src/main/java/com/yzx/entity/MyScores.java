package com.yzx.entity;

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

    @Override
    public String toString() {
        return "MyScores{" +
                "id=" + id +","+ ", name='" + name + '\'' + ", Chinese=" + chinese +", English=" + english +", Math=" + math +
                '}';
    }

    public String toCSVString(){
        return id +","+ name +","+ chinese+","+ english +"," +math;
    }
}
