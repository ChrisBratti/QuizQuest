package com.example.owner.quizquest;

public class Class {
    private String name;
    private String teacher;
    private int id;
    private String nickName;

    public Class(){

    }

    public Class(String name, String teacher, int id) {
        this.name = name;
        this.teacher = teacher;
        this.id = id;
    }

    public Class(int id){
        this.id = id;
    }

    public String getName() {
        return name;
    }


    public String getTeacher() {
        return teacher;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public int getId(){
        return this.id;
    }

    public void setNickName(String nickName) {this.nickName = nickName;}

    public String getNickName(){return this.nickName;}
}
