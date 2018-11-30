package com.example.owner.quizquest;

public class Class {
    private String name;
    private String number;
    private String teacher;
    private String classCode;

    public Class(){

    }

    public Class(String name, String number, String teacher, String classCode) {
        this.name = name;
        this.number = number;
        this.teacher = teacher;
        this.classCode = classCode;
    }

    public Class(String classCode){
        this.classCode = classCode;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getClassCode(){
        return this.classCode;
    }
}
